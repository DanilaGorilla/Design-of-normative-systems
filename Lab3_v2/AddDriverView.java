package org.example.mvc.view;

public class AddDriverView {

    public static String renderForm(String errorMessage) {
        String errorBlock = "";

        if (errorMessage != null) {
            errorBlock = """
                    <div class="error">%s</div>
                    """.formatted(errorMessage);
        }

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Добавить водителя</title>
                    <link rel="stylesheet" href="/static/style.css">
                </head>
                <body>
                    <div class="container">
                        <h1>Добавить водителя</h1>
                        %s
                        <form method="post">
                            <label>Имя</label>
                            <input name="firstName" required>

                            <label>Отчество</label>
                            <input name="middleName">

                            <label>Фамилия</label>
                            <input name="lastName" required>

                            <label>Опыт (лет)</label>
                            <input name="experience" type="number" min="0" required>

                            <label>Оплата</label>
                            <input name="payment" type="number" step="0.01" min="0" required>

                            <button type="submit">Сохранить</button>
                        </form>
                    </div>
                </body>
                </html>
                """.formatted(errorBlock);
    }
}
