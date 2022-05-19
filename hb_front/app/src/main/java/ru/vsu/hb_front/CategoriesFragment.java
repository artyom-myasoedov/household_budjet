package ru.vsu.hb_front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import ru.vsu.hb_front.adapters.CategoriesAdapter;
import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.databinding.FragmentCategoriesBinding;
import ru.vsu.hb_front.databinding.FragmentTransactionsBinding;
import ru.vsu.hb_front.dto.CategoryDto;
import ru.vsu.hb_front.sheets.CreateCategoryBottomSheet;
import ru.vsu.hb_front.sheets.EditCategoryBottomSheet;

public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding b;
    private Disposable categoriesDisposable;
    private Disposable monthOutDisposable;
    private List<CategoryDto> categories;
    private CategoriesAdapter adapter;

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(
                inflater, R.layout.fragment_categories, container, false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.category_btn);

        categoriesDisposable = Api.getInstance().getUserCategories()
                .repeatWhen(completed -> completed.delay(500, TimeUnit.MILLISECONDS)).subscribe(resp -> {
                    if (resp.isSuccessful()) {
                        List<CategoryDto> categoriesFromServer = resp.body().getData();
                        CategoryDto addingCategory = new CategoryDto();
                        addingCategory.setCategoryName("Создать");
                        categoriesFromServer.add(addingCategory);
                        if(adapter == null){
                            categories = categoriesFromServer;
                            b.gridCategories.setAdapter(new CategoriesAdapter(getContext(), categories));
                        }
                        if (isNeenUpdateAdapter(categoriesFromServer)) {
                            categories = categoriesFromServer;
                            adapter.notifyDataSetChanged();
                        }

                    } else {

                    }
                }, err -> {
                    err.printStackTrace();
                });

        monthOutDisposable = Api.getInstance().getCurMonthOutSum()
                .repeatWhen(completed -> completed.delay(1000, TimeUnit.MILLISECONDS)).subscribe(resp -> {
            if (resp.isSuccessful()) {
                if(!b.monthSum.getText().toString().equals("Траты за месяц: "+resp.body().getData().toString()))
                    b.monthSum.setText("Траты за месяц: " + resp.body().getData().toString());
            } else {

            }
        }, err -> {
            err.printStackTrace();
        });

        View view = b.getRoot();

        return view;
    }

    private boolean isNeenUpdateAdapter(List<CategoryDto> categoriesFromServer) {
        if (categories == null)
            return true;
        if (categories.size() != categoriesFromServer.size())
            return true;
        for (CategoryDto category : categories) {
            for (CategoryDto categoryFromServer : categoriesFromServer) {
                if (category.getCategoryId()!=null && category.getCategoryId().equals(categoryFromServer.getCategoryId())
                        && !category.getCategoryName().equals(categoryFromServer.getCategoryName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (categoriesDisposable != null) categoriesDisposable.dispose();
        if (monthOutDisposable != null) monthOutDisposable.dispose();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gridView = view.findViewById(R.id.gridCategories);
        gridView.setOnItemClickListener((adapterView, view1, i, l) -> {

            if(categories.get(i).getCategoryName().equals("Создать")){
                CreateCategoryBottomSheet bottomSheet = new CreateCategoryBottomSheet();
                bottomSheet.show(getActivity().getSupportFragmentManager(),
                        "CreateCategoryBottomSheet");
            }else{
                EditCategoryBottomSheet bottomSheet = new EditCategoryBottomSheet(categories.get(i));
                bottomSheet.show(getActivity().getSupportFragmentManager(),
                        "EditCategoryBottomSheet");
            }
        });

    }

}