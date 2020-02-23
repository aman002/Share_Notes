package com.example.sharenotes.Model;

public class Users
{
    private String Password,Phone;

    public Users()
    {

    }

    public Users(String password, String phone) {

        Password = password;
        Phone = phone;
    }



    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
