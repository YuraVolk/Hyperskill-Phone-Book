package phonebook;

public class NoSortMessage implements Message {
    private long[] searchTime;
    private int found;
    private int size;

    NoSortMessage(int size) {
        this.size = size;
    }

    @Override
    public void print() {
        System.out.printf("Found %s / %s entries. Time taken: %s min. %s sec. %s ms.\n\n",
                found, size,
                searchTime[0], searchTime[1], searchTime[2]);
    }

    @Override
    public void setSearchTime(long start) {
        searchTime = getExecutionTime(start);
    }

    @Override
    public void setFound(int found) {
        this.found = found;
    }
}
