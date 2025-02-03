package com.slavikart.hw_05;

public class UserModel {
    private int avatarResId;
    private String firstName;
    private String lastName;
    private int age;
    private String country;
    private String city;

    public UserModel(int avatarResId, String firstName, String lastName,
                     int age, String country, String city) {
        this.avatarResId = avatarResId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.country = country;
        this.city = city;
    }

    public int getAvatarResId() { return avatarResId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
}
