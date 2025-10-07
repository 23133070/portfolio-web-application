package com.portfolio.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Contact model class representing a contact form submission
 */
public class Contact {
    private int id;
    private String name;
    private String email;
    private String subject;
    private String message;
    private LocalDateTime submittedAt;
    private boolean isRead;
    private String ipAddress;

    // Default constructor
    public Contact() {
        this.submittedAt = LocalDateTime.now();
        this.isRead = false;
    }

    // Constructor with required fields
    public Contact(String name, String email, String subject, String message) {
        this();
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    // Full constructor
    public Contact(int id, String name, String email, String subject, String message, 
                   LocalDateTime submittedAt, boolean isRead, String ipAddress) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.submittedAt = submittedAt;
        this.isRead = isRead;
        this.ipAddress = ipAddress;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    // Utility methods
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return submittedAt.format(formatter);
    }

    public String getShortMessage() {
        if (message == null) return "";
        return message.length() > 100 ? message.substring(0, 100) + "..." : message;
    }

    // Validation methods
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
               email != null && isValidEmail(email) &&
               subject != null && !subject.trim().isEmpty() &&
               message != null && !message.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", submittedAt=" + submittedAt +
                ", isRead=" + isRead +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}