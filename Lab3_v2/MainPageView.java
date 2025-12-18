package org.example.mvc.view;

import org.example.Driver;
import java.util.List;

public class MainPageView {

    public static String render(List<Driver> drivers) {

        StringBuilder html = new StringBuilder("""
            <!DOCTYPE html>
            <html lang="ru">
            <head>
                <meta charset="UTF-8">
                <title>Список водителей</title>
                <link rel="stylesheet" href="/static/style.css">
            </head>
            <body>
                <div class="container">
                    <h1>Список водителей</h1>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Фамилия</th>
                                <th>Опыт (лет)</th>
                                <th>Детали</th>
                            </tr>
                        </thead>
                        <tbody>
        """);

        for (Driver d : drivers) {
            html.append("""
                            <tr>
                                <td>%d</td>
                                <td>%s</td>
                                <td>%d</td>
                                <td>
                                    <a href="/details?id=%d" target="_blank">
                                        Открыть
                                    </a>
                                </td>
                            </tr>
            """.formatted(
                    d.getDriverId(),
                    d.getLastName(),
                    d.getExperience(),
                    d.getDriverId()
            ));
        }

        html.append("""
                        </tbody>
                    </table>
                </div>
            </body>
            </html>
        """);

        return html.toString();
    }
}
