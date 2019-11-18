package contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Contacts implements Serializable {
    List<Entry> contacts = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    private void serialize() {
        try {
            List<Entry> list = contacts;
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("MyData.ser"));
            out.writeObject(list);
            out.close();
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    @SuppressWarnings("unchecked")
    private void deserialize() {
        try {
            FileInputStream fileIn = new FileInputStream("MyData.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            contacts = (List<Entry>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Contacts file is not found");
        } catch (ClassCastException e) {
            System.out.println("Contacts file is corrupted.");
        }
    }

    void start() {
        File f = new File("MyData.ser");
        if(f.exists() && !f.isDirectory()) {
            deserialize();
            System.out.println("open phonebook.db");
        }

        Command command;
        String choice;

        contacts:
        while (true) {
            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            choice = scanner.next();
            switch (choice) {
                case "add":
                    command = new AddContactCommand(this);
                    command.execute();
                    break;
                case "list":
                    new ListCommand(this).execute();
                    break;
                case "count":
                    System.out.printf("The phone book has %s records.\n",
                            contacts.size());
                    break;
                case "search":
                    new SearchCommand(this).execute();
                    break;
                case "exit":
                    break contacts;
            }
        }

        serialize();
    }
}
