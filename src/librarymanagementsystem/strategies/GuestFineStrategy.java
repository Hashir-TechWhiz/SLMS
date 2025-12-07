package librarymanagementsystem.strategies;

public class GuestFineStrategy implements FineStrategy {
    public double calculateFine(long daysLate) {
        if (daysLate <= 0)
            return 0;
        return daysLate * 100.0; // LKR 100/day
    }
}
