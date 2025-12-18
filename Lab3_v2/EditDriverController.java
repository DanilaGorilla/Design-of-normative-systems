package org.example.mvc.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Driver;
import org.example.mvc.model.DriverModel;
import org.example.mvc.view.EditDriverView;

import java.io.IOException;

public class EditDriverController extends HttpServlet {

    private final DriverModel model = DriverModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        model.selectDriver(id);

        Driver driver = model.getSelectedDriver();
        send(resp, EditDriverView.renderForm(driver, null));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));

            String firstName = req.getParameter("firstName");
            String middleName = req.getParameter("middleName");
            String lastName = req.getParameter("lastName");
            String experienceStr = req.getParameter("experience");
            String paymentStr = req.getParameter("payment");

            validate(firstName, lastName, experienceStr, paymentStr);

            Driver updated = new Driver(
                    id,
                    firstName,
                    middleName,
                    lastName,
                    Integer.parseInt(experienceStr),
                    Double.parseDouble(paymentStr)
            );

            model.updateDriver(id, updated);

            // PRG — redirect
            resp.sendRedirect("/");

        } catch (Exception e) {
            int id = Integer.parseInt(req.getParameter("id"));
            Driver driver = model.getSelectedDriver();
            send(resp, EditDriverView.renderForm(driver, e.getMessage()));
        }
    }

    private void validate(
            String firstName,
            String lastName,
            String experience,
            String payment
    ) {
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("Имя не может быть пустым");

        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Фамилия не может быть пустой");

        int exp;
        double pay;

        try {
            exp = Integer.parseInt(experience);
            pay = Double.parseDouble(payment);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Опыт и оплата должны быть числами");
        }

        if (exp < 0)
            throw new IllegalArgumentException("Опыт не может быть отрицательным");

        if (pay < 0)
            throw new IllegalArgumentException("Оплата не может быть отрицательной");
    }

    private void send(HttpServletResponse resp, String html) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write(html);
    }
}
