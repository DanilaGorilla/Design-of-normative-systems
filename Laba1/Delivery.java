import java.time.LocalDate;

public class Delivery {
    private int deliveryId;
    private int routeId;
    private int driverId;
    private LocalDate departureDate;
    private LocalDate arrivalDate;

    public Delivery(int deliveryId, int routeId, int driverId, LocalDate departureDate, LocalDate arrivalDate) {
        this.deliveryId = deliveryId;
        this.routeId = routeId;
        this.driverId = driverId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
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

