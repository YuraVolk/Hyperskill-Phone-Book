package phonebook;

public class SortMessage implements Message {
    private long[] sortTime;
    private long[] searchTime;
    private long[] totalTime;
    private int found;
    private int size;
    private int differ;

    SortMessage(int size) {
        this.size = size;
    }

    @Override
    public void print() {
        System.out.printf("Found %s / %s entries. Time taken: %s min. %s sec. %s ms.\n",
                found, size,
                totalTime[0], totalTime[1], totalTime[2]);
        if (differ == 2) {
            System.out.printf("Sorting time: %s min. %s sec. %s ms.\n",
                    sortTime[0], sortTime[1], sortTime[2]);
        } else if (differ == 1) {
            System.out.printf("Sorting time: %s min. %s sec. %s ms. " +
                            " - STOPPED, moved to linear search\n",
                    sortTime[0], sortTime[1], sortTime[2]);
        } else {
            System.out.printf("Creating time: %s min. %s sec. %s ms.\n",
                    sortTime[0], sortTime[1], sortTime[2]);
        }
        System.out.printf("Searching time: %s min. %s sec. %s ms.\n\n",
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

    void setSortTime(long start) {
        sortTime = getExecutionTime(start);
        long millis1 = getMilliseconds(searchTime);
        long millis2 = getMilliseconds(sortTime);
        totalTime = convertMillis(millis1 + millis2);
    }

    void setDiffer(int differ) {
        this.differ = differ;
    }
}
