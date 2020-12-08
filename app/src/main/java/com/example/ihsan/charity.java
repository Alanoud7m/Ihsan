package com.example.ihsan;

public class charity {

    String charityAddress , charityName, charityNumber, email, phoneNumber, status, userName;

    public charity(String charityAddress, String charityName, String charityNumber, String email, String phoneNumber, String status, String userName) {
        this.charityAddress = charityAddress;
        this.charityName = charityName;
        this.charityNumber = charityNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.userName = userName;
    }

    public String getCharityAddress() {
        return charityAddress;
    }

    public String getCharityName() {
        return charityName;
    }

    public String getCharityNumber() {
        return charityNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getUserName() {
        return userName;
    }

    public void setCharityAddress(String charityAddress) {
        this.charityAddress = charityAddress;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public void setCharityNumber(String charityNumber) {
        this.charityNumber = charityNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
