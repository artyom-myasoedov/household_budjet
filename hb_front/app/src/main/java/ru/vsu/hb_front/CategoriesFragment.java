package ru.vsu.hb_front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding b;
    private Disposable categoriesDisposable;
    private Disposable monthOutDisposable;
    private List<CategoryDto> categories;

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(
                inflater, R.layout.fragment_categories, container, false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.category_btn);
        
        categoriesDisposable = Api.getInstance().getUserCategories().repeat().subscribe(resp->{
            if(resp.isSuccessful()){
                List<CategoryDto> categoriesFromServer = resp.body().getData();
                CategoryDto addingCategory = new CategoryDto();
                addingCategory.setCategoryName("Создать");
                categoriesFromServer.add(addingCategory);
                if(categories == null || categoriesFromServer.size()!=categories.size()){
                    categories = categoriesFromServer;
                    b.gridCategories.setAdapter(new CategoriesAdapter(getContext(), categories));
                }

            }else{

            }
        }, err->{
            err.printStackTrace();
        });

        monthOutDisposable = Api.getInstance().getCurMonthOutSum().subscribe(resp->{
            if(resp.isSuccessful()){
                    b.monthSum.setText("Траты за месяц: "+resp.body().getData().toString());
            }else{

            }
        }, err->{
            err.printStackTrace();
        });

        View view = b.getRoot();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(categoriesDisposable!=null) categoriesDisposable.dispose();
        if(monthOutDisposable!=null) monthOutDisposable.dispose();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}