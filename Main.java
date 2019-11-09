package phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    private static SearchingAlgorithm algorithm;

    private static int hastableSearch(List<String> requests,
                                      Hashtable<String, String> table) {
        int count = 0;
        for (String request : requests) {
            if (table.get(request) != null) {
                count++;
            }
        }
        return count;
    }

    private static long[] getExecutionTime(long start) {
        long millis = System.currentTimeMillis() - start;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        millis -= TimeUnit.SECONDS.toMillis(seconds);
        return new long[]{minutes, seconds, millis};
    }

    private static long[] convertMillis(long millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        millis -= TimeUnit.SECONDS.toMillis(seconds);
        return new long[]{minutes, seconds, millis};
    }



    public static void main(String[] args) throws IOException {
        List<String> directoryLines = Files.readAllLines(Paths.get("C:\\Hyperskill\\directory.txt"));
        List<String> names = new ArrayList<>();
        List<String> numbers = new ArrayList<>();
        directoryLines.forEach(line -> {
            numbers.add(line.substring(0, line.indexOf(" ")));
            names.add(line.substring(line.indexOf(" ") + 1));
        });
        List<String> requests = Files.readAllLines(Paths.get("C:\\Hyperskill\\find.txt"));
        System.out.println("Start searching (linear search)...");

        algorithm = new LinearSearch();
        algorithm.sortArray(names);

        long start = System.currentTimeMillis();
        int found = algorithm.findInArray(requests);
        long timePassed = System.currentTimeMillis() - start;
        long[] executionTime = getExecutionTime(start);

        System.out.printf("Found %s / %s entries. Time taken: %s min. %s sec. %s ms.\n\n",
                found, requests.size(),
                executionTime[0], executionTime[1], executionTime[2]);


        System.out.println("Start searching (bubble sort + jump search)...");

        JumpSearch jumpSearch = new JumpSearch();
        jumpSearch.setLimit(timePassed / 2);
        start = System.currentTimeMillis();
        jumpSearch.sortArray(names);
        long sortingTime = System.currentTimeMillis() - start;
        long[] sort;
        long[] totalTime;
        long[] search;
        long searchingTime;
        found = 0;

        if (!jumpSearch.getFinish()) {
            start = System.currentTimeMillis();
            algorithm = new LinearSearch();
            algorithm.sortArray(names);
            found = algorithm.findInArray(requests);
            timePassed = System.currentTimeMillis() - start;
            executionTime = convertMillis(timePassed);
            sort = convertMillis(sortingTime);
            totalTime = convertMillis(sortingTime + timePassed);

            System.out.printf("Found %s / %s entries. Time taken: %s min. %s sec. %s ms.\n",
                    found, requests.size(),
                    totalTime[0], totalTime[1], totalTime[2]);
            System.out.printf("Sorting time: %s min. %s sec. %s ms. - STOPPED, moved to linear search\n",
                    sort[0], sort[1], sort[2]);
            System.out.printf("Searching time: %s min. %s sec. %s ms.\n\n",
                    executionTime[0], executionTime[1], executionTime[2]);
        } else {
            start = System.currentTimeMillis();
            found = jumpSearch.findInArray(requests);

            searchingTime = System.currentTimeMillis() - start;
            search = convertMillis(searchingTime);
            sort = convertMillis(sortingTime);
            totalTime = convertMillis(searchingTime + sortingTime);

            System.out.printf("Found %s / %s entries. Time taken: %s min. %s sec. %s ms.\n",
                    found, requests.size(),
                    totalTime[0], totalTime[1], totalTime[2]);
            System.out.printf("Sorting time: %s min. %s sec. %s ms.\n",
                    sort[0], sort[1], sort[2]);
            System.out.printf("Searching time: %s min. %s sec. %s ms.\n\n",
                    search[0], search[1], search[2]);
        }

        System.out.println("Start searching (quick sort + binary search)...");
        algorithm = new BinarySearch();
        start = System.currentTimeMillis();
        algorithm.sortArray(names);

        sortingTime = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();

        found = algorithm.findInArray(requests);
        searchingTime = System.currentTimeMillis() - start;
        totalTime = convertMillis(sortingTime + searchingTime);
        sort = convertMillis(sortingTime);
        search = convertMillis(searchingTime);

        System.out.printf("Found %s / %s entries. Time taken: %s min. %s sec. %s ms.\n",
                found, requests.size(),
                totalTime[0], totalTime[1], totalTime[2]);
        System.out.printf("Sorting time: %s min. %s sec. %s ms.\n",
                sort[0], sort[1], sort[2]);
        System.out.printf("Searching time: %s min. %s sec. %s ms.\n\n",
                search[0], search[1], search[2]);


        System.out.println("Start searching (hash table)...");
        HashTableSearch searchHash = new HashTableSearch();
        start = System.currentTimeMillis();
        searchHash.setNumbers(numbers);
        searchHash.sortArray(names);
        sortingTime = System.currentTimeMillis() - start;
        sort = convertMillis(sortingTime);
        start = System.currentTimeMillis();
        found = searchHash.findInArray(requests);
        searchingTime = System.currentTimeMillis() - start;
        totalTime = convertMillis(sortingTime + searchingTime);
        System.out.printf("Found %s / %s entries. Time taken: %s min. %s sec. %s ms.\n",
                found, requests.size(),
                totalTime[0], totalTime[1], totalTime[2]);
        System.out.printf("Sorting time: %s min. %s sec. %s ms.\n",
                sort[0], sort[1], sort[2]);
        System.out.printf("Searching time: %s min. %s sec. %s ms.\n\n",
                search[0], search[1], search[2]);
    }

}
