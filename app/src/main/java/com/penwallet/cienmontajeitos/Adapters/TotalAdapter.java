package com.penwallet.cienmontajeitos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.penwallet.cienmontajeitos.Entities.Item;
import com.penwallet.cienmontajeitos.Entities.Person;
import com.penwallet.cienmontajeitos.R;
import com.penwallet.cienmontajeitos.ui.viewmodel.SharedViewModel;

import java.text.DecimalFormat;
import java.util.Map;

public class TotalAdapter extends BaseExpandableListAdapter {

    private SharedViewModel viewModel;
    private Context context;

    public TotalAdapter(Context context, SharedViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    @Override
    public int getGroupCount() {
        return viewModel.getClients().getValue().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return viewModel.getClients().getValue().get(groupPosition).getItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return viewModel.getClients().getValue().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return viewModel.getClients().getValue().get(groupPosition).getItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Person person = viewModel.getClients().getValue().get(groupPosition);
        DecimalFormat df = new DecimalFormat("#0.00");

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.total_row_header, null);
        }

        TextView txtName = convertView.findViewById(R.id.total_row_header_name);
        TextView txtPrice = convertView.findViewById(R.id.total_row_header_price);

        txtName.setText(person.getName());
        txtPrice.setText(df.format(person.getTotalPriceItems(viewModel.getIsEuromania().getValue()))+" €");

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Map.Entry<Item, Integer> item = (Map.Entry<Item, Integer>)viewModel.getClients().getValue().get(groupPosition).getItems().entrySet().toArray()[childPosition];
        DecimalFormat df = new DecimalFormat("#0.00");

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.total_row_body, null);
        }

        TextView txtName = convertView.findViewById(R.id.total_row_body_txtName);
        TextView txtPrice = convertView.findViewById(R.id.total_row_body_txtPrice);
        TextView txtId = convertView.findViewById(R.id.total_row_body_txtId);
        ImageView image = convertView.findViewById(R.id.total_row_body_image);

        txtName.setText(item.getKey().getName() + (item.getValue() == 1 ? "" : " ("+item.getValue()+")"));
        txtPrice.setText(df.format((viewModel.getIsEuromania().getValue() ? item.getKey().getPriceEuromania() : item.getKey().getPrice())*item.getValue())+" €");

        //Dependiendo del tipo de item, se cambia la imagen y se esconde el ID
        switch(item.getKey().getMenuItemType())
        {
            case MONTADITO:
                txtId.setText(Integer.toString(item.getKey().getMenuId()));
                image.setImageResource(R.drawable.sandwich);
                break;
            case SALAD:
                txtId.setText(Integer.toString(item.getKey().getMenuId()));
                image.setImageResource(R.drawable.salad);
                break;
            case DESSERT:
                txtId.setText(Integer.toString(item.getKey().getMenuId()));
                image.setImageResource(R.drawable.dessert);
                break;
            case APPETIZER:
                txtId.setVisibility(View.GONE);
                image.setImageResource(R.drawable.appetizer);
                break;
            case DRINK:
                txtId.setVisibility(View.GONE);
                image.setImageResource(R.drawable.soda);
                break;
            case ALCOHOL:
                txtId.setVisibility(View.GONE);
                image.setImageResource(R.drawable.beer);
                break;
        }

        convertView.setOnLongClickListener(v -> {
            //Now we show the dialog
            new MaterialStyledDialog.Builder(v.getContext())
                    .setTitle(v.getResources().getString(R.string.total_dialog_title))
                    .setDescription(v.getResources().getString(R.string.total_dialog_description, item.getKey().getName(), viewModel.getClients().getValue().get(groupPosition).getName()))
                    .setPositiveText(R.string.total_dialog_accept)
                    .setNegativeText(R.string.total_dialog_cancel)
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setIcon(R.drawable.delete)
                    .setHeaderColor(R.color.errorRed)
                    .setCancelable(true)
                    .withIconAnimation(true)
                    .withDialogAnimation(true, Duration.FAST)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            viewModel.getTotalOrder().getValue().merge(item.getKey(), item.getValue(), Integer::sum);
                            viewModel.getClients().getValue().get(groupPosition).getItems().remove(item.getKey());
                            notifyDataSetChanged();
                        }
                    })
                    .show();
            return false;
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
