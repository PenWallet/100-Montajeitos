package com.penwallet.cienmontajeitos.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.penwallet.cienmontajeitos.Entities.Item;
import com.penwallet.cienmontajeitos.Entities.Person;
import com.penwallet.cienmontajeitos.MenuItemType;
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
            public void onClick(final View v) {
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

                    unselectedTags.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                        @Override
                        public void onTagClick(String tag) {
                            ArrayList<String> selectedNames = new ArrayList<>();
                            if(selectedDiners.isEmpty())
                                selDinLinearLayout.setVisibility(View.VISIBLE);

                            if(selectedDiners.containsKey(tag))
                                selectedDiners.put(tag, selectedDiners.get(tag)+1);
                            else
                                selectedDiners.put(tag, 1);

                            for (Map.Entry<String, Integer> entry : selectedDiners.entrySet()) {
                                selectedNames.add(entry.getValue() == 1 ? entry.getKey() : entry.getKey()+"("+entry.getValue()+")");
                            }

                            selectedTags.setTags(selectedNames);
                        }
                    });

                    selectedTags.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                        @Override
                        public void onTagClick(String tag) {
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
                        }
                    });

                    //Now we show the dialog
                    new MaterialStyledDialog.Builder(v.getContext())
                            .setTitle(v.getResources().getString(R.string.dialog_title, items[position].getName()))
                            //.setDescription("Has elegido el item "+position+", que es un "+items[position].getName())
                            .setPositiveText(R.string.dialog_accept)
                            .setNegativeText(R.string.dialog_cancel)
                            .setStyle(Style.HEADER_WITH_ICON)
                            .setIcon(R.drawable.diner)
                            .setHeaderColor(R.color.lightPurple)
                            .setCancelable(true)
                            .withIconAnimation(false)
                            .withDialogAnimation(true, Duration.FAST)
                            .setCustomView(tagView, 20, 10, 20, 10)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if(!selectedDiners.isEmpty())
                                    {
                                        for(Person person : viewModel.getClients().getValue())
                                        {
                                            if(selectedDiners.containsKey(person.getName()))
                                            {
                                                person.getItems().put(items[position], selectedDiners.get(person.getName()));
                                            }
                                        }
                                    }
                                }
                            })
                            .show();
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
