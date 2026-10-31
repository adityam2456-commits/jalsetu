package com.jalsetu.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class WaterRequest {

    private int id;
    private int societyId;
    private int requiredQuantity;
    private LocalDate preferredDate;
    private String urgency;
    private String remarks;
    private String status;
    private Timestamp createdAt;

    // Not a column of water_request. We fill it later when we join
    // with the society table, so the admin sees the society name.
    private String societyName;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSocietyId() { return societyId; }
    public void setSocietyId(int societyId) { this.societyId = societyId; }

    public int getRequiredQuantity() { return requiredQuantity; }
    public void setRequiredQuantity(int requiredQuantity) { this.requiredQuantity = requiredQuantity; }

    public LocalDate getPreferredDate() { return preferredDate; }
    public void setPreferredDate(LocalDate preferredDate) { this.preferredDate = preferredDate; }

    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getSocietyName() { return societyName; }
    public void setSocietyName(String societyName) { this.societyName = societyName; }
}