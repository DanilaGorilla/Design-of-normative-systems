package org.example.mvc.controller;

import jakarta.servlet.http.*;
import org.example.Driver;
import org.example.mvc.model.DriverModel;
import org.example.mvc.view.MainPageView;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class FilterDriverController extends HttpServlet {

    private final DriverModel model = DriverModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String type = req.getParameter("type");
        String sort = req.getParameter("sort");

        Predicate<Driver> filter = null;
        Comparator<Driver> comparator = null;

        //Фильтр
        if (type != null) {
            switch (type) {
                case "exp_gt_3" ->
                        filter = d -> d.getExperience() > 3;
                case "salary_5000" ->
                        filter = d -> d.getPayment() == 5000;
                case "lastname_b" ->
                        filter = d -> d.getLastName() != null &&
                                d.getLastName().contains("Б");
            }
        }

        //Сортировка
        if (sort != null) {
            switch (sort) {
                case "exp_asc" ->
                        comparator = Comparator.comparingInt(Driver::getExperience);
                case "exp_desc" ->
                        comparator = Comparator.comparingInt(Driver::getExperience).reversed();
                case "pay_asc" ->
                        comparator = Comparator.comparingDouble(Driver::getPayment);
                case "pay_desc" ->
                        comparator = Comparator.comparingDouble(Driver::getPayment).reversed();
            }
        }

        List<Driver> drivers =
                model.getFilteredSortedPage(10, 1, filter, comparator);

        send(resp, MainPageView.render(drivers, type, sort));
    }

    private void send(HttpServletResponse resp, String html) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write(html);
    }
}
