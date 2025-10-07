package com.portfolio.dao;

import com.portfolio.model.Contact;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * File-based implementation of ContactDAO
 */
public class ContactDAOImpl implements ContactDAO {
    
    private static final String DATA_FILE = "contacts.txt";
    private static final String SEPARATOR = "|";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    private AtomicInteger idCounter;
    private String dataFilePath;

    public ContactDAOImpl() {
        // Initialize data file path (in webapp's data directory)
        String webappPath = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF/data/";
        File dataDir = new File(webappPath);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        this.dataFilePath = webappPath + DATA_FILE;
        this.idCounter = new AtomicInteger(getMaxId() + 1);
    }

    @Override
    public Contact save(Contact contact) {
        try {
            if (contact.getId() == 0) {
                // New contact
                contact.setId(idCounter.getAndIncrement());
            }
            
            List<Contact> contacts = findAll();
            
            // Remove existing contact if updating
            contacts.removeIf(c -> c.getId() == contact.getId());
            
            // Add the contact
            contacts.add(contact);
            
            // Save to file
            saveToFile(contacts);
            
            return contact;
            
        } catch (Exception e) {
            System.err.println("Error saving contact: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Contact findById(int id) {
        List<Contact> contacts = findAll();
        return contacts.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> contacts = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Contact contact = parseContactFromLine(line);
                if (contact != null) {
                    contacts.add(contact);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, return empty list
            return contacts;
        } catch (IOException e) {
            System.err.println("Error reading contacts: " + e.getMessage());
        }
        
        // Sort by submission date (newest first)
        contacts.sort((c1, c2) -> c2.getSubmittedAt().compareTo(c1.getSubmittedAt()));
        
        return contacts;
    }

    @Override
    public List<Contact> findUnread() {
        return findAll().stream()
                .filter(contact -> !contact.isRead())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public boolean markAsRead(int id) {
        Contact contact = findById(id);
        if (contact != null) {
            contact.setRead(true);
            return save(contact) != null;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            List<Contact> contacts = findAll();
            boolean removed = contacts.removeIf(contact -> contact.getId() == id);
            
            if (removed) {
                saveToFile(contacts);
            }
            
            return removed;
        } catch (Exception e) {
            System.err.println("Error deleting contact: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int count() {
        return findAll().size();
    }

    @Override
    public int countUnread() {
        return (int) findAll().stream()
                .filter(contact -> !contact.isRead())
                .count();
    }

    @Override
    public List<Contact> findPaginated(int page, int pageSize) {
        List<Contact> allContacts = findAll();
        int start = page * pageSize;
        int end = Math.min(start + pageSize, allContacts.size());
        
        if (start >= allContacts.size()) {
            return new ArrayList<>();
        }
        
        return allContacts.subList(start, end);
    }

    // Private helper methods
    private void saveToFile(List<Contact> contacts) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFilePath))) {
            for (Contact contact : contacts) {
                writer.println(contactToLine(contact));
            }
        }
    }

    private String contactToLine(Contact contact) {
        return String.join(SEPARATOR,
                String.valueOf(contact.getId()),
                escapeString(contact.getName()),
                escapeString(contact.getEmail()),
                escapeString(contact.getSubject()),
                escapeString(contact.getMessage()),
                contact.getSubmittedAt().format(DATE_FORMATTER),
                String.valueOf(contact.isRead()),
                escapeString(contact.getIpAddress() != null ? contact.getIpAddress() : ""));
    }

    private Contact parseContactFromLine(String line) {
        try {
            String[] parts = line.split("\\" + SEPARATOR, -1);
            if (parts.length >= 7) {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(parts[0]));
                contact.setName(unescapeString(parts[1]));
                contact.setEmail(unescapeString(parts[2]));
                contact.setSubject(unescapeString(parts[3]));
                contact.setMessage(unescapeString(parts[4]));
                contact.setSubmittedAt(LocalDateTime.parse(parts[5], DATE_FORMATTER));
                contact.setRead(Boolean.parseBoolean(parts[6]));
                
                if (parts.length > 7) {
                    contact.setIpAddress(unescapeString(parts[7]));
                }
                
                return contact;
            }
        } catch (Exception e) {
            System.err.println("Error parsing contact line: " + line + " - " + e.getMessage());
        }
        return null;
    }

    private String escapeString(String str) {
        if (str == null) return "";
        return str.replace(SEPARATOR, "\\|").replace("\n", "\\n").replace("\r", "\\r");
    }

    private String unescapeString(String str) {
        if (str == null) return "";
        return str.replace("\\|", SEPARATOR).replace("\\n", "\n").replace("\\r", "\r");
    }

    private int getMaxId() {
        return findAll().stream()
                .mapToInt(Contact::getId)
                .max()
                .orElse(0);
    }
}