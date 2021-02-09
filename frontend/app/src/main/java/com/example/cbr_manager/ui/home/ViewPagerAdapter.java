package com.example.cbr_manager.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.cbr_manager.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private List<ViewPagerModel> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public ViewPagerAdapter(List<ViewPagerModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_item, container, false);

        ImageView imageView;
        TextView name, place;

        imageView = view.findViewById(R.id.imageView2);
        name = view.findViewById(R.id.textView2);
        place = view.findViewById(R.id.textView3);

        imageView.setImageResource(models.get(position).getImage());
        name.setText(models.get(position).getName());
        place.setText(models.get(position).getLocation());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Priority client position " + position + " selected." , Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
