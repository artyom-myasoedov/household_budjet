package ru.vsu.hb_front.sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.disposables.Disposable;
import ru.vsu.hb_front.R;
import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.dto.CategoryDto;

public class EditCategoryBottomSheet extends BottomSheetDialogFragment {

    private Disposable createCategoryDisposable;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_category_bottom_sheet,
                container, false);

        TextInputEditText categoryNameInput = v.findViewById(R.id.name);
        MaterialButton createBtn = v.findViewById(R.id.create_btn);

        createBtn.setOnClickListener(view -> {
            if(categoryNameInput.getText().toString()==null || categoryNameInput.getText().toString().length()==0){
                categoryNameInput.setError("Недопустимая длина");
            }else {
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setCategoryName(categoryNameInput.getText().toString());
                createCategoryDisposable = Api.getInstance().createCategory(categoryDto).subscribe(resp->{
                    if(resp.isSuccessful()){
                        dismiss();
                    }else{
                        categoryNameInput.setError("Возникла ошибка, возможно имя уже занято");
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(createCategoryDisposable!=null) createCategoryDisposable.dispose();
    }
}
