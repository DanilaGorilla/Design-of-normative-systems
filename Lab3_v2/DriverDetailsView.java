package org.example.mvc.view;

import org.example.Driver;

public class DriverDetailsView {

    public static String render(Driver d) {

        if (d == null) {
            return """
                <!DOCTYPE html>
                <html lang="ru">
                <head>
                    <meta charset="UTF-8">
                    <title>Ошибка</title>
                    <link rel="stylesheet" href="/static/style.css">
                </head>
                <body>
                    <div class="container">
                        <h1>Водитель не найден</h1>
                    </div>
                </body>
                </html>
            """;
        }

        return """
            <!DOCTYPE html>
            <html lang="ru">
            <head>
                <meta charset="UTF-8">
                <title>Информация о водителе</title>
                <link rel="stylesheet" href="/static/style.css">
            </head>
            <body>
                <div class="container">
                    <h1>Полная информация о водителе</h1>

                    <ul class="details-list">
                        <li><strong>ID:</strong> %d</li>
                        <li><strong>Фамилия:</strong> %s</li>
                        <li><strong>Имя:</strong> %s</li>
                        <li><strong>Отчество:</strong> %s</li>
                        <li><strong>Опыт (лет):</strong> %d</li>
                        <li><strong>Оплата:</strong> %.2f</li>
                    </ul>
                </div>
            </body>
            </html>
        """.formatted(
                d.getDriverId(),
                d.getLastName(),
                d.getFirstName(),
                d.getMiddleName(),
                d.getExperience(),
                d.getPayment()
        );
    }
}
