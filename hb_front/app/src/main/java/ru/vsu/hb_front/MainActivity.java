package ru.vsu.hb_front;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.vsu.hb_front.api.StatisticsService;
import ru.vsu.hb_front.databinding.ActivityMainBinding;
import ru.vsu.hb_front.dialogs.LogoutConfirmDialogFragment;
import ru.vsu.hb_front.sheets.CreateCategoryBottomSheet;
import ru.vsu.hb_front.sheets.CreateTransactionBottomSheet;

public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
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

        b.bottomNavigationView.getMenu().getItem(1).setOnMenuItemClickListener(item->{
            if (b.bottomNavigationView.getSelectedItemId() != R.id.transaction_btn) {
                TransactionsFragment transactionsFragment = new TransactionsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, transactionsFragment, "transactions").commit();
            }
            return false;
        });

        b.bottomNavigationView.getMenu().getItem(3).setOnMenuItemClickListener(item->{
            if (b.bottomNavigationView.getSelectedItemId() != R.id.statistics_btn) {
                StatisticsFragment statisticsFragment = new StatisticsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, statisticsFragment, "statistics").commit();
            }
            return false;
        });

        b.bottomNavigationView.getMenu().getItem(4).setOnMenuItemClickListener(item->{
            LogoutConfirmDialogFragment dialog = new LogoutConfirmDialogFragment();
            dialog.show(getSupportFragmentManager(), "logoutConfirm");
            return true;
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

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Нажмите ещё раз для выхода!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }


}