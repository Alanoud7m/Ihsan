package com.example.ihsan;

public class DeliveryVolunteers {
    String email,familyName,firstName,idNumber,phoneNumber,userName;

    public DeliveryVolunteers(String email, String familyName, String firstName, String idNumber, String phoneNumber, String userName) {
        this.email = email;
        this.familyName = familyName;
        this.firstName = firstName;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
    }

    public DeliveryVolunteers() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
