package ru.nektodev.voshod;

import ru.nektodev.voshod.exception.CarInitializationException;
import ru.nektodev.voshod.exception.ParkingException;
import ru.nektodev.voshod.model.Car;
import ru.nektodev.voshod.model.Parking;

import java.util.Date;

/**
 * ru.nektodev.voshod
 * Parking
 *
 * @author Tsykin V.A.
 *         tsykin.vyacheslav@otr.ru
 * @date 28.09.15
 */
public class ParkingApplication {


    public static final int HOUR = 60 * 60 * 1000;

    public static void main(String[] args) {
        System.out.println("Создание парковки... ");

        Parking parking = new Parking(1, 3, 3);

        System.out.println("Cоздана парковка " + parking.toString());

        Car car1 = null;
        Car car2 = null;
        Car car3 = null;
        Car car4 = null;
        Car bCar1 = null;
        Car bCar2 = null;
        Car bCar3 = null;
        Car bCar4 = null;
        try {
            car1 = new Car("х111хх", "БМВ");
            car2 = new Car("х112хх", "БМВ");
            car3 = new Car("х113хх", "БМВ");
            car4 = new Car("х114хх", "БМВ");

            bCar1 = new Car("х211хх", "БМВ", true);
            bCar2 = new Car("х212хх", "БМВ", true);
            bCar3 = new Car("х213хх", "БМВ", true);
            bCar4 = new Car("х214хх", "БМВ", true);

        } catch (CarInitializationException e) {
            e.printStackTrace();
        }

        Date car1Date = new Date(2015,9,28,7,0,0);
        parkCar(parking, car1, car1Date);
        getCar(parking, car1.getNumber(), new Date(2015,9,28,9,59,0));


//        parkCar(parking, car2, new Date());
//        parkCar(parking, car3, new Date());
//        parkCar(parking, bCar1, new Date());
//        parkCar(parking, bCar2, new Date());
//        //System.out.println(parking.toString());
//
//        parkCar(parking, car4, new Date());
//        //System.out.println(parking.toString());
//
//        getCar(parking, "х111хх", new Date());
//        //System.out.println(parking.toString());
//
//        parkCar(parking, bCar3, new Date());
//
//        getCar(parking, car4.getNumber(), new Date());
//        parkCar(parking, bCar4, new Date());
//
//        //System.out.println(parking.toString());
//
//        parkCar(parking, car4, new Date());

        //System.out.println(parking.toString());


    }

    private static void getCar(Parking parking, String number, Date takeDate){
        try {
            parking.takeCar(number, takeDate);
        } catch (ParkingException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void parkCar(Parking parking, Car car1, Date parkDate) {
        try {
            parking.parkCar(car1, parkDate);
        } catch (ParkingException e) {
            System.out.println(e.getMessage());
        }
    }

}
