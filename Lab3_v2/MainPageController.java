package org.example.mvc.controller;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.mvc.model.DriverModel;
import org.example.mvc.view.MainPageView;

@WebServlet("/")
public class MainPageController extends HttpServlet {

    private final DriverModel model = new DriverModel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        var drivers = model.getPage(10, 1);
        send(resp, MainPageView.render(drivers));
    }

    protected void send(HttpServletResponse resp, String html) {
        try {
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().write(html);
        } catch (Exception ignored) {}
    }
}
