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

                    <div class="actions">
                        <button onclick="window.open('/add', '_blank')">
                            Добавить водителя
                        </button>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Фамилия</th>
                                <th>Опыт (лет)</th>
                                <th>Действия</th>
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
                    <td class="table-actions">

                        <a href="/details?id=%d" target="_blank">
                            Открыть
                        </a>

                        <a href="/edit?id=%d" target="_blank">
                            Редактировать
                        </a>

                        <form method="post" action="/delete" style="display:inline;">
                            <input type="hidden" name="id" value="%d">
                            <button type="submit" class="danger">
                                Удалить
                            </button>
                        </form>

                    </td>
                </tr>
            """.formatted(
                    d.getDriverId(),
                    d.getLastName(),
                    d.getExperience(),
                    d.getDriverId(),
                    d.getDriverId(),
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
