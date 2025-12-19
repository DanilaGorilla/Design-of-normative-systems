package org.example.mvc.model;

import org.example.Driver;
import org.example.DriverRepository;
import org.example.MyDriver_rep_DB_Adapter;
import org.example.FilterSortDriverRepositoryDecorator;
import org.example.observer.RepositoryObserver;
import org.example.observer.RepositorySubject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DriverModel implements RepositorySubject {

    private static DriverModel instance;

    private final DriverRepository repository;
    private final List<RepositoryObserver> observers = new ArrayList<>();

    private Driver selectedDriver;

    private DriverModel() {

        MyDriver_rep_DB_Adapter baseRepo =
                new MyDriver_rep_DB_Adapter(null);

        this.repository =
                new FilterSortDriverRepositoryDecorator(baseRepo);
    }
    public static synchronized DriverModel getInstance() {
        if (instance == null) {
            instance = new DriverModel();
        }
        return instance;
    }


    public boolean addDriver(Driver driver) {
        boolean result = repository.addDriver(driver);
        if (result) {
            notifyObservers();
        }
        return result;
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
        if (result) {
            notifyObservers();
        }
        return result;
    }

    public List<Driver> getPage(int k, int n) {
        return repository.get_k_n_short_list(k, n);
    }

    public List<Driver> getFilteredPage(
            int k,
            int n,
            Predicate<Driver> filter
    ) {
        return repository.get_k_n_short_list(k, n, filter, null);
    }

    public void selectDriver(int id) {
        this.selectedDriver = repository.getById(id);
        notifyObservers();
    }

    public Driver getSelectedDriver() {
        return selectedDriver;
    }

    @Override
    public void addObserver(RepositoryObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(RepositoryObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (RepositoryObserver o : observers) {
            o.update();
        }
    }
}
