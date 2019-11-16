package com.penwallet.cienmontajeitos.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.penwallet.cienmontajeitos.Adapters.MenuItemAdapter;
import com.penwallet.cienmontajeitos.Adapters.TotalAdapter;
import com.penwallet.cienmontajeitos.MenuItemType;
import com.penwallet.cienmontajeitos.MenuItemsData;
import com.penwallet.cienmontajeitos.R;
import com.penwallet.cienmontajeitos.ui.viewmodel.SharedViewModel;

public class TotalFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_total, container, false);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        ExpandableListView expandableListView = view.findViewById(R.id.total_expandableList);

        expandableListView.setAdapter(new TotalAdapter(getContext(), sharedViewModel));

        return view;
    }
}