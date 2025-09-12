import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            Delivery delivery1 = new Delivery(
                    1,
                    10,
                    100,
                    LocalDate.of(2025, 9, 12),
                    LocalDate.of(2025, 9, 15)
            );
            System.out.println("Успешно создан Delivery:");
            System.out.println("deliveryId = " + delivery1.getDeliveryId());
            System.out.println("routeId = " + delivery1.getRouteId());
            System.out.println("driverId = " + delivery1.getDriverId());
            System.out.println("departureDate = " + delivery1.getDepartureDate());
            System.out.println("arrivalDate = " + delivery1.getArrivalDate());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при создании Delivery: " + e.getMessage());
        }

        System.out.println("\n---------------------------\n");

        try {
            Delivery delivery2 = new Delivery(
                    -5,
                    10,
                    100,
                    LocalDate.of(2025, 9, 12),
                    LocalDate.of(2025, 9, 15)
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Ожидаемая ошибка: " + e.getMessage());
        }

        System.out.println("\n---------------------------\n");

        try {
            Delivery delivery3 = new Delivery(
                    2,
                    11,
                    101,
                    LocalDate.of(2025, 9, 15),
                    LocalDate.of(2025, 9, 12)
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Ожидаемая ошибка: " + e.getMessage());
        }
    }
}
