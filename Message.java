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

    default long getMilliseconds(long[] arr) {
        long ms = arr[2];
        ms += arr[0] * 1000 * 60;
        ms += arr[1] * 1000;
        return ms;
    }

    default long[] convertMillis(long millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        millis -= TimeUnit.SECONDS.toMillis(seconds);
        return new long[]{minutes, seconds, millis};
    }
}
