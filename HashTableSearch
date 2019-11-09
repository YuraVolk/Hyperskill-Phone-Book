package phonebook;

import java.util.Hashtable;
import java.util.List;

public class HashTableSearch implements SearchingAlgorithm {
    private Hashtable<String, String> table = new Hashtable<>();
    private List<String> numbers;

    @Override
    public void sortArray(List<String> list) {
        for(int i = 0; i < list.size(); i++){
            table.put(list.get(i), numbers.get(i));
        }
    }

    @Override
    public int findInArray(List<String> requests) {
        int found = 0;
        for (String request : requests) {
            if (table.get(request) != null) {
                found++;
            }
        }
        return found;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }
}
