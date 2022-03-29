package com.example.jalyaan;

public class User {
    private String uid,name,mobileNumber,street,city,pincode,profileImage,email;
    private boolean isAdmin;

    public User() {
    }



    public User(String uid, String name, String mobileNumber, String street, String city, String pincode, String profileImage,String email) {
        this.uid = uid;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.street = street;
        this.city = city;
        this.pincode = pincode;
        this.profileImage = profileImage;
        this.isAdmin = false;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
