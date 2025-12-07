package librarymanagementsystem.users;

import librarymanagementsystem.strategies.FacultyFineStrategy;
import librarymanagementsystem.strategies.FineStrategy;

public class Faculty extends User {
    public Faculty(String userId, String name, String email, String contactNo) {
        super(userId, name, email, contactNo, "Faculty");
    }

    public int getBorrowLimit() {
        return 10;
    }

    public FineStrategy getFineStrategy() {
        return new FacultyFineStrategy();
    }
}
