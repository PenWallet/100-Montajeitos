package com.penwallet.cienmontajeitos.Adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.penwallet.cienmontajeitos.Entities.Item;
import com.penwallet.cienmontajeitos.MenuItemType;
import com.penwallet.cienmontajeitos.R;
import com.penwallet.cienmontajeitos.ui.viewmodel.SharedViewModel;

import java.util.ArrayList;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {

    private Item[] items;
    private MenuItemType menuItemType;
    private SharedViewModel viewModel;

    public MenuItemAdapter(Item[] items, MenuItemType menuItemType, SharedViewModel viewModel) {
        this.items = items;
        this.menuItemType = menuItemType;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        MenuItemViewHolder vh = new MenuItemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, final int position) {
        Item item = items[position];

        holder.txtName.setText(item.getName());
        holder.txtPrice.setText(item.getPrice()+" €");

        //Dependiendo de si es miércoles o domingo
        if(viewModel.getIsEuromania().getValue())
            holder.txtPrice.setText(item.getPriceEuromania()+" €");
        else
            holder.txtPrice.setText(item.getPrice()+" €");

        //Dependiendo del tipo de item, se cambia la imagen y se esconde el ID
        switch (menuItemType)
        {
            case MONTADITO:
                holder.txtId.setText(Integer.toString(item.getMenuId()));
                holder.image.setImageResource(R.drawable.sandwich);
                break;
            case SALAD:
                holder.txtId.setText(Integer.toString(item.getMenuId()));
                holder.image.setImageResource(R.drawable.salad);
                break;
            case DESSERT:
                holder.txtId.setText(Integer.toString(item.getMenuId()));
                holder.image.setImageResource(R.drawable.dessert);
                break;
            case APPETIZER:
                holder.txtId.setVisibility(View.GONE);
                holder.image.setImageResource(R.drawable.appetizer);
                break;
            case DRINK:
                holder.txtId.setVisibility(View.GONE);
                holder.image.setImageResource(R.drawable.soda);
                break;
            case ALCOHOL:
                holder.txtId.setVisibility(View.GONE);
                holder.image.setImageResource(R.drawable.beer);
                break;
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewModel.getClients().getValue().isEmpty())
                    Toast.makeText(v.getContext(), v.getResources().getText(R.string.error_noClients), Toast.LENGTH_SHORT).show();
                else
                {
                    Toast.makeText(v.getContext(), "Jeje lo siento, WIP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder
    {
        public View view;
        public ImageView image;
        public TextView txtName, txtPrice, txtId;
        //public Resources resources;

        public MenuItemViewHolder(View view)
        {
            super(view);
            this.view = view;
            this.image = view.findViewById(R.id.itemrow_image);
            this.txtName = view.findViewById(R.id.itemrow_txtName);
            this.txtPrice = view.findViewById(R.id.itemrow_txtPrice);
            this.txtId = view.findViewById(R.id.itemrow_txtId);
            //resources = view.getResources();
        }
    }

}
