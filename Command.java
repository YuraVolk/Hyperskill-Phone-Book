package contacts;

public abstract class Command {
    public Contacts contacts;

    Command(Contacts contacts) {
        this.contacts = contacts;
    }

    abstract void execute();
}
