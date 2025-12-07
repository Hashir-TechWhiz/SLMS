package librarymanagementsystem.users;

import librarymanagementsystem.strategies.FineStrategy;
import librarymanagementsystem.strategies.StudentFineStrategy;

public class Student extends User {
    public Student(String userId, String name, String email, String contactNo) {
        super(userId, name, email, contactNo, "Student");
    }

    public int getBorrowLimit() {
        return 5;
    }

    public FineStrategy getFineStrategy() {
        return new StudentFineStrategy();
    }
}
