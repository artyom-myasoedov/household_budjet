package ru.vsu.hb_front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.vsu.hb_front.databinding.FragmentRegisterBinding;
import ru.vsu.hb_front.databinding.FragmentTransactionsBinding;

public class TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding b;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        b = FragmentTransactionsBinding.inflate(inflater, container, false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.transaction_btn);



        return b.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}