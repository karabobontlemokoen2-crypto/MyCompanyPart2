/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mycompanypart2;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
/**
 *Represent a single QuickChat message, along with the validation rules,
 * hash generation, and send/store/disregard logic described in the brief.
 * 
 * @author Student
 */
public class Message {
    
//Keeps track of every message that has actually been sent while the
//program is running, and the running total of sent messages
private static final List<Message> sentMessages = new ArrayList<>();
private static int totalMessagesSent = 0;
private static int messageCounter = 0; //assigns each message its sequence number

  static String returnTotalMessages() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
private final String messageID;
private final int messageNumber;
private final String recipient;
private final String messageText;
private final String messageHash;
private String status; // "Sent", "Stored", or "Disregarded"

//Normal use: the message ID is generated automatically
public Message(String recipient, String messageText) {
this.messageID = generateMessageID();
this.messageNumber = messageCounter++;
this.recipient = recipient;
this.messageText = messageText;
this.messageHash = createMessageHash();
}

//Test use: lets a unit test supply a fixed message ID, so the hash
//can be checked deterministically instead of against a random value
public Message(String messageID, String recipient, String messageText) {
this.messageID = messageID;
this.messageNumber = messageCounter++;
this.recipient = recipient;
this.messageText = messageText;
this.messageHash = createMessageHash();
}

private String generateMessageID() {
Random random = new Random();
StringBuilder id = new StringBuilder();
for (int i = 0; i < 10; i++) {
id.append(random.nextInt(10));
}
return id.toString();
}

//Resets all static tracking - used only between unit tests so each
//test starts from a clean, predictable state
public static void resetForTesting() {
messageCounter = 0;
totalMessagesSent = 0;
sentMessages.clear();
}

//Ensures that the message ID is not more than ten characters
public boolean checkMessageID() {
return messageID != null && messageID.length() <= 10; 
}

//Returns a status line confirming the generated message ID
public String generateMessageIDStatus() {
return "Message ID generated: " + messageID;
}

//Ensures that the recipient cell number is no more than ten characters
//long and starts with an international code
public String checkRecipientCell() {
if (recipient == null || !recipient.startsWith("+") || recipient.length() > 10) {
return "Cell phone number is incorrectly formatted or does not "
+ "contain an international code. Please correct the "
+ "number and try again.";
}

return "Cell phone number successfully captured.";
}

//Ensures the message is no longer than 250 characters
public String checkMessageLength() {
if (messageText.length() <= 250) {
return "Message ready to send.";
}

int excess = messageText.length() - 250;
return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
}
//Builds the Message Hash: the first two digits of the message ID, the
//message's sequence number, and the first and last words of the
//message, all in capitals - e.g. 00:0:HITHANKS
public final String createMessageHash() {
String firstTwo = messageID.substring(0, 2);
String[] words = messageText.trim().split("\\s+");
String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
}

//Lets the user choose whether to send, store, or disregard the message.
//choice: 1 = send, 2 = disregard, 3 = store
public String sentMessage(int choice) {
switch (choice) {
case 1:
status = "Sent";
totalMessagesSent++;
sentMessages.add(this);
return "Message successfully sent.";
case 2:
status = "Disregarded";
return "Press 0 to delete the message.";
case 3:
status = "Stored";
storeMessage();
return "Message successfully stored.";
default:
return "Invalid option selected.";
}

}

//Returns all the messages sent while the program is running, in the
//order required: Message ID, Message Hash, Recipient, Message
public static String printMessages() {
StringBuilder sb = new StringBuilder();
for (Message m : sentMessages) {
sb.append("Message ID: ").append(m.messageID).append("\n")
.append("Message Hash: ").append(m.messageHash).append("\n")
.append("Recipient: ").append(m.recipient).append("\n")
.append("Message: ").append(m.messageText).append("\n\n");
}
return sb.toString();
}

//Returns the total number of messages sent so far
public static int returnTotalMessagess() {
return totalMessagesSent;
}
//Stores this message's details as a JSON object, appended as one line
//to messages.json. A small hand-written JSON writer is used here so
//the project does not need an external JSON library - each field is
//escaped and wrapped manually instead of relying on a dependency.
public void storeMessage() {
String json = "{"
+ "\"messageID\":\"" + escapeJson(messageID) + "\","
+ "\"messageHash\":\"" + escapeJson(messageHash) + "\","
+ "\"recipient\":\"" + escapeJson(recipient) + "\","
+ "\"message\":\"" + escapeJson(messageText) + "\","
+ "\"status\":\"" + escapeJson(status) + "\""
+ "}";

try (FileWriter writer = new FileWriter("messages.json", true)) {
writer.write(json);
writer.write(System.lineSeparator());
} catch (IOException e) {
System.out.println("Could not store message: " + e.getMessage());
}
}

private String escapeJson(String value) {
if (value == null) {
return "";
}

return value.replace("\\", "\\\\").replace("\"", "\\\"");
}

//Getters - useful for both the console app and the unit tests
public String getMessageID() { return messageID; }
public String getMessageHash() { return messageHash; }
public String getRecipient() { return recipient; }
public String getMessageText() { return messageText; }
public int getMessageNumber() { return messageNumber; }
public String getStatus() { return status; }

}


