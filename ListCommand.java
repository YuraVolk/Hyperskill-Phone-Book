package contacts;

class ListCommand extends MultipleChoiceCommand {
    ListCommand(Contacts contacts) {
        super(contacts);
    }

    @Override
    void printList() {
        for (int i = 0; i < contacts.contacts.size(); i++) {
            System.out.printf("%s. ", i + 1);
            contacts.contacts.get(i).printCompact();
        }
        System.out.println();
    }

    @Override
    void execute() {
        printList();
        System.out.println("[list] Enter action ([number], back): ");
        String action;

        action = contacts.scanner.next();
        if (!isNumber(action)) {
            return;
        }

        int record = Integer.parseInt(action) - 1;
        startContactOperations(record);
    }
}
