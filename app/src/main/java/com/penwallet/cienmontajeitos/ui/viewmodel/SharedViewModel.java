package com.penwallet.cienmontajeitos.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.penwallet.cienmontajeitos.Entities.Item;
import com.penwallet.cienmontajeitos.Entities.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<Boolean> isEuromania;
    private MutableLiveData<ArrayList<Person>> clients;

    public SharedViewModel() {
        isEuromania = new MutableLiveData<>(); isEuromania.setValue(true);
        clients = new MutableLiveData<>();

        clients.setValue(new ArrayList<Person>());
        clients.getValue().add(new Person("Oscar", new HashMap<Item, Integer>()));
        clients.getValue().add(new Person("Miguel", new HashMap<Item, Integer>()));
        clients.getValue().add(new Person("Olga", new HashMap<Item, Integer>()));
        clients.getValue().add(new Person("Fran", new HashMap<Item, Integer>()));
        clients.getValue().add(new Person("Triana", new HashMap<Item, Integer>()));
    }

    public MutableLiveData<Boolean> getIsEuromania() {
        return isEuromania;
    }

    public MutableLiveData<ArrayList<Person>> getClients() {
        return clients;
    }

    public ArrayList<String> getClientsNames()
    {
        ArrayList<String> clientsNames = new ArrayList<>();
        for(Person client : clients.getValue())
        {
            clientsNames.add(client.getName());
        }

        return clientsNames;
    }
}