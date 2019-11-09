package phonebook;

import java.util.concurrent.TimeUnit;

public interface Message {
    void print();
    void setSearchTime(long start);
    void setFound(int size);

    default long[] getExecutionTime(long start) {
        long millis = System.currentTimeMillis() - start;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        millis -= TimeUnit.SECONDS.toMillis(seconds);
        return new long[]{minutes, seconds, millis};
    }
}
