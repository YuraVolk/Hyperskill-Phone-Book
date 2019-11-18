package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;

abstract class Entry implements Serializable {
    private String name;
    private String phone;
    private LocalDateTime timeCreated;
    private LocalDateTime timeEdited;
    static ContactValidator validator = new ContactValidator();
    static Contacts contacts;

    Entry(Contacts contacts) {
        this.contacts = contacts;
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void setPhone(String phone) {
        if (!validator.validateNumber(phone)) {
            System.out.println("Wrong number format!");
            this.phone = "";
        } else {
            this.phone = phone;
        }
    }

    String getPhone() {
        return phone;
    }

    void setTimeCreated() {
        timeCreated = LocalDateTime.now();
        timeEdited = LocalDateTime.now();
    }

    void setTimeEdited() {
        timeEdited = LocalDateTime.now();
    }

    LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    LocalDateTime getTimeEdited() {
        return timeEdited;
    }

    void printCompact() {
        System.out.println(name);
    }

    abstract void print();
    abstract String getFields();
    abstract void setField(String field, String value);
    abstract void create();
    abstract String returnFieldsString();
}
