package com.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contact Management System
 * Problem Statement: Ensure contacts are stored and retrieved accurately.
 * Implements full CRUD: Create, Read, Update, Delete
 * Java 17 Compatible
 */
public class ContactManager {

    // In-memory storage: contactId -> Contact
    private final Map<Integer, Contact> contactStore = new HashMap<>();
    private int nextId = 1;

    /**
     * Inner class representing a Contact
     */
    public static class Contact {
        private int    id;
        private String name;
        private String phone;
        private String email;

        public Contact(int id, String name, String phone, String email) {
            this.id    = id;
            this.name  = name;
            this.phone = phone;
            this.email = email;
        }

        public int    getId()    { return id; }
        public String getName()  { return name; }
        public String getPhone() { return phone; }
        public String getEmail() { return email; }

        public void setName(String name)   { this.name  = name; }
        public void setPhone(String phone) { this.phone = phone; }
        public void setEmail(String email) { this.email = email; }

        @Override
        public String toString() {
            return String.format("Contact{id=%d, name='%s', phone='%s', email='%s'}",
                    id, name, phone, email);
        }
    }

    /**
     * CREATE - Add a new contact
     * Returns the newly created Contact with assigned ID
     */
    public Contact addContact(String name, String phone, String email) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact name cannot be null or empty");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        Contact contact = new Contact(nextId++, name.trim(), phone.trim(), email.trim());
        contactStore.put(contact.getId(), contact);
        return contact;
    }

    /**
     * READ - Get a contact by ID
     * Returns null if not found
     */
    public Contact getContact(int id) {
        return contactStore.get(id);
    }

    /**
     * READ - Get all contacts as a list
     */
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contactStore.values());
    }

    /**
     * UPDATE - Update an existing contact's details
     * Returns true if updated, false if contact not found
     */
    public boolean updateContact(int id, String name, String phone, String email) {
        Contact existing = contactStore.get(id);
        if (existing == null) {
            return false;
        }
        if (name != null && !name.trim().isEmpty())   existing.setName(name.trim());
        if (phone != null && !phone.trim().isEmpty())  existing.setPhone(phone.trim());
        if (email != null && !email.trim().isEmpty())  existing.setEmail(email.trim());
        return true;
    }

    /**
     * DELETE - Remove a contact by ID
     * Returns true if deleted, false if not found
     */
    public boolean deleteContact(int id) {
        return contactStore.remove(id) != null;
    }

    /**
     * Returns total number of contacts stored
     */
    public int getContactCount() {
        return contactStore.size();
    }

    public static void main(String[] args) {
        ContactManager manager = new ContactManager();

        System.out.println("===== Contact Management System =====");
        System.out.println();

        // CREATE
        System.out.println("--- Adding Contacts ---");
        Contact c1 = manager.addContact("Tharun",  "9876543210", "tharun@gmail.com");
        Contact c2 = manager.addContact("Rithik",   "9123456789", "rithik@gmail.com");
        Contact c3 = manager.addContact("Sabaresh", "9001234567", "sabaresh@gmail.com");
        System.out.println("Added: " + c1);
        System.out.println("Added: " + c2);
        System.out.println("Added: " + c3);
        System.out.println("Total Contacts: " + manager.getContactCount());
        System.out.println();

        // READ
        System.out.println("--- Fetching Contact ID=2 ---");
        Contact found = manager.getContact(2);
        System.out.println("Found: " + found);
        System.out.println();

        // UPDATE
        System.out.println("--- Updating Contact ID=1 ---");
        boolean updated = manager.updateContact(1, "Tharun Kumar", "9999999999", "tharunkumar@gmail.com");
        System.out.println("Update successful: " + updated);
        System.out.println("Updated: " + manager.getContact(1));
        System.out.println();

        // DELETE
        System.out.println("--- Deleting Contact ID=3 ---");
        boolean deleted = manager.deleteContact(3);
        System.out.println("Delete successful: " + deleted);
        System.out.println("Total Contacts after delete: " + manager.getContactCount());
        System.out.println();

        // GET ALL
        System.out.println("--- All Remaining Contacts ---");
        manager.getAllContacts().forEach(System.out::println);
        System.out.println("=====================================");
    }
}
