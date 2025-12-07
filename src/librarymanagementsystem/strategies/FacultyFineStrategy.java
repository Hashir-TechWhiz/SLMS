package librarymanagementsystem.strategies;

public class FacultyFineStrategy implements FineStrategy {
    public double calculateFine(long daysLate) {
        if (daysLate <= 0)
            return 0;
        return daysLate * 20.0; // LKR 20/day
    }
}
