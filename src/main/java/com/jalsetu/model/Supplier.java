package com.jalsetu.model;

public class Supplier {

    private int id;
    private String supplierName;
    private String contactPerson;
    private String contactNumber;
    private int tankerCapacity;
    private String availabilityStatus;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public int getTankerCapacity() { return tankerCapacity; }
    public void setTankerCapacity(int tankerCapacity) { this.tankerCapacity = tankerCapacity; }

    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }
}