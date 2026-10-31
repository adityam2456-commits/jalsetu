package com.jalsetu.model;

public class Society {

    private int id;
    private String societyName;
    private String contactPerson;
    private String contactNumber;
    private String address;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSocietyName() { return societyName; }
    public void setSocietyName(String societyName) { this.societyName = societyName; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}