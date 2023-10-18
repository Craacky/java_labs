import java.util.*;
public class Main {
    public static void main(String[] args) {
        HashMap<String, Tariff> tariffs = new HashMap<>();


        tariffs.put("Tariff6", new Tariff(600, 800, 3000));
        tariffs.put("Tariff2", new Tariff(200, 400, 1000));
        tariffs.put("Tariff5", new Tariff(500, 700, 2500));
        tariffs.put("Tariff4", new Tariff(400, 600, 2000));
        tariffs.put("Tariff3", new Tariff(300, 500, 1500));
        tariffs.put("Tariff1", new Tariff(100, 300, 500));

        int totalCustomers = 0;

        for (Map.Entry<String, Tariff> entry : tariffs.entrySet()) {
            totalCustomers = entry.getValue().getCustomers();
        }
        System.out.println("Total customers = " + totalCustomers);

        Scanner in = new Scanner(System.in);
        System.out.println("Enter max and min of range:");
        System.out.print("MIN = ");
        int min = in.nextInt();
        System.out.print("MAX = ");
        int max = in.nextInt();
        System.out.println("\nTariff in the company corresponding to the specified range of parameters:");

        for (Map.Entry<String, Tariff> entry : tariffs.entrySet()) {
            int subs = entry.getValue().subscription();
            if (subs >= min && subs <= max) {
                System.out.println(entry.getKey() + ":" + subs);
            }
        }

        TreeMap<String, Tariff> sortedMap = new TreeMap<>(tariffs);

        System.out.println("\nWithout sorting:");
        for (Map.Entry<String, Tariff> entry : tariffs.entrySet()) {
            System.out.println("Key: " + entry.getKey()
                    + " Subscription: "  + entry.getValue().subscription()
                    + " Customers: " + entry.getValue().getCustomers()
                    + " Minutes: " + entry.getValue().messages()
            );
        }

        System.out.println("\nWith sorting:");
        for (Map.Entry<String, Tariff> entry : sortedMap.entrySet()) {
            System.out.println("Key: " + entry.getKey()
                    + " Subscription: "  + entry.getValue().subscription()
                    + " Customers: " + entry.getValue().getCustomers()
                    + " Minutes: " + entry.getValue().messages()
            );
        }

    }
}

record Tariff(int subscription, int minutes, int messages) {
    public int getCustomers() {
        return (int) (Math.random() * 1000);
    }
}
