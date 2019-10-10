package phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    private static int performLinearSearch(List<String> requests, List<String> names) {
        int found = 0;
        for (String request : requests) {
            for (String name : names) {
                if (request.equals(name)) {
                    found++;
                    break;
                }
            }
        }
        return found;
    }

    private static int blockLinearSearch(String[] array, String target, int left, int right) {
        for (int i = right; i > left; i--) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    private static int getPartition(String[] names, int index1, int index2) {
        String pivotValue = names[index1];
        while (index1 < index2) {
            String value1;
            String value2;
            while ((value1 = names[index1]).compareTo(pivotValue) < 0) {
                index1 += 1;
            }
            while ((value2 = names[index2]).compareTo(pivotValue) > 0) {
                index2 -= 1;
            }
            names[index1] = value2;
            names[index2] = value1;
        }
        return index1;
    }


    private static void quicksort(String[] names, int index1, int index2) {
        if (index1 >= index2) {
            return;
        }
        int pivotIndex = getPartition(names, index1, index2);
        quicksort(names, index1, pivotIndex);
        quicksort(names, pivotIndex+1, index2);
    }

    private static int binarySearch(String names[], int start, int end, String target){
        int mid = start + (end-start)/2;

        if (names[mid].compareTo(target) == 0){
            return mid;
        }
        if (names[mid].compareTo(target) > 0){
            return binarySearch(names, start, mid-1, target);
        } else if(names[mid].compareTo(target) < 0){
            return binarySearch(names, mid+1, end, target);
        }
        return -1;
    }

    private static int jumpSearch(String[] array, String target) {
        int currentRight = 0;
        int prevRight = 0;

        if (array.length == 0) {
            return -1;
        }
        if (array[currentRight].equals(target)) {
            return 0;
        }
        int jumpLength = (int) Math.sqrt(array.length);
        while (currentRight < array.length - 1) {
            currentRight = Math.min(array.length - 1, currentRight + jumpLength);
            if (array[currentRight].compareTo(target) >=0) {
                break;
            }
            prevRight = currentRight;
        }

        if (currentRight == array.length - 1 && target.compareTo(array[currentRight]) >0) {
            return -1;
        }

        return blockLinearSearch(array, target, prevRight, currentRight);
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

    public static List<String> performBubbleSort(long limit, List<String> names) {
        String temp;
        Long start = System.currentTimeMillis();
        boolean finished = true;

        bubbleSort:
        for (int j = 0; j < names.size(); j++) {
            for (int i = j + 1; i < names.size(); i++) {
                if (names.get(i).compareTo(names.get(j)) < 0) {
                    if ((System.currentTimeMillis() - start) > limit) {
                        finished = false;
                        break bubbleSort;
                    }
                    temp = names.get(j);
                    names.set(j, names.get(i));
                    names.set(i, temp);
                }
            }
        }
        if (finished) {
            return names;
        } else {
            return Collections.<String>emptyList();
        }
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


        long start = System.currentTimeMillis();
        int found = performLinearSearch(requests, names);
        long timePassed = System.currentTimeMillis() - start;
        long[] executionTime = getExecutionTime(start);

        System.out.printf("Found %s / %s entries. Time taken: %s min. %s sec. %s ms.\n\n",
                found, requests.size(),
                executionTime[0], executionTime[1], executionTime[2]);


        System.out.println("Start searching (bubble sort + jump search)...");

        start = System.currentTimeMillis();
        List<String> sortedNames = performBubbleSort(timePassed / 2, names);

        long sortingTime = System.currentTimeMillis() - start;
        long[] sort;
        long[] totalTime;
        long[] search;
        long searchingTime;
        found = 0;
        String[] sortedArr = sortedNames.stream().toArray(String[]::new);


        if (sortedNames.size() == 0) {
            start = System.currentTimeMillis();
            found = performLinearSearch(requests, names);
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
            for (String request : requests) {
                if (jumpSearch(sortedArr, request) > -1) {
                    found++;
                }
            }

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
        start = System.currentTimeMillis();
        String[] quickSorted = names.stream().toArray(String[]::new);
        quicksort(quickSorted, 0, quickSorted.length - 1);
        sortingTime = System.currentTimeMillis() - start;

        found = 0;
        start = System.currentTimeMillis();
        for (String request : requests) {
            if (binarySearch(quickSorted, 0, quickSorted.length - 1, request) > -1) {
                found++;
            }
        }
        searchingTime = System.currentTimeMillis() - start;
        totalTime = convertMillis(sortingTime + searchingTime);
        sort = convertMillis(sortingTime);
        search = convertMillis(searchingTime);

        System.out.printf("Found %s / %s entries. Time taken: %s min. %s sec. %s ms.\n",
                found, requests.size(),
                totalTime[0], totalTime[1], totalTime[2]);
        System.out.printf("Sorting time: %s min. %s sec. %s ms.\n",
                sort[0], sort[1], sort[2]);
        System.out.printf("Searching time: %s min. %s sec. %s ms.",
                search[0], search[1], search[2]);

    }

}
