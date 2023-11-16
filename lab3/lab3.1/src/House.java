public interface House {
    void buildHouse();
    double pricePerSquare();
    int countOfRooms();
    void increaseSquare(double percent);
    void rent(double price, int term);
    void repair(String room);
}
