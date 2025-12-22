package librarymanagementsystem.users;

import java.util.ArrayList;
import java.util.List;

import librarymanagementsystem.books.BorrowRecord;
import librarymanagementsystem.observers.Observer;
import librarymanagementsystem.strategies.FineStrategy;

public abstract class User implements Observer {

    protected String userId;
    protected String name;
    protected String email;
    protected String contactNo;
    protected String membershipType;

    protected List<BorrowRecord> borrowedRecords = new ArrayList<>();

    public User(String userId, String name, String email, String contactNo, String membershipType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
        this.membershipType = membershipType;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }

    // Borrow records
    public void addBorrowedRecord(BorrowRecord r) {
        borrowedRecords.add(r);
    }

    public List<BorrowRecord> getBorrowedRecords() {
        return borrowedRecords;
    }

    // Observer pattern
    @Override
    public void update(String message) {
        System.out.println("[Notification for " + name + "] " + message);
    }

    // Strategy pattern
    public abstract int getBorrowLimit();

    public abstract FineStrategy getFineStrategy();
}
