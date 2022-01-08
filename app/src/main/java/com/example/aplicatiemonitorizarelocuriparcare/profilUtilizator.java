package com.example.aplicatiemonitorizarelocuriparcare;
public class profilUtilizator {
    public String firstName,secondName,userPassword;
    public String userEmail,userPhone,licensePlateNumber;  //inițializarea variabilelor de tip String
    public String inParking,totalPayment;
    public profilUtilizator(String userEmail,String userPassword,String firstName,String secondName,String userPhone,String licensePlateNumber,String inParking,String totalPayment){
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.firstName = firstName;
        this.secondName = secondName;  //preluarea datelor adăugate extern
        this.userPhone = userPhone;
        this.licensePlateNumber = licensePlateNumber;
        this.inParking = inParking;
        this.totalPayment = totalPayment;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName; }
    public String getSecondName(){
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName; }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail; }
    public String getUserPassword() {
        return userPassword; }                  //realizarea metodelor get și set pentru datele referitoare la utilizator
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword; }
    public String getUserPhone() {
        return userPhone; }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone; }
    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }
    public void setLicensePlateNumber(String licencePlateNumber) {
        this.licensePlateNumber = licensePlateNumber; }
    public String getInParking() {
        return inParking;
    }
    public void setInParking(String inParking) {
        this.inParking = inParking; }
    public String getTotalPayment() {
        return totalPayment;
    }
    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment; }
}
