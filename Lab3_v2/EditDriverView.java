package org.example.mvc.view;

import org.example.Driver;

import java.util.Locale;

public class EditDriverView {

    public static String renderForm(Driver d, String error) {

        String errorBlock = "";
        if (error != null) {
            errorBlock = "<div class='error'>" + error + "</div>";
        }

        String paymentValue = String.format(
                Locale.US,
                "%.2f",
                d.getPayment()
        );

        return """
            <!DOCTYPE html>
            <html lang="ru">
            <head>
                <meta charset="UTF-8">
                <title>Редактировать водителя</title>
                <link rel="stylesheet" href="/static/style.css">
            </head>
            <body>
                <div class="container">
                    <h1>Редактировать водителя</h1>
                    %s
                    <form method="post">
                        <input type="hidden" name="id" value="%d">

                        <label>Имя</label>
                        <input name="firstName" value="%s" required>

                        <label>Отчество</label>
                        <input name="middleName" value="%s">

                        <label>Фамилия</label>
                        <input name="lastName" value="%s" required>

                        <label>Опыт (лет)</label>
                        <input name="experience" type="number" value="%d" min="0" required>

                        <label>Оплата</label>
                        <input name="payment"
                               type="number"
                               step="0.01"
                               min="0"
                               value="%s"
                               required>

                        <button type="submit">Сохранить</button>
                    </form>
                </div>
            </body>
            </html>
        """.formatted(
                errorBlock,
                d.getDriverId(),
                d.getFirstName(),
                d.getMiddleName(),
                d.getLastName(),
                d.getExperience(),
                paymentValue
        );
    }
}
