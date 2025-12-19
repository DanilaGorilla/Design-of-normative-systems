package org.example.mvc.model;

import org.example.Driver;
import org.example.MyDriver_rep_DB_Adapter;
import org.example.observer.*;

import java.util.ArrayList;
import java.util.List;

public class DriverModel implements RepositorySubject {

    private static DriverModel instance;
    private final MyDriver_rep_DB_Adapter repository;
    private final List<RepositoryObserver> observers = new ArrayList<>();

    private Driver selectedDriver;

    public DriverModel() {
        this.repository = new MyDriver_rep_DB_Adapter(null);
    }
    public static synchronized DriverModel getInstance() {
        if (instance == null) {
            instance = new DriverModel();
        }
        return instance;
    }

    public boolean addDriver(Driver driver) {
        return repository.addDriver(driver);
    }
    public boolean updateDriver(int id, Driver driver) {
        boolean result = repository.replaceById(id, driver);
        if (result) {
            notifyObservers();
        }
        return result;
    }
    public boolean deleteDriver(int id) {
        boolean result = repository.deleteById(id);
        notifyObservers();
        return result;
    }

    public List<Driver> getPage(int k, int n) {
        return repository.get_k_n_short_list(k, n);
    }

    public void selectDriver(int id) {
        this.selectedDriver = repository.getById(id);
        notifyObservers();
    }

    public Driver getSelectedDriver() {
        return selectedDriver;
    }

    @Override
    public void removeObserver(RepositoryObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void addObserver(RepositoryObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (RepositoryObserver o : observers) {
            o.update();
        }
    }
}
