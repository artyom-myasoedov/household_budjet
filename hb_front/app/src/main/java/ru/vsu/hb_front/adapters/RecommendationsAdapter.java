package ru.vsu.hb_front.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.vsu.hb_front.R;
import ru.vsu.hb_front.dto.TransactionDto;
import ru.vsu.hb_front.sheets.EditTransactionBottomSheet;


public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.CardViewHolder>{

    List<String> cards;
    FragmentActivity fragmentActivity;

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        int currentCardPosition;
        Context mContext;
        String curRecommendation;

        CardViewHolder(CardView cv, Context context) { //добавили Context context
            super(cv);
            cardView = cv;
            mContext=context;
        }

    }

    public RecommendationsAdapter(List<String> cards, FragmentActivity fragmentActivity){
        this.cards = cards;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommendation_cardview, parent, false);
        return new CardViewHolder(cv, cv.getContext());
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int position) {
        CardView cardView = cardViewHolder.cardView;


        cardViewHolder.currentCardPosition = position;

        TextView varName = cardView.findViewById(R.id.name);
        varName.setText(cards.get(position));

        cardViewHolder.curRecommendation = cards.get(position);
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
