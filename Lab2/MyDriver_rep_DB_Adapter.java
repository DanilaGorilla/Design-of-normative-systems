package org.example;

import java.util.List;

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

    @Override
    public void addDriver(Driver driver) {
        dbRepo.insertDriver(driver);
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
}
