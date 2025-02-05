package com.slavikart.hw_07;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {
    private String brand, model, description;
    private int year, cost, imgResID;

    public Car(String brand, String model,
               int year, String description, int cost, int imgResID) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.description = description;
        this.cost = cost;
        this.imgResID = imgResID;
    }

    protected Car(Parcel in) {
        brand = in.readString();
        model = in.readString();
        year = in.readInt();
        description = in.readString();
        cost = in.readInt();
        imgResID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeInt(year);
        dest.writeString(description);
        dest.writeInt(cost);
        dest.writeInt(imgResID);
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) { return new Car(in); }
        @Override
        public Car[] newArray(int size) { return new Car[size]; }
    };

    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getDescription() { return description; }
    public int getCost() { return cost; }
    public int getImgResID() { return imgResID; }
}
