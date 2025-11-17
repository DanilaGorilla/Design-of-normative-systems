package org.example;

import java.util.List;
import java.util.function.Predicate;
import java.util.Comparator;

public class DBFilterSortDriverRepositoryDecorator implements DriverRepository {
    private final MyDriver_rep_DB_Adapter decoratedRepository;

    public DBFilterSortDriverRepositoryDecorator(MyDriver_rep_DB_Adapter repository) {
        this.decoratedRepository = repository;
    }

    @Override
    public Driver getById(int id) {
        return decoratedRepository.getById(id);
    }

    @Override
    public List<Driver> get_k_n_short_list(int k, int n) {
        return decoratedRepository.get_k_n_short_list(k, n);
    }

    @Override
    public List<Driver> get_k_n_short_list(int k, int n, Predicate<Driver> filter, Comparator<Driver> sorter) {
        List<Driver> allDrivers = decoratedRepository.get_k_n_short_list(1000, 1);

        // Применяем фильтр и сортировку в памяти
        List<Driver> filteredDrivers = allDrivers;

        if (filter != null) {
            filteredDrivers = allDrivers.stream()
                    .filter(filter)
                    .toList();
        }

        List<Driver> sortedDrivers = filteredDrivers;
        if (sorter != null) {
            sortedDrivers = filteredDrivers.stream()
                    .sorted(sorter)
                    .toList();
        }

        int start = (n - 1) * k;
        int end = Math.min(start + k, sortedDrivers.size());

        if (start >= sortedDrivers.size()) {
            return List.of();
        }

        return sortedDrivers.subList(start, end);
    }

    @Override
    public boolean addDriver(Driver driver) {
        return decoratedRepository.addDriver(driver);
    }

    @Override
    public boolean replaceById(int id, Driver newDriver) {
        return decoratedRepository.replaceById(id, newDriver);
    }

    @Override
    public boolean deleteById(int id) {
        return decoratedRepository.deleteById(id);
    }

    @Override
    public int getCount() {
        return decoratedRepository.getCount();
    }

    @Override
    public int getCount(Predicate<Driver> filter) {
        List<Driver> allDrivers = decoratedRepository.get_k_n_short_list(1000, 1);
        if (filter == null) {
            return allDrivers.size();
        }
        return (int) allDrivers.stream().filter(filter).count();
    }

    @Override
    public void printAll() {
        decoratedRepository.printAll();
    }


}
