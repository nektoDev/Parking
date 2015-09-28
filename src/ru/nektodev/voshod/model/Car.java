package ru.nektodev.voshod.model;

import ru.nektodev.voshod.exception.CarInitializationException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ru.nektodev.voshod.model
 * Parking
 *
 * @author Tsykin V.A.
 *         tsykin.vyacheslav@otr.ru
 * @date 28.09.15
 */
public class Car {

    private String number;
    private String model;
    private boolean isBigCar;

    private Date parkingDate;

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public Car(String number, String model) throws CarInitializationException {
        if (number == null || number.isEmpty()) {
            throw new CarInitializationException("Невозможно создать машину с пустым гос. номером");
        }

        this.number = number;
        this.model = model;
        this.isBigCar = false;
    }

    public Car(String number, String model, boolean isBigCar) {
        this.number = number;
        this.model = model;
        this.isBigCar = isBigCar;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isBigCar() {
        return isBigCar;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (this.isBigCar()) {
            sb.append("Большая машина: ");
        } else {
            sb.append("Машина: ");
        }

        sb.append(model).append(" гос. номер ").append(number);
        if (parkingDate != null) {
            sb.append(" Стоит с ").append(sdf.format(parkingDate));
        }
        return sb.toString();
    }


    public Date getParkingDate() {
        return parkingDate;
    }

    public void setParkingDate(Date parkingDate) {
        this.parkingDate = parkingDate;
    }
}
