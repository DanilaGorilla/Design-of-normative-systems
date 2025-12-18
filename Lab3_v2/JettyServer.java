package org.example.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.example.mvc.controller.MainPageController;
import org.example.mvc.controller.DriverDetailsController;

public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        // Контекст для сервлетов
        ServletContextHandler servletContext = new ServletContextHandler();
        servletContext.setContextPath("/");
        servletContext.addServlet(MainPageController.class, "/");
        servletContext.addServlet(DriverDetailsController.class, "/details");

        // Контекст для статических ресурсов
        ServletContextHandler staticContext = new ServletContextHandler();
        staticContext.setContextPath("/static");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("src/main/resources/static");
        resourceHandler.setDirectoriesListed(false);

        // Более простой вариант - используйте DefaultServlet для статики
        staticContext.addServlet(DefaultServlet.class, "/");
        staticContext.setHandler(resourceHandler);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.addHandler(servletContext);
        contexts.addHandler(staticContext);

        server.setHandler(contexts);
        server.start();
        server.join();
    }
}
