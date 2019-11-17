package com.penwallet.cienmontajeitos.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.penwallet.cienmontajeitos.Adapters.MenuItemAdapter;
import com.penwallet.cienmontajeitos.MenuItemsData;
import com.penwallet.cienmontajeitos.R;
import com.penwallet.cienmontajeitos.ui.viewmodel.SharedViewModel;

public class AppetizersFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_appetizers, container, false);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        recyclerView = view.findViewById(R.id.appetizers_recyclerlistview);

        //Setting up the recycler view
        adapter = new MenuItemAdapter(MenuItemsData.appetizers, sharedViewModel);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

}