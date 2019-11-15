package com.penwallet.cienmontajeitos.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.penwallet.cienmontajeitos.Adapters.MenuItemAdapter;
import com.penwallet.cienmontajeitos.Entities.Item;
import com.penwallet.cienmontajeitos.MenuItemType;
import com.penwallet.cienmontajeitos.MenuItemsData;
import com.penwallet.cienmontajeitos.R;
import com.penwallet.cienmontajeitos.ui.viewmodel.SharedViewModel;

import java.util.ArrayList;

public class AppetizersFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_appetizers, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        recyclerView = getActivity().findViewById(R.id.appetizers_recyclerlistview);

        //Setting up the recycler view
        adapter = new MenuItemAdapter(MenuItemsData.appetizers, MenuItemType.APPETIZER, sharedViewModel);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}