public class Main {
    public static void main(String[] args) {
        IndividualHouse individualHouse1 = new IndividualHouse(230, 2, 5, 1, true);
        Office office1 = new Office(1024, 5, 30, true);
        ShoppingCentre shoppingCentre1 = new ShoppingCentre(2000, 3, 60, true);

        individualHouse1.buildHouse();
        System.out.println("--------------------------------------------------");
        System.out.println("Price per Square = " + individualHouse1.pricePerSquare() + "$");
        System.out.println("--------------------------------------------------");
        System.out.println("Total rooms = " + individualHouse1.countOfRooms());
        individualHouse1.increaseSquare(10);
        individualHouse1.rent(1230, 3);
        individualHouse1.repair("Bedroom");
        individualHouse1.addSwimmingPool();
        System.out.println("-----INDIVIDUAL HOUSE BLOCK END-----");

        office1.buildHouse();
        System.out.println("--------------------------------------------------");
        System.out.println("Price per Square = " + office1.pricePerSquare() + "$");
        System.out.println("--------------------------------------------------");
        System.out.println("Total rooms = " + office1.countOfRooms());
        office1.increaseSquare(50);
        office1.rent(1500, 12);
        office1.repair("AI-TECH");
        office1.addParking();
        System.out.println("-----OFFICE BLOCK END-----");

        shoppingCentre1.buildHouse();
        System.out.println("--------------------------------------------------");
        System.out.println("Price per Square = " + shoppingCentre1.pricePerSquare() + "$");
        System.out.println("--------------------------------------------------");
        System.out.println("Total rooms = " + shoppingCentre1.countOfRooms());
        shoppingCentre1.increaseSquare(99);
        shoppingCentre1.rent(2300, 24);
        shoppingCentre1.repair("Santa");
        shoppingCentre1.addCinema();
        System.out.println("-----PROGRAM END-----");
    }
}