package com.company.myproject1.ui.domain;

public final class User {
    private final String userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String nationalID;
    private String status;
    private String dateAdded;
    private String designation;
    private String authorisationLevel;
    private String bio;

    public User(String userId,
                String userName,
                String email,
                String phoneNumber,
                String password,
                String firstName,
                String lastName,
                String gender,
                String nationalID,
                String status,
                String dateAdded,
                String designation,
                String authorisationLevel,
                String bio){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationalID = nationalID;
        this.status = status;
        this.dateAdded = dateAdded;
        this.designation = designation;
        this.authorisationLevel = authorisationLevel;
        this.bio = bio;
    }


    public String getUserId(){
        return userId;
    }

    public String getUserName(){
        return userName;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumben(){
        return phoneNumber;
    }

    public String getPassword(){
        return password;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getGender(){
        return gender;
    }

    public String getNationalID(){
        return nationalID;
    }

    public String getStatus(){
        return status;
    }

    public String getDateAdded(){
        return dateAdded;
    }

    public String getDesignation(){
        return designation;
    }

    public String getAuthorisationLevel(){
        return authorisationLevel;
    }

    public String getBio(){
        return  bio;
    }


    public void setUserName(final String userName){
        this.userName = userName;
    }

    public void setEmail(final String email){
        this.email  = email;
    }

    public void setPhoneNumber(final String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(final String password){
        this.password = password;
    }

    public void setFirstName(final String firstName){
        this.firstName = firstName;
    }

    public void setLastName(final String lastName){
        this.lastName = lastName;
    }

    public void setGender(final String gender){
        this.gender = gender;
    }

    public void setNationalID(final String nationalID){
        this.nationalID = nationalID;
    }

    public void setStatus(final String status){
        this.status = status;
    }

    public void setDateAdded(final String dateAdded){
        this.dateAdded = dateAdded;
    }

    public void setDesignation(final String designation){
        this.designation = designation;
    }

    public void setAuthorisationLevel(final String authorisationLevel){
        this.authorisationLevel = authorisationLevel;
    }

    public  void setBio (final String bio){
        this.bio = bio;
    }

}
