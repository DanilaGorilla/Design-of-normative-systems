package org.example.mvc.view;

import org.example.Driver;
import java.util.Locale;

public class DriverFormView {

    public static String render(Driver driver, String errorMessage, String title) {

        boolean isEdit = driver != null;

        String errorBlock = errorMessage == null
                ? ""
                : "<div class='error'>" + errorMessage + "</div>";

        String paymentValue = isEdit
                ? String.format(Locale.US, "%.2f", driver.getPayment())
                : "";

        return """
            <!DOCTYPE html>
            <html lang="ru">
            <head>
                <meta charset="UTF-8">
                <title>%s</title>
                <link rel="stylesheet" href="/static/style.css">
            </head>
            <body>
                <div class="container">
                    <h1>%s</h1>
                    %s
                    <form method="post">
                        %s

                        <label>Имя</label>
                        <input name="firstName" value="%s" required>

                        <label>Отчество</label>
                        <input name="middleName" value="%s">

                        <label>Фамилия</label>
                        <input name="lastName" value="%s" required>

                        <label>Опыт (лет)</label>
                        <input name="experience" type="number" min="0" value="%s" required>

                        <label>Оплата</label>
                        <input name="payment" type="number" step="0.01" min="0" value="%s" required>

                        <button type="submit">Сохранить</button>
                    </form>
                </div>
            </body>
            </html>
        """.formatted(
                title,
                title,
                errorBlock,
                isEdit ? "<input type='hidden' name='id' value='" + driver.getDriverId() + "'>" : "",
                isEdit ? driver.getFirstName() : "",
                isEdit ? driver.getMiddleName() : "",
                isEdit ? driver.getLastName() : "",
                isEdit ? driver.getExperience() : "",
                paymentValue
        );
    }
}
