package contacts;

import java.io.Serializable;

class Organization extends Entry implements Serializable {
    private String address;

    Organization(Contacts contacts) {
        super(contacts);
    }

    @Override
    void print() {
        String number = getPhone().length() == 0 ? "[no number]" : getPhone();

        System.out.printf("Organization name: %s\n" +
                "Address: %s\n" +
                "Number: %s\n" +
                "Time created: %s\n" +
                "Time last edit: %s\n",
                getName(), address, number,
                getTimeCreated(), getTimeEdited());
    }

    @Override
    String getFields() {
        return "name, address, number";
    }

    @Override
    void setField(String field, String value) {
        switch (field) {
            case "name":
                setName(value);
                break;
            case "address":
                address = value;
                break;
            case "number":
                setPhone(value);
                break;
        }

        setTimeEdited();
    }

    @Override
    void create() {
        System.out.print("Enter the organization name: ");
        contacts.scanner.nextLine();
        setName(contacts.scanner.nextLine());
        System.out.print("Enter the address: ");
        this.address = contacts.scanner.nextLine();
        System.out.print("Enter the number: ");
        setPhone(contacts.scanner.nextLine());

        setTimeCreated();

        contacts.contacts.add(this);
    }

    @Override
    String returnFieldsString() {
        return getName() + getPhone() + address;
    }
}
