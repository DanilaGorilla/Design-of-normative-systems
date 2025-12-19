package org.example.mvc.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mvc.model.DriverModel;

import java.io.IOException;

public class DeleteDriverController extends HttpServlet {

    private final DriverModel model = DriverModel.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        model.deleteDriver(id);
        resp.sendRedirect("/");
    }
}
