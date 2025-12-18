package org.example.mvc.controller;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.mvc.model.DriverModel;
import org.example.mvc.view.DriverDetailsView;
import org.example.observer.RepositoryObserver;

@WebServlet("/details")
public class DriverDetailsController extends HttpServlet
        implements RepositoryObserver {

    private final DriverModel model = new DriverModel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));

        model.addObserver(this);
        model.selectDriver(id);   // notifyObservers() внутри
        send(resp, DriverDetailsView.render(model.getSelectedDriver()));
    }

    @Override
    public void update() {
    }

    protected void send(HttpServletResponse resp, String html) {
        try {
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().write(html);
        } catch (Exception ignored) {}
    }
}
