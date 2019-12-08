package com.hzg.designmodel.factory;

/**
 * @Author: huangzhigao
 * @Date: 2019/12/8 16:08
 */
public class CarFactory {
    public static Car getCar(String carName){
        Car car = null;
        if(carName.equals("奥迪")){
            car = new AudiCar();
        }else if(carName.equals("奔驰")){
            car = new BenchiCar();
        }else{
            return null;
        }
        return car;
    }


    public static void main(String[] args) {
        Car audiCar = CarFactory.getCar("奥迪");
        Car benChiCar = CarFactory.getCar("奔驰");
        audiCar.run();
        benChiCar.run();
    }

}
