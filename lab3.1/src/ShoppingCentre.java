public class ShoppingCentre implements House {

    private double square;
    private int floors;
    private int shops;
    private boolean foodCourt;


    @Override
    public void buildHouse() {
        System.out.println("--------------------------------------------------");
        System.out.println("Your individual house:");
        System.out.println("Square = " + square + " sq.m.");
        System.out.println("Floors = " + floors);
        System.out.println("Shops = " + shops);
        System.out.println("Food Court = " + (foodCourt ? "with food court" : "without food court"));
    }

    @Override
    public double pricePerSquare() {
        double basePrice = 2000;
        double ratioFloor = 1 + 0.2 * (floors - 1);
        double ratioShops = 1 + 0.01 * (shops - 10);
        double ratioCourt = foodCourt ? 1.3 : 1;
        return basePrice * ratioFloor + ratioShops + ratioCourt;
    }

    @Override
    public int countOfRooms() {
        return shops + 1;
    }

    @Override
    public void increaseSquare(double percent) {
        double newSquare = square * (1 + percent / 100);
        int newShops = (int) Math.round(shops * (1 + percent / 100));
        System.out.println("--------------------------------------------------");
        System.out.println("Square increase by" + percent + "% from " + square + " sq.m. to " + newSquare + " sq.m.");
        System.out.println("Count of shops increase by " + percent + "% from " + shops + " to " + newShops);

        square = newSquare;
        shops = newShops;
    }

    @Override
    public void rent(double price, int term) {
        double income = price * term;
        System.out.println("--------------------------------------------------");
        System.out.println("Price for rent = " + price + "$ in month");
        System.out.println("Term of rent = " + term);
        System.out.println("Total income = " + income + "$");
    }

    @Override
    public void repair(String room) {
        System.out.println("--------------------------------------------------");
        System.out.println("Renovation in the office " + room);
        System.out.println("Replaced the floor, furniture and wallpaper");
    }

    public void addCinema() {
        System.out.println("--------------------------------------------------");
        System.out.println("Cinema added");
    }
}
