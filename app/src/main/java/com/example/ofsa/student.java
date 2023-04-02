package com.example.ofsa;

public class student {

    public String FullName,Address,PhoneNumber,Email,Password,ConfirmPassword;

    public student(){

    }

    public student(String FullName,String email,String address,String phoneNumber,String password,String confirmPassword) {
        this.FullName=FullName;
        Email=email;
        Address=address;
        PhoneNumber=phoneNumber;
        Password=password;
        ConfirmPassword=confirmPassword;
    }
}
