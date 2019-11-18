package contacts;

import java.util.ArrayList;
import java.util.List;

public class SearchCommand extends MultipleChoiceCommand {
    private List<Integer> realIndexes = new ArrayList<>();

    SearchCommand(Contacts contacts) {
        super(contacts);
    }

    private boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0) {
            return true;
        }

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp) {
                continue;
            }

            if (src.regionMatches(true, i, what, 0, length)) {
                return true;
            }
        }

        return false;
    }

    @Override
    void printList() {
        System.out.print("Enter search query: ");
        contacts.scanner.nextLine();
        String query = contacts.scanner.nextLine();

        List<Entry> contactsFound = new ArrayList<>();
        for (int i = 0; i < contacts.contacts.size(); i++) {
            if (containsIgnoreCase(contacts.contacts.get(i).
                            returnFieldsString(), query))  {
                contactsFound.add(contacts.contacts.get(i));
                realIndexes.add(i);
            }
        }

        System.out.printf("Found %s results:\n", contactsFound.size());
        for (int i = 0; i < contactsFound.size(); i++) {
            System.out.printf("%s. ", i + 1);
            contactsFound.get(i).printCompact();
        }
        System.out.println();
    }

    @Override
    void execute() {
        printList();

        System.out.println("[list] Enter action ([number], back, again): ");
        String action;

        action = contacts.scanner.next();
        if (!isNumber(action)) {
            if (action.equals("again")) {
                execute();
            }
        } else {
            int record = Integer.parseInt(action) - 1;
            startPostSearchOperations(record, realIndexes);
        }
    }
}
