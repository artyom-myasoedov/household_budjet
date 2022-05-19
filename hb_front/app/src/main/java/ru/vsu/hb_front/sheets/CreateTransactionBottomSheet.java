package ru.vsu.hb_front.sheets;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.disposables.Disposable;
import ru.vsu.hb_front.R;
import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.dto.CategoryDto;
import ru.vsu.hb_front.dto.TransactionDto;

public class CreateTransactionBottomSheet extends BottomSheetDialogFragment {

    private Disposable createTransactionDisposable;
    private Disposable getCategoriesDisposable;
    private List<CategoryDto> categories;
    private AutoCompleteTextView categoriesAutoTV;
    private MaterialButton currentDate;
    Calendar date=Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_transaction_bottom_sheet,
                container, false);

        TextInputEditText transactionNameInput = v.findViewById(R.id.name);
        TextInputEditText summInput = v.findViewById(R.id.summ);
        MaterialButton createBtn = v.findViewById(R.id.create_btn);
        MaterialButtonToggleGroup group = v.findViewById(R.id.toggleGroup);
        TextInputLayout categoriesSpinLayout = v.findViewById(R.id.categories_spin_layout);
        currentDate = v.findViewById(R.id.date);
        currentDate.setOnClickListener(this::setDate);

        getCategoriesDisposable = Api.getInstance().getUserCategories().subscribe(resp -> {
            if(resp.isSuccessful()){
                categories = resp.body().getData();
                initCategoriesSpinner(v);
            }
        }, Throwable::printStackTrace);

        group.addOnButtonCheckedListener((group1, checkedId, isChecked) -> {
            if(isChecked){
                if(checkedId == R.id.btn_out){
                    categoriesSpinLayout.setVisibility(View.VISIBLE);
                }else{
                    categoriesSpinLayout.setVisibility(View.GONE);
                }
            }
        });

        createBtn.setOnClickListener(view -> {
            if (transactionNameInput.getText().toString() == null || transactionNameInput.getText().toString().length() == 0) {
                transactionNameInput.setError("Недопустимая длина");
            }else if(summInput.getText().toString() == null || summInput.getText().toString().length() == 0){
                summInput.setError("Неверная сумма");
            } else if(group.getCheckedButtonId() == R.id.btn_out && (categoriesAutoTV==null || categoriesAutoTV.getText().toString()==null
                    ||categoriesAutoTV.getText().toString().length()==0)){
                categoriesAutoTV.setError("Выберите категорию");
            } else {
                TransactionDto transactionDto = new TransactionDto();
                transactionDto.setCreateTime(LocalDateTime.from(date.toInstant()));
                transactionDto.setDescription(transactionNameInput.getText().toString());
                transactionDto.setSum(new BigDecimal(summInput.getText().toString()));
                if(group.getCheckedButtonId() == R.id.btn_out){
                    for(CategoryDto category: categories){
                        if(category.getCategoryName().equals(categoriesAutoTV.getText().toString())){
                            transactionDto.setCategoryId(category.getCategoryId());
                        }
                    }
                }

                createTransactionDisposable = Api.getInstance().createTransaction(transactionDto).subscribe(resp -> {
                    if (resp.isSuccessful()) {
                        dismiss();
                    } else {
                        transactionNameInput.setError("Возникла ошибка, возможно имя уже занято");
                    }
                });
            }
        });

        setInitialDateTime();

        return v;
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

    DatePickerDialog.OnDateSetListener d= (view, year, monthOfYear, dayOfMonth) -> {
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

    private void initCategoriesSpinner(View v)
    {
        categoriesAutoTV = v.findViewById(R.id.categories_spin);
        categoriesAutoTV.setOnTouchListener((view, motionEvent) -> {
            categoriesAutoTV.showDropDown();
            return true;
        });

        ArrayList<String> customerList = getCustomerList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, customerList);
        categoriesAutoTV.setAdapter(adapter);

    }

    private ArrayList<String> getCustomerList()
    {

        ArrayList<String> categoryNames = new ArrayList<>();
        for(CategoryDto category: categories){
            categoryNames.add(category.getCategoryName());
        }
        return categoryNames;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (createTransactionDisposable != null) createTransactionDisposable.dispose();
        if (getCategoriesDisposable != null) getCategoriesDisposable.dispose();
    }
}
