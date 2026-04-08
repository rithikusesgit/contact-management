package com.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * JUnit 5 Tests for ContactManager
 * Problem Statement: Ensure contacts are stored and retrieved accurately.
 * Tests all CRUD operations with valid, invalid, and edge case inputs.
 */
public class ContactManagerTest {

    private ContactManager manager;

    @BeforeEach
    public void setUp() {
        manager = new ContactManager();
    }

    // ===== CREATE TESTS =====

    @Test
    public void testAddContact_ValidInput_ContactCreated() {
        ContactManager.Contact c = manager.addContact("Tharun", "9876543210", "tharun@gmail.com");
        assertNotNull(c, "Added contact should not be null");
        assertEquals("Tharun", c.getName(),  "Name should match");
        assertEquals("9876543210", c.getPhone(), "Phone should match");
        assertEquals("tharun@gmail.com", c.getEmail(), "Email should match");
    }

    @Test
    public void testAddContact_AssignsUniqueIds() {
        ContactManager.Contact c1 = manager.addContact("Alice", "1111111111", "alice@mail.com");
        ContactManager.Contact c2 = manager.addContact("Bob",   "2222222222", "bob@mail.com");
        assertNotEquals(c1.getId(), c2.getId(), "Each contact must have a unique ID");
    }

    @Test
    public void testAddContact_NullName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.addContact(null, "9876543210", "test@mail.com");
        }, "Null name should throw IllegalArgumentException");
    }

    @Test
    public void testAddContact_EmptyName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.addContact("  ", "9876543210", "test@mail.com");
        }, "Empty/blank name should throw IllegalArgumentException");
    }

    @Test
    public void testAddContact_NullPhone_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.addContact("Tharun", null, "test@mail.com");
        }, "Null phone should throw IllegalArgumentException");
    }

    @Test
    public void testAddContact_NullEmail_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.addContact("Tharun", "9876543210", null);
        }, "Null email should throw IllegalArgumentException");
    }

    @Test
    public void testAddContact_IncreasesCount() {
        assertEquals(0, manager.getContactCount(), "Initial count should be 0");
        manager.addContact("Tharun", "9876543210", "t@mail.com");
        manager.addContact("Rithik", "9123456789", "r@mail.com");
        assertEquals(2, manager.getContactCount(), "Count should be 2 after adding 2 contacts");
    }

    // ===== READ TESTS =====

    @Test
    public void testGetContact_ValidId_ReturnsCorrectContact() {
        ContactManager.Contact added = manager.addContact("Tharun", "9876543210", "tharun@mail.com");
        ContactManager.Contact fetched = manager.getContact(added.getId());
        assertNotNull(fetched, "Fetched contact should not be null");
        assertEquals(added.getId(),    fetched.getId(),    "IDs should match");
        assertEquals(added.getName(),  fetched.getName(),  "Names should match");
        assertEquals(added.getPhone(), fetched.getPhone(), "Phones should match");
        assertEquals(added.getEmail(), fetched.getEmail(), "Emails should match");
    }

    @Test
    public void testGetContact_InvalidId_ReturnsNull() {
        ContactManager.Contact fetched = manager.getContact(999);
        assertNull(fetched, "Non-existent ID should return null");
    }

    @Test
    public void testGetAllContacts_EmptyStore_ReturnsEmptyList() {
        List<ContactManager.Contact> all = manager.getAllContacts();
        assertNotNull(all, "getAllContacts should return empty list, not null");
        assertEquals(0, all.size(), "Should be no contacts initially");
    }

    @Test
    public void testGetAllContacts_ReturnsAllAdded() {
        manager.addContact("Alice",   "1111111111", "alice@mail.com");
        manager.addContact("Bob",     "2222222222", "bob@mail.com");
        manager.addContact("Charlie", "3333333333", "charlie@mail.com");
        List<ContactManager.Contact> all = manager.getAllContacts();
        assertEquals(3, all.size(), "Should return all 3 added contacts");
    }

    // ===== UPDATE TESTS =====

    @Test
    public void testUpdateContact_ValidId_UpdatesSuccessfully() {
        ContactManager.Contact c = manager.addContact("Tharun", "9876543210", "tharun@mail.com");
        boolean result = manager.updateContact(c.getId(), "Tharun Kumar", "9999999999", "tk@mail.com");
        assertTrue(result, "Update should return true for valid ID");
        ContactManager.Contact updated = manager.getContact(c.getId());
        assertEquals("Tharun Kumar", updated.getName(),  "Name should be updated");
        assertEquals("9999999999",   updated.getPhone(), "Phone should be updated");
        assertEquals("tk@mail.com",  updated.getEmail(), "Email should be updated");
    }

    @Test
    public void testUpdateContact_InvalidId_ReturnsFalse() {
        boolean result = manager.updateContact(999, "Ghost", "0000000000", "ghost@mail.com");
        assertFalse(result, "Update on non-existent ID should return false");
    }

    @Test
    public void testUpdateContact_PartialUpdate_OnlyChangesProvided() {
        ContactManager.Contact c = manager.addContact("Tharun", "9876543210", "tharun@mail.com");
        // Only update name - phone and email should remain the same
        manager.updateContact(c.getId(), "Tharun Kumar", null, null);
        ContactManager.Contact updated = manager.getContact(c.getId());
        assertEquals("Tharun Kumar", updated.getName(),  "Name should be updated");
        assertEquals("9876543210",   updated.getPhone(), "Phone should remain unchanged");
        assertEquals("tharun@mail.com", updated.getEmail(), "Email should remain unchanged");
    }

    // ===== DELETE TESTS =====

    @Test
    public void testDeleteContact_ValidId_DeletesSuccessfully() {
        ContactManager.Contact c = manager.addContact("Tharun", "9876543210", "tharun@mail.com");
        boolean result = manager.deleteContact(c.getId());
        assertTrue(result, "Delete should return true for valid ID");
        assertNull(manager.getContact(c.getId()), "Contact should be null after deletion");
    }

    @Test
    public void testDeleteContact_InvalidId_ReturnsFalse() {
        boolean result = manager.deleteContact(999);
        assertFalse(result, "Delete on non-existent ID should return false");
    }

    @Test
    public void testDeleteContact_DecreasesCount() {
        ContactManager.Contact c1 = manager.addContact("Alice", "1111111111", "a@mail.com");
        manager.addContact("Bob", "2222222222", "b@mail.com");
        assertEquals(2, manager.getContactCount());
        manager.deleteContact(c1.getId());
        assertEquals(1, manager.getContactCount(), "Count should decrease after delete");
    }

    @Test
    public void testDeleteContact_AlreadyDeleted_ReturnsFalse() {
        ContactManager.Contact c = manager.addContact("Tharun", "9876543210", "t@mail.com");
        manager.deleteContact(c.getId());
        boolean result = manager.deleteContact(c.getId());
        assertFalse(result, "Deleting already-deleted contact should return false");
    }
}
