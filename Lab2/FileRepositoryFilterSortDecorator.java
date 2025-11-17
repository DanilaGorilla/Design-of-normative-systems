package org.example;

import java.util.List;
import java.util.function.Predicate;
import java.util.Comparator;
import java.util.ArrayList;

public class FileRepositoryFilterSortDecorator implements DriverRepository {
    private final DriverRepository decoratedRepository;

    public FileRepositoryFilterSortDecorator(DriverRepository repository) {
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
    public void printAll() {
        decoratedRepository.printAll();
    }

    @Override
    public List<Driver> get_k_n_short_list(int k, int n, Predicate<Driver> filter, Comparator<Driver> sorter) {
        // Получаем все данные из репозитория
        List<Driver> allDrivers = decoratedRepository.get_k_n_short_list(Integer.MAX_VALUE, 1);

        // Применяем фильтр если он задан
        List<Driver> filteredDrivers = allDrivers;
        if (filter != null) {
            filteredDrivers = new ArrayList<>();
            for (Driver driver : allDrivers) {
                if (filter.test(driver)) {
                    filteredDrivers.add(driver);
                }
            }
        }

        //сортировка (если задана)
        List<Driver> sortedDrivers = filteredDrivers;
        if (sorter != null) {
            sortedDrivers = new ArrayList<>(filteredDrivers);
            sortedDrivers.sort(sorter);
        }

        //пагинация
        int start = (n - 1) * k;
        int end = Math.min(start + k, sortedDrivers.size());

        if (start >= sortedDrivers.size()) {
            return new ArrayList<>();
        }

        return sortedDrivers.subList(start, end);
    }

    @Override
    public int getCount(Predicate<Driver> filter) {
        List<Driver> allDrivers = decoratedRepository.get_k_n_short_list(Integer.MAX_VALUE, 1);

        if (filter == null) {
            return allDrivers.size();
        }

        // Подсчитываем количество отфильтрованных записей
        int count = 0;
        for (Driver driver : allDrivers) {
            if (filter.test(driver)) {
                count++;
            }
        }
        return count;
    }
}
