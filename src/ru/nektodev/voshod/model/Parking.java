package ru.nektodev.voshod.model;

import ru.nektodev.voshod.exception.ParkingException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * ru.nektodev.voshod.model
 * Parking
 *
 * @author Tsykin V.A.
 *         tsykin.vyacheslav@otr.ru
 * @date 28.09.15
 */
public class Parking {

    public static final int MS_IN_HOUR = 1000 * 60 * 60;
    private Integer id;
    private Integer smallCarSize;
    private Integer bigCarSize;

    private HashMap<String, Car> smallCarPlaces;
    private HashMap<String, Car> bigCarPlaces;

    private final Integer DAY_PRICE = 100;
    private final Integer NIGHT_PRICE = 200;

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public Parking(Integer id, Integer smallCarSize, Integer bigCarSize) {
        this.id = id;
        this.smallCarSize = smallCarSize;
        this.bigCarSize = bigCarSize;

        smallCarPlaces = new HashMap<String, Car>();
        bigCarPlaces = new HashMap<String, Car>();
    }

    public void parkCar(Car car, Date parkDate) throws ParkingException {

        if (parkDate == null) {
            throw new ParkingException("Не указана дата парковки");
        }

        if (car == null) {
            throw new ParkingException("Невозможно припарковать несуществующую машину");
        }

        if (smallCarPlaces.containsKey(car.getNumber()) || bigCarPlaces.containsKey(car.getNumber())) {
            throw new ParkingException("Машина с таким гос.номером уже припаркована");
        }

        if (car.isBigCar()) {
            if (bigCarPlaces.size() >= bigCarSize) {
                throw new ParkingException("Парковка переполнена");
            }

            bigCarPlaces.put(car.getNumber(), car);

        } else {

            if (smallCarPlaces.size() >= smallCarSize) {

                if (bigCarPlaces.size() >= bigCarSize) {
                    throw new ParkingException("Парковка переполнена");
                } else {
                    bigCarPlaces.put(car.getNumber(), car);
                }

            } else {
                smallCarPlaces.put(car.getNumber(), car);
            }
        }

        car.setParkingDate(parkDate);

        System.out.println("Поставили машину: " + car.toString());
    }

    public Car takeCar(String carNumber, Date takeDate) throws ParkingException {
        if (carNumber == null || carNumber.isEmpty()) {
            throw new ParkingException("ВЫ не указали номер машины которую забрать");
        }

        if (takeDate == null) {
            throw new ParkingException("ВЫ не указали дату получения машины");
        }

        Car car = null;

        if (!smallCarPlaces.containsKey(carNumber)) {

            if (!bigCarPlaces.containsKey(carNumber)) {
                throw new ParkingException("Машины с таким гос.номером нет на парковке");
            } else {
                car = bigCarPlaces.get(carNumber);

                if (takeDate.before(car.getParkingDate())) {
                    throw new ParkingException("Дата парковки не должна быть позже даты забирания ");
                } else {
                    car = bigCarPlaces.remove(carNumber);
                }
            }

        } else {
            car = smallCarPlaces.get(carNumber);

            if (takeDate.before(car.getParkingDate())) {
                throw new ParkingException("Дата парковки не должна быть позже даты забирания ");
            } else {
                car = smallCarPlaces.remove(carNumber);
            }
        }



        Integer price = getPrice(car.getParkingDate(), takeDate);

        System.out.println("Забрали машину " + car.toString() + " по " + sdf.format(takeDate) + " Должны " + price);
        return car;
    }

    private Integer getPrice(Date parkDate, Date takeDate) {
        Integer result = 0;
        Date tmpParkDate = (Date) parkDate.clone();
        long l = (takeDate.getTime() - parkDate.getTime()) / MS_IN_HOUR;

        for (long i = 0; i <= l; i++) {
            parkDate.setTime(tmpParkDate.getTime() + MS_IN_HOUR);
            if (tmpParkDate.getHours() > 7 && tmpParkDate.getHours() < 20) {
                result += DAY_PRICE;
            } else {
                result += NIGHT_PRICE;
                tmpParkDate.setTime(tmpParkDate.getTime() + MS_IN_HOUR*11);
                i += 11;
            }


        }
        return result;
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("Парковка #")
                .append(id)
                .append(" вместимостью ")
                .append(smallCarSize).append(" маленьких машин ").append(".")
                .append(bigCarSize).append(" больших машин ").append(".");

        if (smallCarPlaces.size() != 0) {
            s.append("Припарковано маленьких \n");

            for (Car car : smallCarPlaces.values()) {
                s.append(car.toString()).append("\n");
            }
        }

        if (bigCarPlaces.size() != 0) {
            s.append("Припарковано больших \n");

            for (Car car : bigCarPlaces.values()) {
                s.append(car.toString()).append("\n");
            }
        }

        return s.toString();
    }
}
