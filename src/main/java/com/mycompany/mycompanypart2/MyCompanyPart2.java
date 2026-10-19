/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mycompanypart2;


import java.util.Scanner;

/**
 * Console-driven QuickChat application.Builds on PartOne's Login class-
 * a user must log in successfully before the messaging menu appears.
 * 
 * @author Student
 */
public class MyCompanyPart2{
    static Scanner input = new Scanner(System.in);
    
public static void main(String[] args) {
    
//----- Step 1: the user must log in before anything else -----
System.out.println("===== Registration =====");

System.out.print("Enter your first name: ");
String firstName = input.nextLine();

System.out.print("Enter your last name: ");
String lastName = input.nextLine();

Login login = new Login(firstName, lastName);

String username, password, cellPhoneNumber, registrationResult;
do {
    
System.out.print("Enter a username: ");
username = input.nextLine();

System.out.print("Enter a password: ");
password = input.nextLine();

System.out.println ("Enter your South African cell phone number (e.g. +27718693002): ");
cellPhoneNumber = input.nextLine();

registrationResult = login.registerUser(username, password, cellPhoneNumber);
System.out.println(registrationResult);

} while (!registrationResult.startsWith("Username successfully captured"));

System.out.println("\n===== Login =====");
System.out.print("Enter your username: ");
String loginUsername = input.nextLine();
System.out.print("Enter your password: ");
String loginPassword = input.nextLine();

boolean loginSuccessful = login.loginUser(loginUsername, loginPassword);
System.out.println(login.returnLoginStatus(loginSuccessful));

//Users should only be able to send messages if they have logged
//in successfully
if (!loginSuccessful) {
System.out.println("Login failed - exiting QuickChat.");
input.close();
return;
}

//----- Step 2: welcome message -----
System.out.println("\nWelcome to QuickChat.");

//----- Step 3-4: numeric menu, loops until the user quits -----
int maxMessages = 0;
int messagesEntered = 0;
boolean limitSet = false;
int choice;

do {
System.out.println("\n1) Send Messages");
System.out.println("2) Show recently sent messages");
System.out.println("3) Quit");
System.out.print("Enter your choice: ");
choice = Integer.parseInt(input.nextLine());

switch (choice) {
case 1:
if (!limitSet) {
System.out.print("How many messages would you like to enter? ");
maxMessages = Integer.parseInt(input.nextLine());
limitSet = true;
}

if (messagesEntered >= maxMessages) {
System.out.println("You have already entered your set number of messages");
break;
}

while (messagesEntered < maxMessages) {
System.out.print("Enter recipient cell number: ");
String recipient = input.nextLine();

System.out.print("Enter your message: ");
String messageText = input.nextLine();

Message message = new Message(recipient, messageText);

System.out.println(message.checkRecipientCell());
System.out.println(message.checkMessageLength());

System.out.println("1) Send Message");
System.out.println("2) Disregard Message");
System.out.println("3) Store Message to send later");
System.out.print("Enter your choice: ");
int sendChoice = Integer.parseInt(input.nextLine());

System.out.println(message.sentMessage(sendChoice));

if ("Sent".equals(message.getStatus())) {
System.out.println("\nMessage ID: " + message.getMessageID());
System.out.println("Message Hash: " + message.getMessageHash());
System.out.println("Recipient: " + message.getRecipient());
System.out.println("Message: " + message.getMessageText());
}

messagesEntered++;

}
System.out.println("\nTotal messages sent so far: " + Message.returnTotalMessages ());
break;

case 2:
System.out.println("Coming Soon.");
break;

case 3:
System.out.println("\nThank you for using QuickChat.");
System.out.println("Total messages sent: " + Message.returnTotalMessagess ());
break;

default:
System.out.println("Invalid option, please try again.");
}

} while (choice != 3);

input.close();
}
    
}