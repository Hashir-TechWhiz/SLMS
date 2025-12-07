package librarymanagementsystem.strategies;

public class StudentFineStrategy implements FineStrategy {
    public double calculateFine(long daysLate) {
        if (daysLate <= 0)
            return 0;
        return daysLate * 50.0; // LKR 50/day
    }
}
