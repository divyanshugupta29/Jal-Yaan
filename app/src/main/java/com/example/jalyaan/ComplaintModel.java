package com.example.jalyaan;

public class ComplaintModel {
    String subject,description;
    boolean complaintResolved;

    public ComplaintModel(String subject, String description) {
        this.subject = subject;
        this.description = description;
        this.complaintResolved = false;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplaintResolved() {
        return complaintResolved;
    }

    public void setComplaintResolved(boolean complaintResolved) {
        this.complaintResolved = complaintResolved;
    }
}
