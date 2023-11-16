import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        HashMap<Integer, String> hashMap = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            int key = i;
            String value = "Value " + i + key;
            hashMap.put(key, value);
        }

        System.out.println("KEYS > 5:");
        for (Integer key : hashMap.keySet()) {
            if (key > 5) {
                System.out.println("Key: " + key + ", Value: " + hashMap.get(key));
            }
        }
        System.out.print("KEY = 0: ");
        if (hashMap.containsKey(0)) {
            String values = "";
            for (String value : hashMap.values()) {
                values += value + ", ";
            }
            System.out.print(values.substring(0, values.length() - 2) + "\n");
        } else {
            System.out.print("false\n");
        }

        long result = 1;
        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            if (entry.getKey() > 5) {
                result *= entry.getKey();
            }
        }
        System.out.println("Result: " + result);
    }
}
