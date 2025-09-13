package org.example;

public class DeliveryBase {
    protected int deliveryId;
    protected int driverId;

    public DeliveryBase(int deliveryId, int driverId) {
        if (deliveryId <= 0) throw new IllegalArgumentException("deliveryId должен быть положительным числом");
        if (driverId <= 0) throw new IllegalArgumentException("driverId должен быть положительным числом");

        this.deliveryId = deliveryId;
        this.driverId = driverId;
    }

    public int getDeliveryId() { return deliveryId; }
    public int getDriverId() { return driverId; }

    public String toString() {
        return "DeliveryBase{" +
                "deliveryId=" + deliveryId +
                ", driverId=" + driverId +
                '}';
    }
}
