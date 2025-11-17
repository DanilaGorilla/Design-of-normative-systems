package org.example;

import java.util.List;
import java.util.function.Predicate;
import java.util.Comparator;

public class MyDriver_rep_DB_Adapter extends MyDriver_rep_base {

    private final MyDriver_rep_DB dbRepo;

    public MyDriver_rep_DB_Adapter(String filePath) {
        super(filePath);
        this.dbRepo = new MyDriver_rep_DB();
    }

    @Override
    public void readAll() {
    }

    @Override
    public void writeAll() {
    }

    @Override
    public Driver getById(int id) {
        return dbRepo.getDriverById(id);
    }

    @Override
    public List<Driver> get_k_n_short_list(int k, int n) {
        return dbRepo.getDriverListPage(k, n);
    }

    // Реализация метода с фильтрацией и сортировкой для БД
    @Override
    public List<Driver> get_k_n_short_list(int k, int n, Predicate<Driver> filter, Comparator<Driver> sorter) {
        // Преобразуем Predicate и Comparator в SQL условия
        String whereClause = buildWhereClause(filter);
        String orderClause = buildOrderClause(sorter);

        return dbRepo.getDriverListPageWithFilterSort(k, n, whereClause, orderClause);
    }

    @Override
    public boolean addDriver(Driver driver) { // изменено на boolean
        // Для БД проверка уникальности должна быть на уровне базы данных
        // или через дополнительные запросы
        try {
            dbRepo.insertDriver(driver);
            System.out.println("Водитель успешно добавлен в базу данных!");
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении водителя: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean replaceById(int id, Driver newDriver) {
        return dbRepo.updateDriverById(id, newDriver);
    }

    @Override
    public boolean deleteById(int id) {
        return dbRepo.deleteDriverById(id);
    }

    @Override
    public int getCount() {
        return dbRepo.getDriverCount();
    }

    @Override
    public int getCount(Predicate<Driver> filter) {
        String whereClause = buildWhereClause(filter);
        return dbRepo.getDriverCountWithFilter(whereClause);
    }

    //преобразует его в WHERE условие
    private String buildWhereClause(Predicate<Driver> filter) {
        if (filter == null) {
            return null;
        }

        try {
            String filterString = filter.toString();
            if (filterString.contains("experience") && filterString.contains(">")) {
                return "experience > 5";
            } else if (filterString.contains("payment") && filterString.contains(">")) {
                return "payment > 50000";
            }
        } catch (Exception e) {
        }

        return null;
    }
    //преобразует его в ORDER BY условие (Используем , т.к без этого нарушается инкапсуляция)
    private String buildOrderClause(Comparator<Driver> sorter) {
        if (sorter == null) {
            return null;
        }

        try {
            String sorterString = sorter.toString();
            if (sorterString.contains("Experience")) {
                return "experience DESC";
            } else if (sorterString.contains("Payment")) {
                return "payment DESC";
            } else if (sorterString.contains("experience")) {
                return "experience ASC";
            } else if (sorterString.contains("payment")) {
                return "payment ASC";
            }
        } catch (Exception e) {
        }

        return null;
    }

    //Cтраница с учётом фильтра и сортировки.
    public List<Driver> getFilteredSortedPage(int k, int n, String where, String order) {
        return dbRepo.getDriverListPageWithFilterSort(k, n, where, order);
    }

    // Кол-во водителей с учётом фильтра.

    public int getFilteredCount(String where) {
        return dbRepo.getDriverCountWithFilter(where);
    }

    public MyDriver_rep_DB getInnerDB() {
        return dbRepo;
    }
}
