package org.example;

import com.google.gson.*;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Delivery {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private int deliveryId;
    private int routeId;
    private int driverId;
    private LocalDate departureDate;
    private LocalDate arrivalDate;

    private Delivery() {}

    //конструктор
    public Delivery(int deliveryId, int routeId, int driverId, LocalDate departureDate, LocalDate arrivalDate) {
        this.deliveryId = validatePositiveId(deliveryId, "deliveryId");
        this.routeId = validatePositiveId(routeId, "routeId");
        this.driverId = validatePositiveId(driverId, "driverId");
        this.departureDate = validateDepartureDate(departureDate);
        this.arrivalDate = validateArrivalDate(arrivalDate, departureDate);
    }

    //конструктор строки
    public Delivery(String csv) {
        if (csv == null || csv.isBlank()) {
            throw new IllegalArgumentException("CSV строка не может быть пустой");
        }
        String[] parts = csv.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("CSV строка должна содержать 5 полей: deliveryId, routeId, driverId, departureDate, arrivalDate");
        }
        this.deliveryId = validatePositiveId(Integer.parseInt(parts[0].trim()), "deliveryId");
        this.routeId = validatePositiveId(Integer.parseInt(parts[1].trim()), "routeId");
        this.driverId = validatePositiveId(Integer.parseInt(parts[2].trim()), "driverId");
        this.departureDate = validateDepartureDate(LocalDate.parse(parts[3].trim(), DATE_FORMAT));
        this.arrivalDate = validateArrivalDate(LocalDate.parse(parts[4].trim(), DATE_FORMAT), this.departureDate);
    }

    //конструктор JSON
    public Delivery(String jsonFilePath, boolean isJsonFile) throws IOException {
        if (!isJsonFile) {
            throw new IllegalArgumentException("Для CSV используйте другой конструктор");
        }

        try (FileReader reader = new FileReader(jsonFilePath)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            Delivery temp = gson.fromJson(reader, Delivery.class);

            this.deliveryId = validatePositiveId(temp.deliveryId, "deliveryId");
            this.routeId = validatePositiveId(temp.routeId, "routeId");
            this.driverId = validatePositiveId(temp.driverId, "driverId");
            this.departureDate = validateDepartureDate(temp.departureDate);
            this.arrivalDate = validateArrivalDate(temp.arrivalDate, temp.departureDate);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Ошибка в формате JSON", e);
        }
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

    public int getDeliveryId() { return deliveryId; }
    public int getRouteId() { return routeId; }
    public int getDriverId() { return driverId; }
    public LocalDate getDepartureDate() { return departureDate; }
    public LocalDate getArrivalDate() { return arrivalDate; }

    public void setDeliveryId(int deliveryId) { this.deliveryId = validatePositiveId(deliveryId, "deliveryId"); }
    public void setRouteId(int routeId) { this.routeId = validatePositiveId(routeId, "routeId"); }
    public void setDriverId(int driverId) { this.driverId = validatePositiveId(driverId, "driverId"); }
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = validateDepartureDate(departureDate);
        if (this.arrivalDate != null) {
            this.arrivalDate = validateArrivalDate(this.arrivalDate, departureDate);
        }
    }
    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = validateArrivalDate(arrivalDate, this.departureDate);
    }

    public static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        @Override
        public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DATE_FORMAT));
        }
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString(), DATE_FORMAT);
        }
    }
}
