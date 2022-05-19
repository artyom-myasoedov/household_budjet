package ru.vsu.hb_front.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.List;

import ru.vsu.hb_front.MainActivity;
import ru.vsu.hb_front.R;
import ru.vsu.hb_front.dto.CategoryDto;
import ru.vsu.hb_front.sheets.CreateCategoryBottomSheet;

public class CategoriesAdapter extends BaseAdapter {
    private Context context;
    private List<CategoryDto> categories;

    public CategoriesAdapter(Context context, List<CategoryDto> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid = inflater.inflate(R.layout.category_grid_card, parent, false);
        } else {
            grid = convertView;
        }

        ImageView imageView = grid.findViewById(R.id.image);
        TextView textName = grid.findViewById(R.id.text_name);
        TextView textSum = grid.findViewById(R.id.text_sum);

        switch (categories.get(position).getCategoryName()){
            case "Еда":
                imageView.setImageResource(R.drawable.baseline_restaurant_24);
                break;
            case "Транспорт":
                imageView.setImageResource(R.drawable.baseline_directions_car_24);
                break;
            case "Одежда":
                imageView.setImageResource(R.drawable.baseline_man_24);
                break;
            case "Здоровье":
                imageView.setImageResource(R.drawable.baseline_medical_information_24);
                break;
            case "Дом":
                imageView.setImageResource(R.drawable.baseline_home_24);
                break;
            case "Развлечения":
                imageView.setImageResource(R.drawable.baseline_celebration_24);
                break;
            case "Создать":
                imageView.setImageResource(R.drawable.baseline_add_circle_outline_24);
                textSum.setVisibility(View.GONE);
                grid.setOnClickListener(v -> {
                    if(context instanceof MainActivity){
                        CreateCategoryBottomSheet bottomSheet = new CreateCategoryBottomSheet();
                        bottomSheet.show(((MainActivity)context).getSupportFragmentManager(),
                                "ModalBottomSheet");
                    }
                });
                break;
            default:
                imageView.setImageResource(R.drawable.baseline_more_horiz_24);
                break;
        }

        textName.setText(categories.get(position).getCategoryName());
        if(categories.get(position).getOutSumLastMonth() != null){
            textSum.setText(categories.get(position).getOutSumLastMonth().toString());

        }

        return grid;
    }

}