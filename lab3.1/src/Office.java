public class Office implements House {
    private double square;
    private int floors;
    private int offices;
    private boolean elevators;

    public Office(double square, int floors, int offices, boolean elevators) {
        this.square = square;
        this.floors = floors;
        this.offices = offices;
        this.elevators = elevators;
    }

    @Override
    public void buildHouse() {
        System.out.println("--------------------------------------------------");
        System.out.println("Your Office:");
        System.out.println("Square = " + square + " sq.m.");
        System.out.println("Floors = " + floors);
        System.out.println("Count of offices = " + offices);
        System.out.println("Elevators = " + (elevators ? "with elevators" : "without elevators"));

    }

    @Override
    public double pricePerSquare() {
        double basePrice = 1500;
        double ratioFloor = 1 + 0.15 * (floors - 1);
        double ratioOffices = 1 + 0.02 * (offices - 10);
        double ratioElevator = elevators ? 1.2 : 1;
        return basePrice * ratioFloor + ratioOffices + ratioElevator;
    }

    @Override
    public int countOfRooms() {
        return offices + 1;
    }

    @Override
    public void increaseSquare(double percent) {
        double newSquare = square * (1 + percent / 100);
        int newOffices = (int) Math.round(offices * (1 + percent / 100));
        System.out.println("--------------------------------------------------");
        System.out.println("Square increase by" + percent + "% from " + square + " sq.m. to " + newSquare + " sq.m.");
        System.out.println("Count of offices increase by " + percent + "% from " + offices + " to " + newOffices);

        square = newSquare;
        offices = newOffices;
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

    public void addParking() {
        System.out.println("--------------------------------------------------");
        System.out.println("Parking added");
    }
}
