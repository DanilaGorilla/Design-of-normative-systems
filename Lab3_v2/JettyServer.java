package org.example.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import org.example.mvc.controller.MainPageController;
import org.example.mvc.controller.DriverDetailsController;
import org.example.mvc.controller.AddDriverController;
import org.example.mvc.controller.EditDriverController;

public class JettyServer {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);

        ServletContextHandler servletContext =
                new ServletContextHandler(ServletContextHandler.SESSIONS);

        servletContext.setContextPath("/");

        servletContext.addServlet(MainPageController.class, "/");
        servletContext.addServlet(DriverDetailsController.class, "/details");
        servletContext.addServlet(AddDriverController.class, "/add");
        servletContext.addServlet(EditDriverController.class, "/edit");
        ServletContextHandler staticContext = new ServletContextHandler();
        staticContext.setContextPath("/static");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("src/main/resources/static");
        resourceHandler.setDirectoriesListed(false);

        staticContext.setHandler(resourceHandler);
        staticContext.addServlet(DefaultServlet.class, "/");
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.addHandler(servletContext);
        contexts.addHandler(staticContext);

        server.setHandler(contexts);

        server.start();
        server.join();
    }
}
