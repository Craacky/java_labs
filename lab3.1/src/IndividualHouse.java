public class IndividualHouse implements House {
    private double square;
    private int floors;
    private int bedrooms;
    private int bathrooms;
    private boolean garage;

    public IndividualHouse(double square, int floors, int bedrooms, int bathrooms, boolean garage) {
        this.square = square;
        this.floors = floors;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.garage = garage;
    }

    @Override
    public void buildHouse() {
        System.out.println("--------------------------------------------------");
        System.out.println("Your individual house:");
        System.out.println("Square = " + square + " sq.m.");
        System.out.println("Floors = " + floors);
        System.out.println("Bedrooms = " + bedrooms);
        System.out.println("Bathroom = " + bathrooms);
        System.out.println("Garage = " + (garage ? "with garage" : "without garage"));
    }

    @Override
    public double pricePerSquare() {
        double basePrice = 1000;
        double ratioFloors = 1 + 0.1 * (floors - 1);
        double ratioBedrooms = 1 + 0.05 * (bedrooms - 2);
        double ratioBathrooms = 1 + 0.03 * (bathrooms - 1);
        double ratioGarage = garage ? 1.1 : 1;
        return basePrice * ratioFloors * ratioBedrooms * ratioBathrooms * ratioGarage;
    }

    @Override
    public int countOfRooms() {
        return bedrooms + bathrooms + 1;
    }

    @Override
    public void increaseSquare(double percent) {
        double newSquare = square * (1 + percent / 100);
        int newBedrooms = (int) Math.round(bedrooms * (1 + percent / 100));
        int newBathrooms = (int) Math.round(bathrooms * (1 + percent / 100));
        System.out.println("Square increase by " + percent + "% from " + square + " sq.m. to " + newSquare + " sq.m");
        System.out.println("Count of bedrooms increased by " + percent + "% from " + bedrooms + " to " + newBedrooms);
        System.out.println("Count of bathrooms  " + percent + "% from " + bathrooms + " to " + newBathrooms);

        square = newSquare;
        bedrooms = newBedrooms;
        bathrooms = newBathrooms;
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
        System.out.println("Renovation in the room " + room);
        System.out.println("Replaced the floor, furniture and wallpaper");
    }

    public void addSwimmingPool() {
        System.out.println("--------------------------------------------------");
        System.out.println("Swimming poll added");
    }
}
