package phonebook;

import java.util.List;

public class JumpSearch implements SearchingAlgorithm {
    private List<String> list;
    private long limit;
    private boolean isFinished = true;

    @Override
    public void sortArray(List<String> list) {
        this.list = list;

        String temp;
        Long start = System.currentTimeMillis();

        bubbleSort:
        for (int j = 0; j < list.size(); j++) {
            for (int i = j + 1; i < list.size(); i++) {
                if (list.get(i).compareTo(list.get(j)) < 0) {
                    if ((System.currentTimeMillis() - start) > limit) {
                        isFinished = false;
                        break bubbleSort;
                    }
                    temp = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, temp);
                }
            }
        }

        if (isFinished) {
            this.list = list;
        }
    }

    private int jumpSearch(String[] array, String target) {
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
            if (array[currentRight].compareTo(target) >= 0) {
                break;
            }
            prevRight = currentRight;
        }

        if (currentRight == array.length - 1 && target.compareTo(array[currentRight]) >0) {
            return -1;
        }

        return blockLinearSearch(array, target, prevRight, currentRight);
    }

    private int blockLinearSearch(String[] array, String target, int left, int right) {
        for (int i = right; i > left; i--) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int findInArray(List<String> requests) {
        String[] arr = list.stream().toArray(String[]::new);
        int found = 0;
        for (String request : requests) {
            if (jumpSearch(arr, request) > -1) {
                found++;
            }
        }
        return found;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public boolean getFinish() {
        return isFinished;
    }
}
