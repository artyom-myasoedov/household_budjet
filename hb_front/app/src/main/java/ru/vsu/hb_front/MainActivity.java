package ru.vsu.hb_front;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

import ru.vsu.hb_front.databinding.ActivityMainBinding;
import ru.vsu.hb_front.sheets.CreateCategoryBottomSheet;
import ru.vsu.hb_front.sheets.CreateTransactionBottomSheet;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);

        b.bottomNavigationView.setBackground(null);
        b.bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        b.bottomNavigationView.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
            if (b.bottomNavigationView.getSelectedItemId() != R.id.category_btn) {
                CategoriesFragment categoriesFragment = new CategoriesFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, categoriesFragment, "categories").commit();
            }
            return false;
        });

        b.fab.setOnClickListener(view -> {
            CreateTransactionBottomSheet bottomSheet = new CreateTransactionBottomSheet();
            bottomSheet.show(this.getSupportFragmentManager(),
                    "CreateTransactionBottomSheet");
        });

        CategoriesFragment categoriesFragment = new CategoriesFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, categoriesFragment, "categories").commit();

    }


}