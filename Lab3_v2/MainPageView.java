package org.example.mvc.view;

import org.example.Driver;
import java.util.List;

public class MainPageView {

    public static String render(
            List<Driver> drivers,
            String selectedFilter,
            String selectedSort
    ) {

        //selected для ФИЛЬТРА
        String expSelected = "exp_gt_3".equals(selectedFilter) ? "selected" : "";
        String salarySelected = "salary_5000".equals(selectedFilter) ? "selected" : "";
        String lastNameSelected = "lastname_b".equals(selectedFilter) ? "selected" : "";

        //selected для СОРТИРОВКИ
        String expAsc = "exp_asc".equals(selectedSort) ? "selected" : "";
        String expDesc = "exp_desc".equals(selectedSort) ? "selected" : "";
        String payAsc = "pay_asc".equals(selectedSort) ? "selected" : "";
        String payDesc = "pay_desc".equals(selectedSort) ? "selected" : "";

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

                    <!ФИЛЬТР + СОРТИРОВКА>
                    <form method="get" action="/filter" class="filter-form">

                        <label>Фильтр:</label>
                        <select name="type">
                            <option value="">Выбрать</option>
                            <option value="exp_gt_3" %s>Опыт больше 3 лет</option>
                            <option value="salary_5000" %s>Зарплата = 5000</option>
                            <option value="lastname_b" %s>Фамилия содержит «Б»</option>
                        </select>

                        <label>Сортировка:</label>
                        <select name="sort">
                            <option value="">Без сортировки</option>
                            <option value="exp_asc" %s>Опыт ↑</option>
                            <option value="exp_desc" %s>Опыт ↓</option>
                            <option value="pay_asc" %s>Зарплата ↑</option>
                            <option value="pay_desc" %s>Зарплата ↓</option>
                        </select>

                        <button type="submit">Применить</button>
                    </form>

                    <!КНОПКА ДОБАВЛЕНИЯ>
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
        """.formatted(
                expSelected,
                salarySelected,
                lastNameSelected,
                expAsc,
                expDesc,
                payAsc,
                payDesc
        ));

        //строки таблицы
        for (Driver d : drivers) {
            html.append("""
                <tr>
                    <td>%d</td>
                    <td>%s</td>
                    <td>%d</td>
                    <td class="actions-cell">
                        <a href="/details?id=%d" target="_blank">Открыть</a>
                        <a href="/edit?id=%d" target="_blank">Редактировать</a>
                        <form method="post" action="/delete" style="display:inline;">
                            <input type="hidden" name="id" value="%d">
                            <button type="submit"
                                    onclick="return confirm('Удалить водителя %s ?');">
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
                    d.getDriverId(),
                    d.getLastName()
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
