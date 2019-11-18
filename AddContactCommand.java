package contacts;

public class AddContactCommand extends Command {
    AddContactCommand(Contacts contacts) {
        super(contacts);
    }

    @Override
    public void execute() {
        System.out.print("Enter the type (person, organization): ");
        String type = contacts.scanner.next();

        if (type.equals("person")) {
            Contact contact = new Contact(contacts);
            contact.create();
        } else if (type.equals("organization")){
            Organization organization = new Organization(contacts);
            organization.create();
        }

        System.out.println("The record added.\n");
    }
}
