package librarymanagementsystem.users;

import librarymanagementsystem.strategies.FineStrategy;
import librarymanagementsystem.strategies.GuestFineStrategy;

public class Guest extends User {
    public Guest(String userId, String name, String email, String contactNo) {
        super(userId, name, email, contactNo, "Guest");
    }

    public int getBorrowLimit() {
        return 2;
    }

    public FineStrategy getFineStrategy() {
        return new GuestFineStrategy();
    }
}
