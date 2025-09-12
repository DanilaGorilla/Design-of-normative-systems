import java.time.LocalDate;

public class Delivery {
    private int deliveryId;
    private int routeId;
    private int driverId;
    private LocalDate departureDate;
    private LocalDate arrivalDate;

    private Delivery() {
    }

    public Delivery(int deliveryId, int routeId, int driverId, LocalDate departureDate, LocalDate arrivalDate) {
        this.deliveryId = validateDeliveryId(deliveryId);
        this.routeId = validateRouteId(routeId);
        this.driverId = validateDriverId(driverId);
        this.departureDate = validateDepartureDate(departureDate);
        this.arrivalDate = validateArrivalDate(arrivalDate, departureDate);
    }

    public static int validateDeliveryId(int deliveryId) {
        if (deliveryId <= 0) {
            throw new IllegalArgumentException("deliveryId должен быть положительным числом");
        }
        return deliveryId;
    }

    public static int validateRouteId(int routeId) {
        if (routeId <= 0) {
            throw new IllegalArgumentException("routeId должен быть положительным числом");
        }
        return routeId;
    }

    public static int validateDriverId(int driverId) {
        if (driverId <= 0) {
            throw new IllegalArgumentException("driverId должен быть положительным числом");
        }
        return driverId;
    }

    public static LocalDate validateDepartureDate(LocalDate departureDate) {
        if (departureDate == null) {
            throw new IllegalArgumentException("departureDate не может быть пустой");
        }
        return departureDate;
    }

    public static LocalDate validateArrivalDate(LocalDate arrivalDate, LocalDate departureDate) {
        if (arrivalDate == null) {
            throw new IllegalArgumentException("arrivalDate не может быть пустой");
        }
        if (departureDate != null && arrivalDate.isBefore(departureDate)) {
            throw new IllegalArgumentException("arrivalDate не может быть раньше departureDate");
        }
        return arrivalDate;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

}
