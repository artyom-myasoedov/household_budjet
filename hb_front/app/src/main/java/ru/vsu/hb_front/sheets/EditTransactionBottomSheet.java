package ru.vsu.hb_front.sheets;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.disposables.Disposable;
import ru.vsu.hb_front.R;
import ru.vsu.hb_front.adapters.TransactionsAdapter;
import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.dto.CategoryDto;
import ru.vsu.hb_front.dto.TransactionDto;
import ru.vsu.hb_front.dto.request.PageRequest;
import ru.vsu.hb_front.dto.request.TransactionByCategoryRequest;

public class EditTransactionBottomSheet extends BottomSheetDialogFragment {

    private Disposable editTransactionDisposable;
    private Disposable deleteTransactionDisposable;
    private Disposable categoriesDisposable;
    private TransactionDto transaction;
    private MaterialButton currentDate;
    private AutoCompleteTextView categoriesAutoTV;
    private List<CategoryDto> categories;
    private Calendar date = Calendar.getInstance();

    public EditTransactionBottomSheet(TransactionDto transaction) {
        this.transaction = transaction;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_transacrion_bottom_sheet,
                container, false);

        TextInputEditText transactionNameInput = v.findViewById(R.id.name);
        TextInputEditText summInput = v.findViewById(R.id.summ);
        MaterialButton deleteBtn = v.findViewById(R.id.delete_btn);
        MaterialButton saveBtn = v.findViewById(R.id.save_btn);
        TextInputLayout categoriesSpinLayout = v.findViewById(R.id.categories_spin_layout);
        currentDate = v.findViewById(R.id.date);
        date.set(transaction.getCreateTime().getYear(),
                transaction.getCreateTime().getMonthValue()-1,
                transaction.getCreateTime().getDayOfMonth(),
                transaction.getCreateTime().getHour(),
                transaction.getCreateTime().getMinute(),
                transaction.getCreateTime().getSecond());
        currentDate.setOnClickListener(this::setDate);
        setInitialDateTime();

        transactionNameInput.setText(transaction.getDescription());
        summInput.setText(transaction.getSum().toString());


        categoriesDisposable = Api.getInstance().getUserCategories().subscribe(resp -> {
            if (resp.isSuccessful()) {
                categories = resp.body().getData();
                initCategoriesSpinner(v);
                if (transaction.getCategoryId() == null) {
                    categoriesSpinLayout.setVisibility(View.GONE);
                }
            }
        }, Throwable::printStackTrace);

        saveBtn.setOnClickListener(view -> {
            if (transactionNameInput.getText().toString() == null || transactionNameInput.getText().toString().length() == 0) {
                transactionNameInput.setError("Недопустимая длина");
            } else if (summInput.getText().toString() == null || summInput.getText().toString().length() == 0) {
                summInput.setError("Неверная сумма");
            } else if (categoriesSpinLayout.getVisibility() == View.VISIBLE
                    && (categoriesAutoTV == null || categoriesAutoTV.getText().toString() == null
                    || categoriesAutoTV.getText().toString().length() == 0)) {
                categoriesAutoTV.setError("Выберите категорию");
            } else {
                transaction.setCreateTime(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
                transaction.setDescription(transactionNameInput.getText().toString());
                try{
                    transaction.setSum(new BigDecimal(summInput.getText().toString()));
                }catch (Exception e){
                    summInput.setError("Неверный ввод");
                    return;
                }
                if (categoriesSpinLayout.getVisibility() == View.VISIBLE) {
                    for (CategoryDto category : categories) {
                        if (category.getCategoryName().equals(categoriesAutoTV.getText().toString())) {
                            transaction.setCategoryId(category.getCategoryId());
                        }
                    }
                }
                editTransactionDisposable = Api.getInstance().editTransaction(transaction).subscribe(resp -> {
                    if (resp.isSuccessful()) {
                        dismiss();
                    } else {
                        transactionNameInput.setError("Возникла ошибка");
                    }
                });
            }
        });

        deleteBtn.setOnClickListener(view -> {
            deleteTransactionDisposable = Api.getInstance().deleteTransaction(transaction.getTransactionId().toString()).subscribe(resp -> {
                if (resp.isSuccessful()) {
                    dismiss();
                } else {
                    transactionNameInput.setError("Возникла ошибка");
                }
            });
        });

        return v;
    }

    private void initCategoriesSpinner(View v) {
        categoriesAutoTV = v.findViewById(R.id.categories_spin);
        categoriesAutoTV.setOnTouchListener((view, motionEvent) -> {
            categoriesAutoTV.showDropDown();
            return true;
        });

        ArrayList<String> customerList = getCategoriesList();
        for (CategoryDto category : categories) {
            if (transaction.getCategoryId() != null && transaction.getCategoryId().equals(category.getCategoryId())) {
                categoriesAutoTV.setText(category.getCategoryName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, customerList);
        categoriesAutoTV.setAdapter(adapter);
    }

    private ArrayList<String> getCategoriesList() {
        ArrayList<String> categoryNames = new ArrayList<>();
        for (CategoryDto category : categories) {
            categoryNames.add(category.getCategoryName());
        }
        return categoryNames;
    }

    public void setDate(View v) {
        new DatePickerDialog(getActivity(), d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDateTime() {
        currentDate.setText(DateUtils.formatDateTime(getActivity(),
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (editTransactionDisposable != null) editTransactionDisposable.dispose();
        if (deleteTransactionDisposable != null) deleteTransactionDisposable.dispose();
        if (categoriesDisposable != null) categoriesDisposable.dispose();
    }
}
