package com.github.xb10.scorecard;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MenuViewHolder> {

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView menuOption;
        ImageView menuImage;
        MenuOption currentItem;

        MenuViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardView);
            menuOption = (TextView)itemView.findViewById(R.id.menu_option);
            menuImage = (ImageView)itemView.findViewById(R.id.menu_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (currentItem.image){

                        case R.drawable.bitch:
                            Toast.makeText(view.getContext(), "Bitch work for you", Toast.LENGTH_LONG).show();
                            break;
                        case R.drawable.logout:
                            //Intent i = new Intent(view.getContext(), LoginActivity.class);
                            view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));
                            break;
                    }
                }
            });
        }

        interface onClickListener{

        }
    }

    private List<MenuOption> menuList;

    RecyclerAdapter(List<MenuOption> menuList){
        this.menuList = menuList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_menu_option, viewGroup, false);

        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder menuViewHolder, int i) {
        menuViewHolder.menuOption.setText(menuList.get(i).text);
        menuViewHolder.menuImage.setImageResource(menuList.get(i).image);

        menuViewHolder.currentItem = menuList.get(i);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}