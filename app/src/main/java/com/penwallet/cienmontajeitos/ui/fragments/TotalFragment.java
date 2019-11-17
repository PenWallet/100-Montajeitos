package com.penwallet.cienmontajeitos.ui.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.penwallet.cienmontajeitos.Adapters.MenuItemAdapter;
import com.penwallet.cienmontajeitos.Adapters.TotalAdapter;
import com.penwallet.cienmontajeitos.Entities.Item;
import com.penwallet.cienmontajeitos.Entities.Person;
import com.penwallet.cienmontajeitos.MenuItemType;
import com.penwallet.cienmontajeitos.MenuItemsData;
import com.penwallet.cienmontajeitos.R;
import com.penwallet.cienmontajeitos.ui.viewmodel.SharedViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TotalFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_total, container, false);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        ExpandableListView expandableListView = view.findViewById(R.id.total_expandableList);
        expandableListView.setAdapter(new TotalAdapter(getContext(), sharedViewModel));

        Spinner spinner = view.findViewById(R.id.total_spinner);
        String[] spinnerChoices = new String[]{
                getContext().getResources().getString(R.string.total_spinner_individual),
                getContext().getResources().getString(R.string.total_spinner_collective)
        };

        TextView txtCollective = view.findViewById(R.id.total_txtCollective);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0)
                {
                    expandableListView.setVisibility(View.VISIBLE);
                    txtCollective.setVisibility(View.GONE);
                }
                else
                {
                    txtCollective.setText(formFullOrderText());
                    expandableListView.setVisibility(View.GONE);
                    txtCollective.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerChoices);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        return view;
    }

    private String formFullOrderText()
    {
        StringBuilder fullOrderText = new StringBuilder();

        HashMap<Item, Integer> appetizers = new HashMap<>();
        HashMap<Item, Integer> montaditos = new HashMap<>();
        HashMap<Item, Integer> salads = new HashMap<>();
        HashMap<Item, Integer> drinks = new HashMap<>();
        HashMap<Item, Integer> alcohol = new HashMap<>();
        HashMap<Item, Integer> desserts = new HashMap<>();
        double fullPrice = 0;
        DecimalFormat df = new DecimalFormat("#0.00");

        for(Map.Entry<Item, Integer> item : sharedViewModel.getTotalOrder().getValue().entrySet())
        {
            switch (item.getKey().getMenuItemType())
            {
                case MONTADITO:
                    montaditos.merge(item.getKey(), item.getValue(), Integer::sum);
                    break;
                case SALAD:
                    salads.merge(item.getKey(), item.getValue(), Integer::sum);
                    break;
                case DESSERT:
                    desserts.merge(item.getKey(), item.getValue(), Integer::sum);
                    break;
                case APPETIZER:
                    appetizers.merge(item.getKey(), item.getValue(), Integer::sum);
                    break;
                case DRINK:
                    drinks.merge(item.getKey(), item.getValue(), Integer::sum);
                    break;
                case ALCOHOL:
                    alcohol.merge(item.getKey(), item.getValue(), Integer::sum);
                    break;
            }
        }

        if(!appetizers.isEmpty())
        {
            fullOrderText.append(getContext().getResources().getString(R.string.total_collective_appetizers_caps)+":\n");
            for(Map.Entry<Item, Integer> item : appetizers.entrySet())
            {
                fullOrderText.append("\t"+item.getValue()+"x "+item.getKey().getName()+"\n");
                fullPrice += (sharedViewModel.getIsEuromania().getValue() ? item.getKey().getPriceEuromania() : item.getKey().getPrice()) * item.getValue();
            }
        }

        if(!montaditos.isEmpty())
        {
            fullOrderText.append(getContext().getResources().getString(R.string.total_collective_montaditos_caps)+":\n");
            for(Map.Entry<Item, Integer> item : montaditos.entrySet())
            {
                fullOrderText.append("\t"+item.getValue()+"x " + item.getKey().getMenuId() + " (" + item.getKey().getName()+")\n");
                fullPrice += (sharedViewModel.getIsEuromania().getValue() ? item.getKey().getPriceEuromania() : item.getKey().getPrice()) * item.getValue();
            }
        }

        if(!salads.isEmpty())
        {
            fullOrderText.append(getContext().getResources().getString(R.string.total_collective_salads_caps)+":\n");
            for(Map.Entry<Item, Integer> item : salads.entrySet())
            {
                fullOrderText.append("\t"+item.getValue()+"x " + item.getKey().getMenuId() + " (" + item.getKey().getName()+")\n");
                fullPrice += (sharedViewModel.getIsEuromania().getValue() ? item.getKey().getPriceEuromania() : item.getKey().getPrice()) * item.getValue();
            }
        }

        if(!drinks.isEmpty())
        {
            fullOrderText.append(getContext().getResources().getString(R.string.total_collective_drinks_caps)+":\n");
            for(Map.Entry<Item, Integer> item : drinks.entrySet())
            {
                fullOrderText.append("\t"+item.getValue()+"x "+item.getKey().getName()+"\n");
                fullPrice += (sharedViewModel.getIsEuromania().getValue() ? item.getKey().getPriceEuromania() : item.getKey().getPrice()) * item.getValue();
            }
        }

        if(!alcohol.isEmpty())
        {
            fullOrderText.append(getContext().getResources().getString(R.string.total_collective_alcohol_caps)+":\n");
            for(Map.Entry<Item, Integer> item : alcohol.entrySet())
            {
                fullOrderText.append("\t"+item.getValue()+"x "+item.getKey().getName()+"\n");
                fullPrice += (sharedViewModel.getIsEuromania().getValue() ? item.getKey().getPriceEuromania() : item.getKey().getPrice()) * item.getValue();
            }
        }

        if(!desserts.isEmpty())
        {
            fullOrderText.append(getContext().getResources().getString(R.string.total_collective_alcohol_caps)+":\n");
            for(Map.Entry<Item, Integer> item : desserts.entrySet())
            {
                fullOrderText.append("\t"+item.getValue()+"x "+item.getKey().getName()+"\n");
                fullPrice += (sharedViewModel.getIsEuromania().getValue() ? item.getKey().getPriceEuromania() : item.getKey().getPrice()) * item.getValue();
            }
        }

        fullOrderText.append("\n\n"+getContext().getResources().getString(R.string.total_collective_total_caps)+": "+df.format(fullPrice)+" €");

        return fullOrderText.toString();
    }
}