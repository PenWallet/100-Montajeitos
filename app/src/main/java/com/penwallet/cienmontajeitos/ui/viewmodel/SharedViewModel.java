package com.penwallet.cienmontajeitos.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.penwallet.cienmontajeitos.Entities.Item;
import com.penwallet.cienmontajeitos.Entities.Person;

import java.util.ArrayList;
import java.util.HashMap;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<Boolean> isEuromania;
    private MutableLiveData<ArrayList<Person>> clients;

    public SharedViewModel() {
        isEuromania = new MutableLiveData<>(); isEuromania.setValue(true);
        clients = new MutableLiveData<>();

        clients.setValue(new ArrayList<Person>());
        clients.getValue().add(new Person("Oscar Prueba", new HashMap<Item, Integer>()));
    }

    public MutableLiveData<Boolean> getIsEuromania() {
        return isEuromania;
    }

    public MutableLiveData<ArrayList<Person>> getClients() {
        return clients;
    }
}