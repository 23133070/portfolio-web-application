package com.portfolio.dao;

import com.portfolio.model.Contact;
import java.util.List;

/**
 * Data Access Object interface for Contact operations
 */
public interface ContactDAO {
    
    /**
     * Save a new contact message
     * @param contact the contact to save
     * @return the saved contact with generated ID
     */
    Contact save(Contact contact);
    
    /**
     * Find contact by ID
     * @param id the contact ID
     * @return the contact or null if not found
     */
    Contact findById(int id);
    
    /**
     * Get all contacts
     * @return list of all contacts
     */
    List<Contact> findAll();
    
    /**
     * Get unread contacts
     * @return list of unread contacts
     */
    List<Contact> findUnread();
    
    /**
     * Mark contact as read
     * @param id the contact ID
     * @return true if successful
     */
    boolean markAsRead(int id);
    
    /**
     * Delete contact by ID
     * @param id the contact ID
     * @return true if successful
     */
    boolean delete(int id);
    
    /**
     * Count total contacts
     * @return total number of contacts
     */
    int count();
    
    /**
     * Count unread contacts
     * @return number of unread contacts
     */
    int countUnread();
    
    /**
     * Get paginated contacts
     * @param page page number (0-based)
     * @param pageSize number of items per page
     * @return list of contacts for the page
     */
    List<Contact> findPaginated(int page, int pageSize);
}