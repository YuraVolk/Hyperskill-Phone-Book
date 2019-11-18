package contacts;

import java.util.List;

abstract class MultipleChoiceCommand extends Command {
    MultipleChoiceCommand(Contacts contacts) {
        super(contacts);
    }

    private void removeContact(int record) {
        contacts.contacts.remove(record);
        System.out.println("The record removed!\n");
    }

    private void editContact(int record) {
        System.out.printf("Select a field (%s): ",
                contacts.contacts.get(record).getFields());
        String field = contacts.scanner.next();
        System.out.printf("Enter %s: ", field);
        contacts.scanner.nextLine();
        String value = contacts.scanner.nextLine();
        contacts.contacts.get(record).setField(field, value);

        System.out.println("Saved.\n");
        contacts.contacts.get(record).print();
    }

    boolean isNumber(String num) {
        return num.matches("-?\\d+(\\.\\d+)?");
    }

    void startContactOperations(int record) {
        contacts.contacts.get(record).print();
        System.out.println();

        String choice;

        loop:
        while (true) {
            System.out.println("[record] Enter action (edit, delete, menu): ");
            choice = contacts.scanner.next();
            switch (choice) {
                case "edit":
                    editContact(record);
                    break loop;
                case "delete":
                    removeContact(record);
                    break loop;
                case "menu":
                    break loop;
            }
        }
    }

    void startPostSearchOperations(int record,  List<Integer> occurrenceIndexes) {
        contacts.contacts.get(record).print();
        System.out.println();

        String choice;

        loop:
        while (true) {
            System.out.println("[record] Enter action (edit, delete, menu): ");
            choice = contacts.scanner.next();
            switch (choice) {
                case "edit":
                    editContact(occurrenceIndexes.get(record));
                    break loop;
                case "delete":
                    removeContact(occurrenceIndexes.get(record));
                    break loop;
                case "menu":
                    break loop;
            }
        }
    }

    abstract void printList();
}
