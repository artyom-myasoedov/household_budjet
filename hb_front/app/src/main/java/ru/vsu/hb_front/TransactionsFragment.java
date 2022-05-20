package ru.vsu.hb_front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import ru.vsu.hb_front.adapters.TransactionsAdapter;
import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.databinding.FragmentTransactionsBinding;
import ru.vsu.hb_front.dto.TransactionDto;
import ru.vsu.hb_front.dto.request.PageRequest;
import ru.vsu.hb_front.dto.request.TransactionByCategoryRequest;
import ru.vsu.hb_front.dto.request.TransactionListRequest;

public class TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding b;
    private Disposable balanceDisposable;
    private Disposable transactionsDisposable;
    private List<TransactionDto> transactions = new ArrayList<>();
    private TransactionsAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        b = FragmentTransactionsBinding.inflate(inflater, container, false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.transaction_btn);

        balanceDisposable = Api.getInstance().getBalance()
                .repeatWhen(completed -> completed.delay(1000, TimeUnit.MILLISECONDS)).subscribe(resp -> {
                    if (resp.isSuccessful()) {
                        if (!b.balance.getText().toString().equals("Баланс: " + resp.body().getData().toString()))
                            b.balance.setText("Баланс: " + resp.body().getData().toString());
                    }
                }, Throwable::printStackTrace);


        TransactionListRequest request = new TransactionListRequest();
        PageRequest pageRequest = new PageRequest();
        pageRequest.setLimit(1000);
        request.setPage(pageRequest);

        transactionsDisposable = Api.getInstance().getTransactionsList(request)
                .repeatWhen(completed -> completed.delay(1000, TimeUnit.MILLISECONDS)).subscribe(resp -> {
                    if (resp.isSuccessful()) {
                        transactions.clear();
                        transactions.addAll(resp.body().getData().getItems());
                        if(adapter == null){
                            adapter = new TransactionsAdapter(transactions, getActivity());
                            LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
                            b.rvTransactions.setLayoutManager(llm);
                            b.rvTransactions.setAdapter(adapter);
                        }
                        adapter.notifyDataSetChanged();

                    }

                }, Throwable::printStackTrace);

        return b.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (balanceDisposable != null) balanceDisposable.dispose();
        if (transactionsDisposable != null) transactionsDisposable.dispose();
    }
}