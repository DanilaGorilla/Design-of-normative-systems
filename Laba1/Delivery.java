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
        this.deliveryId = validatePositiveId(deliveryId, "deliveryId");
        this.routeId = validatePositiveId(routeId, "routeId");
        this.driverId = validatePositiveId(driverId, "driverId");
        this.departureDate = validateDepartureDate(departureDate);
        this.arrivalDate = validateArrivalDate(arrivalDate, departureDate);
    }

    private static int validatePositiveId(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " должен быть положительным числом");
        }
        return value;
    }
    private static LocalDate validateDepartureDate(LocalDate departureDate) {
        if (departureDate == null) {
            throw new IllegalArgumentException("departureDate не может быть пустой");
        }
        return departureDate;
    }

    private static LocalDate validateArrivalDate(LocalDate arrivalDate, LocalDate departureDate) {
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

    public int getRouteId() {
        return routeId;
    }

    public int getDriverId() {
        return driverId;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = validatePositiveId(deliveryId, "deliveryId");
    }

    public void setRouteId(int routeId) {
        this.routeId = validatePositiveId(routeId, "routeId");
    }

    public void setDriverId(int driverId) {
        this.driverId = validatePositiveId(driverId, "driverId");
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = validateDepartureDate(departureDate);
        if (this.arrivalDate != null) {
            this.arrivalDate = validateArrivalDate(this.arrivalDate, departureDate);
        }
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = validateArrivalDate(arrivalDate, this.departureDate);
    }
}
