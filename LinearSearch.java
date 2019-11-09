package phonebook;

import java.util.List;

public class LinearSearch implements SearchingAlgorithm {
    private List<String> list;

    @Override
    public void sortArray(List<String> list) {
        this.list = list;
    }

    @Override
    public int findInArray(List<String> requests) {
        int found = 0;
        for (String request : requests) {
            for (String name : list) {
                if (request.equals(name)) {
                    found++;
                    break;
                }
            }
        }
        return found;
    }
}
