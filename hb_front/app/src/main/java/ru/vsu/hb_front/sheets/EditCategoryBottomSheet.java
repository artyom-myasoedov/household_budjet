package ru.vsu.hb_front.sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.disposables.Disposable;
import ru.vsu.hb_front.R;
import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.dto.CategoryDto;

public class EditCategoryBottomSheet extends BottomSheetDialogFragment {

    private Disposable editCategoryDisposable;
    private Disposable deleteCategoryDisposable;
    private CategoryDto category;

    public EditCategoryBottomSheet(CategoryDto category) {
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_category_bottom_sheet,
                container, false);

        TextInputEditText categoryNameInput = v.findViewById(R.id.name);
        MaterialButton deleteBtn = v.findViewById(R.id.delete_btn);
        MaterialButton saveBtn = v.findViewById(R.id.save_btn);

        categoryNameInput.setText(category.getCategoryName());

        saveBtn.setOnClickListener(view -> {
            if(categoryNameInput.getText().toString()==null || categoryNameInput.getText().toString().length()==0){
                categoryNameInput.setError("Недопустимая длина");
            }else {
                category.setCategoryName(categoryNameInput.getText().toString());
                editCategoryDisposable = Api.getInstance().editCategory(category).subscribe(resp->{
                    if(resp.isSuccessful()){
                        dismiss();
                    }else{
                        categoryNameInput.setError("Возникла ошибка, возможно имя уже занято");
                    }
                });
            }
        });

        if(category.getDefault()) {
            deleteBtn.setVisibility(View.GONE);
            saveBtn.setVisibility(View.GONE);
            categoryNameInput.setEnabled(false);
        }

        deleteBtn.setOnClickListener(view->{
            deleteCategoryDisposable = Api.getInstance().deleteCategory(category.getCategoryId().toString()).subscribe(resp->{
                if(resp.isSuccessful()){
                    dismiss();
                }else{
                    categoryNameInput.setError("Возникла ошибка");
                }
            });
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(editCategoryDisposable !=null) editCategoryDisposable.dispose();
        if(deleteCategoryDisposable!=null) deleteCategoryDisposable.dispose();
    }
}
