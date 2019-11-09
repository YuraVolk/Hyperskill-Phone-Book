package phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static SearchingAlgorithm algorithm;
    private static List<String> names = new ArrayList<>();
    private static List<String> numbers = new ArrayList<>();
    private static List<String> requests;
    private static MessageBuilder builder;

    private static long performLinearSearch() {
        algorithm = new LinearSearch();
        algorithm.sortArray(names);
        System.out.println("Start searching (linear search)...");
        long start = System.currentTimeMillis();
        int found = algorithm.findInArray(requests);
        long timePassed = System.currentTimeMillis() - start;

        builder.produceMessage(found, start);

        return timePassed;
    }

    private static void performJumpSearch(long timePassed) {
        int found;
        long start;

        System.out.println("Start searching (bubble sort + jump search)...");

        JumpSearch jumpSearch = new JumpSearch();
        jumpSearch.setLimit(timePassed / 2);
        long startSort = System.currentTimeMillis();
        jumpSearch.sortArray(names);

        if (!jumpSearch.getFinish()) {
            start = System.currentTimeMillis();
            algorithm = new LinearSearch();
            algorithm.sortArray(names);
            found = algorithm.findInArray(requests);

            builder.produceMessage(found, start, startSort, 1);
        } else {
            start = System.currentTimeMillis();
            found = jumpSearch.findInArray(requests);
            builder.produceMessage(found, start, startSort, 0);
        }
    }

    private static void performBinarySearch() {
        System.out.println("Start searching (quick sort + binary search)...");
        algorithm = new BinarySearch();
        long startSort = System.currentTimeMillis();
        algorithm.sortArray(names);

        long start = System.currentTimeMillis();
        int found = algorithm.findInArray(requests);

        builder.produceMessage(found, start, startSort, 0);
    }

    private static void performHashTableSearch() {
        System.out.println("Start searching (hash table)...");
        HashTableSearch searchHash = new HashTableSearch();
        long startSort = System.currentTimeMillis();
        searchHash.setNumbers(numbers);
        searchHash.sortArray(names);

        long start = System.currentTimeMillis();
        int found = searchHash.findInArray(requests);

        builder.produceMessage(found, start, startSort, 2);
    }

    private static void init() {
        try {
            List<String> directoryLines = Files.readAllLines(
                    Paths.get("C:\\Hyperskill\\directory.txt"));
            directoryLines.forEach(line -> {
                numbers.add(line.substring(0, line.indexOf(" ")));
                names.add(line.substring(line.indexOf(" ") + 1));
            });
            requests = Files.readAllLines(
                    Paths.get("C:\\Hyperskill\\find.txt"));
             builder = new MessageBuilder(requests.size());
        } catch (IOException e) {
            System.out.println("File requests not found.");
            System.exit(1);
        }
    }

    public static void main(String[] args)  {
        init();

        long timePassed = performLinearSearch();
        performJumpSearch(timePassed);
        performBinarySearch();
        performHashTableSearch();
    }

}
