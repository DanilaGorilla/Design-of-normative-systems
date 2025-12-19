package org.example.mvc.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Driver;
import org.example.mvc.model.DriverModel;
import org.example.mvc.view.MainPageView;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class FilterDriverController extends HttpServlet {

    private final DriverModel model = DriverModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String type = req.getParameter("type");
        Predicate<Driver> filter = null;

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

        List<Driver> drivers = (filter == null)
                ? model.getPage(10, 1)
                : model.getFilteredPage(10, 1, filter);

        send(resp, MainPageView.render(drivers, type));
    }

    private void send(HttpServletResponse resp, String html) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write(html);
    }
}
