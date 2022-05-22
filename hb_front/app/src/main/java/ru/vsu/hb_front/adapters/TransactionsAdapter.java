package ru.vsu.hb_front.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.vsu.hb_front.R;
import ru.vsu.hb_front.dto.TransactionDto;
import ru.vsu.hb_front.sheets.CreateTransactionBottomSheet;
import ru.vsu.hb_front.sheets.EditTransactionBottomSheet;


public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.CardViewHolder>{

    List<TransactionDto> cards;
    FragmentActivity fragmentActivity;

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        int currentCardPosition;
        Context mContext;
        TransactionDto curTransaction;

        CardViewHolder(CardView cv, Context context) { //добавили Context context
            super(cv);
            cardView = cv;
            mContext=context;
        }

    }

    public TransactionsAdapter(List<TransactionDto> cards, FragmentActivity fragmentActivity){
        this.cards = cards;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_cardview, parent, false);
        return new CardViewHolder(cv, cv.getContext());
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int position) {
        CardView cardView = cardViewHolder.cardView;

        cardView.setOnClickListener(view -> {
            EditTransactionBottomSheet bottomSheet = new EditTransactionBottomSheet(cards.get(position));
            bottomSheet.show(fragmentActivity.getSupportFragmentManager(),
                    "EditTransactionBottomSheet");
        });

        cardViewHolder.currentCardPosition = position;

        TextView varName = cardView.findViewById(R.id.name);
        TextView summ = cardView.findViewById(R.id.summ);
        TextView date = cardView.findViewById(R.id.date);
        varName.setText("Описание: "+cards.get(position).getDescription());
        if(cards.get(position).getCategoryId()!=null){
            summ.setText("- "+cards.get(position).getSum().toString()+" руб");
            summ.setTextColor(Color.RED);
        }else{
            summ.setText("+ "+cards.get(position).getSum().toString()+" руб");
            summ.setTextColor(Color.GREEN);
        }

        date.setText(cards.get(position).getCreateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        cardViewHolder.curTransaction = cards.get(position);
    }

    @Override
    public int getItemCount() {
        if(cards!=null)
            return cards.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
