/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mycompanypart2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageTest {

    @BeforeEach
    public void setUp() {
        Message.resetForTesting();
    }

    // ----- Message length (should not exceed 250 characters) -----

    @Test
    public void testMessageLength_Success() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", message.checkMessageLength());
    }

    @Test
    public void testMessageLength_Failure() {
        String longMessage = "A".repeat(260); //10 characters over the limit
        Message message = new Message("+27718693002", longMessage);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", message.checkMessageLength());
    }

    // ----- Recipient cell number formatting -----

    @Test
    public void testRecipientCell_Success() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.", message.checkRecipientCell());
    }

    @Test
    public void testRecipientCell_Failure() {
        Message message = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Cell phone number is incorrectly formatted or does not "
                + "contain an international code. Please correct the "
                + "number and try again.", message.checkRecipientCell());
    }

    // ----- Message Hash (tested individually, then in a loop) -----

    @Test
    public void testMessageHash_TestCase1() {
        Message message = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", message.getMessageHash());
    }

    @Test
    public void testMessageHash_Loop() {
        String[][] testCases = {
            {"0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", "00:0:HITONIGHT"},
            {"0087654321", "08575975889", "Hi Keegan, did you receive the payment?", "00:1:HIPAYMENT"},
        };

        for (String[] testCase : testCases) {
            Message message = new Message(testCase[0], testCase[1], testCase[2]);
            assertEquals(testCase[3], message.getMessageHash());
        }
    }

    // ----- Message ID -----

    @Test
    public void testMessageIDIsCreated() {
        Message message = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ID generated: 0012345678", message.generateMessageIDStatus());
    }

    @Test
    public void testCheckMessageID_Valid() {
        Message message = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(message.checkMessageID());
    }

    // ----- Sending, disregarding, and storing a message -----

    @Test
    public void testSentMessage_Send() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully sent.", message.sentMessage(1));
    }

    @Test
    public void testSentMessage_Disregard() {
        Message message = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Press 0 to delete the message.", message.sentMessage(2));
    }

    @Test
    public void testSentMessage_Store() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully stored.", message.sentMessage(3));
    }

    // ----- Running total of messages sent -----

    @Test
    public void testReturnTotalMessagess() {
        Message message1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        message1.sentMessage(1);

        Message message2 = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        message2.sentMessage(2);

        assertEquals(1, Message.returnTotalMessagess());
    }
}

    
    