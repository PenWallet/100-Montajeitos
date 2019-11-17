package com.penwallet.cienmontajeitos.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.penwallet.cienmontajeitos.Entities.Item;
import com.penwallet.cienmontajeitos.Entities.Person;
import com.penwallet.cienmontajeitos.R;
import com.penwallet.cienmontajeitos.ui.viewmodel.SharedViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.gujun.android.taggroup.TagGroup;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {

    private Item[] items;
    private SharedViewModel viewModel;

    public MenuItemAdapter(Item[] items, SharedViewModel viewModel) {
        this.items = items;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        MenuItemViewHolder vh = new MenuItemViewHolder(view);
        return vh;
    }

    @SuppressLint("SetTextI18n")
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
        switch(items[position].getMenuItemType())
        {
            case MONTADITO:
                holder.txtId.setText(Integer.toString(item.getMenuId()));
                holder.txtId.setVisibility(View.VISIBLE);
                holder.image.setImageResource(R.drawable.sandwich);
                break;
            case SALAD:
                holder.txtId.setVisibility(View.GONE);
                holder.image.setImageResource(R.drawable.salad);
                break;
            case DESSERT:
                holder.txtId.setVisibility(View.GONE);
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

        holder.view.setOnClickListener(v -> {
            if(viewModel.getClients().getValue().isEmpty())
                Toast.makeText(v.getContext(), v.getResources().getText(R.string.error_noClients), Toast.LENGTH_SHORT).show();
            else
            {
                //First we inflate the view which contains the tag control
                LayoutInflater layoutInflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View tagView = layoutInflater.inflate(R.layout.dialog_tags, null);

                final TagGroup unselectedTags = tagView.findViewById(R.id.dialog_unselectedDinersTags);
                final LinearLayout selDinLinearLayout = tagView.findViewById(R.id.dialog_linearLayoutSelectedDiners);
                final TagGroup selectedTags = tagView.findViewById(R.id.dialog_selectedDinersTags);

                final ArrayList<String> allDinersNames = viewModel.getClientsNames();
                final HashMap<String, Integer> selectedDiners = new HashMap<>();
                unselectedTags.setTags(allDinersNames);

                unselectedTags.setOnTagClickListener(tag -> {
                    ArrayList<String> selectedNames = new ArrayList<>();
                    if(selectedDiners.isEmpty())
                        selDinLinearLayout.setVisibility(View.VISIBLE);

                    //If there is no key with the assigned tag, create one with value '1', else, sum 1.
                    selectedDiners.merge(tag, 1, Integer::sum);

                    for (Map.Entry<String, Integer> entry : selectedDiners.entrySet()) {
                        selectedNames.add(entry.getValue() == 1 ? entry.getKey() : entry.getKey()+"("+entry.getValue()+")");
                    }

                    selectedTags.setTags(selectedNames);
                });

                selectedTags.setOnTagClickListener(tag -> {
                    ArrayList<String> selectedNames = new ArrayList<>();
                    if(!selectedDiners.containsKey(tag))
                        tag = tag.split("[(]")[0];

                    selectedDiners.put(tag, selectedDiners.get(tag)-1);

                    if(selectedDiners.get(tag) == 0)
                        selectedDiners.remove(tag);

                    for (Map.Entry<String, Integer> entry : selectedDiners.entrySet()) {
                        selectedNames.add(entry.getValue() == 1 ? entry.getKey() : entry.getKey()+"("+entry.getValue()+")");
                    }

                    selectedTags.setTags(selectedNames);

                    if(selectedDiners.isEmpty())
                        selDinLinearLayout.setVisibility(View.GONE);
                });

                //Now we show the dialog
                new MaterialStyledDialog.Builder(v.getContext())
                        .setTitle(v.getResources().getString(R.string.menu_dialog_title, items[position].getName()))
                        .setPositiveText(R.string.menu_dialog_accept)
                        .setNegativeText(R.string.menu_dialog_cancel)
                        .setStyle(Style.HEADER_WITH_ICON)
                        .setIcon(R.drawable.diner)
                        .setHeaderColor(R.color.lightPurple)
                        .setCancelable(true)
                        .withIconAnimation(false)
                        .withDialogAnimation(true, Duration.FAST)
                        .setCustomView(tagView, 20, 10, 20, 10)
                        .onPositive((dialog, which) -> {
                            if(!selectedDiners.isEmpty())
                            {
                                //TODO: Se puede mejorar, evitando que itere sobre todos si ya se ha añadido el item a los usuarios elegidos
                                for(Person person : viewModel.getClients().getValue())
                                {
                                    if(selectedDiners.containsKey(person.getName()))
                                    {
                                        if(person.getItems().containsKey(items[position]))
                                            person.getItems().put(items[position], person.getItems().get(items[position])+selectedDiners.get(person.getName()));
                                        else
                                            person.getItems().put(items[position], selectedDiners.get(person.getName()));


                                        viewModel.getTotalOrder().getValue().merge(items[position], selectedDiners.get(person.getName()), Integer::sum);
                                    }
                                }
                            }
                        })
                        .show();
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
