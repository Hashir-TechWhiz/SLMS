package librarymanagementsystem.core;

import java.util.Scanner;

import librarymanagementsystem.books.Book;
import librarymanagementsystem.books.BookBuilder;
import librarymanagementsystem.books.BorrowRecord;
import librarymanagementsystem.commands.BorrowCommand;
import librarymanagementsystem.commands.CancelReservationCommand;
import librarymanagementsystem.commands.Command;
import librarymanagementsystem.commands.ReserveCommand;
import librarymanagementsystem.commands.ReturnCommand;
import librarymanagementsystem.users.Faculty;
import librarymanagementsystem.users.Guest;
import librarymanagementsystem.users.Student;
import librarymanagementsystem.users.User;

import java.util.List;

public class LibraryManagementSystem {

    private static Library lib = Library.getInstance();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        seedSampleData();
        System.out.println("=== Smart Library Management System (SLMS) ===");
        boolean running = true;
        while (running) {
            showMainMenu();
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    bookMenu();
                    break;
                case "2":
                    userMenu();
                    break;
                case "3":
                    borrowReturnMenu();
                    break;
                case "4":
                    reportsMenu();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        System.out.println("Goodbye.");
        sc.close();
    }

    private static void showMainMenu() {
        System.out
                .println("\nMain Menu:\n1. Book Menu\n2. User Menu\n3. Borrow / Return / Reserve\n4. Reports\n0. Exit");
        System.out.print("Choose: ");
    }

    // Book Menu
    private static void bookMenu() {
        System.out.println(
                "\nBook Menu:\n" +
                        "1. Add book\n" +
                        "2. Update book\n" +
                        "3. List books\n" +
                        "4. Remove book\n" +
                        "0. Back");
        System.out.print("Choose: ");
        String ch = sc.nextLine();
        switch (ch) {
            case "1":
                addBook();
                break;
            case "2":
                updateBook();
                break;
            case "3":
                listBooks();
                break;
            case "4":
                removeBook();
                break;
            default:
                break;
        }
    }

    private static void addBook() {
        System.out.print("Book ID: ");
        String id = sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("Category: ");
        String cat = sc.nextLine();
        System.out.print("ISBN: ");
        String isbn = sc.nextLine();
        Book b = new BookBuilder(id, title).author(author).category(cat).isbn(isbn).build();
        lib.addBook(b);
        System.out.println("Added: " + b);
    }

    private static void updateBook() {
        System.out.print("Enter Book ID to update: ");
        String id = sc.nextLine();

        System.out.print("New Title (leave blank to keep unchanged): ");
        String title = sc.nextLine();

        System.out.print("New Author (leave blank to keep unchanged): ");
        String author = sc.nextLine();

        System.out.print("New Category (leave blank to keep unchanged): ");
        String category = sc.nextLine();

        System.out.print("New ISBN (leave blank to keep unchanged): ");
        String isbn = sc.nextLine();

        lib.updateBook(id, title, author, category, isbn);
    }

    private static void listBooks() {
        System.out.println("\nBooks:");
        for (Book b : lib.getBooks())
            System.out.println(b);
    }

    private static void removeBook() {
        System.out.print("Book ID to remove: ");
        String id = sc.nextLine();
        Book b = lib.findBookById(id);
        if (b != null) {
            lib.removeBook(b);
            System.out.println("Removed.");
        } else
            System.out.println("Not found.");
    }

    // User Menu
    private static void userMenu() {
        System.out.println("\nUser Menu:\n1. Add user\n2. List users\n0. Back");
        System.out.print("Choose: ");
        String ch = sc.nextLine();
        switch (ch) {
            case "1":
                addUserCLI();
                break;
            case "2":
                listUsersCLI();
                break;
            default:
                break;
        }
    }

    private static void addUserCLI() {
        System.out.print("User ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Contact No: ");
        String contact = sc.nextLine();
        System.out.print("Type (student/faculty/guest): ");
        String type = sc.nextLine().toLowerCase();
        User u = null;
        if (type.equals("student"))
            u = new Student(id, name, email, contact);
        else if (type.equals("faculty"))
            u = new Faculty(id, name, email, contact);
        else
            u = new Guest(id, name, email, contact);
        lib.addUser(u);
        System.out.println("Added user: " + u.getName() + " (" + u.getMembershipType() + ")");
    }

    private static void listUsersCLI() {
        System.out.println("\nUsers:");
        for (User u : lib.getUsers())
            System.out.println(u.getUserId() + " - " + u.getName() + " (" + u.getMembershipType() + ")");
    }

    // Borrow/Return Menu
    private static void borrowReturnMenu() {
        System.out.println(
                "\nBorrow Menu:\n1. Borrow book\n2. Return book\n3. Reserve book\n4. Cancel reservation\n0. Back");
        System.out.print("Choose: ");
        String ch = sc.nextLine();
        switch (ch) {
            case "1":
                borrowBookCLI();
                break;
            case "2":
                returnBookCLI();
                break;
            case "3":
                reserveBookCLI();
                break;
            case "4":
                cancelReservationCLI();
                break;
            default:
                break;
        }
    }

    private static void borrowBookCLI() {
        System.out.print("User ID: ");
        String uid = sc.nextLine();
        System.out.print("Book ID: ");
        String bid = sc.nextLine();
        User u = lib.findUserById(uid);
        Book b = lib.findBookById(bid);
        if (u == null || b == null) {
            System.out.println("User or book not found.");
            return;
        }
        Command c = new BorrowCommand(b, u);
        lib.executeCommand(c);
    }

    private static void returnBookCLI() {
        System.out.print("Book ID: ");
        String bid = sc.nextLine();
        Book b = lib.findBookById(bid);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }
        Command c = new ReturnCommand(b);
        lib.executeCommand(c);
    }

    private static void reserveBookCLI() {
        System.out.print("User ID: ");
        String uid = sc.nextLine();
        System.out.print("Book ID: ");
        String bid = sc.nextLine();
        User u = lib.findUserById(uid);
        Book b = lib.findBookById(bid);
        if (u == null || b == null) {
            System.out.println("User or book not found.");
            return;
        }
        Command c = new ReserveCommand(b, u);
        lib.executeCommand(c);
    }

    private static void cancelReservationCLI() {
        System.out.print("User ID: ");
        String uid = sc.nextLine();
        System.out.print("Book ID: ");
        String bid = sc.nextLine();
        User u = lib.findUserById(uid);
        Book b = lib.findBookById(bid);
        if (u == null || b == null) {
            System.out.println("User or book not found.");
            return;
        }
        Command c = new CancelReservationCommand(b, u);
        lib.executeCommand(c);
    }

    // Reports
    private static void reportsMenu() {
        System.out.println("\nReports:\n1. Most borrowed book\n2. Active borrowers\n3. Overdue books\n0. Back");
        System.out.print("Choose: ");
        String ch = sc.nextLine();
        switch (ch) {
            case "1":
                reportMostBorrowed();
                break;
            case "2":
                reportActiveBorrowers();
                break;
            case "3":
                reportOverdueBooks();
                break;
            default:
                break;
        }
    }

    private static void reportMostBorrowed() {
        Book b = lib.mostBorrowedBook();
        if (b == null)
            System.out.println("No books.");
        else
            System.out.println("Most borrowed: " + b.getTitle() + " (" + b.getTotalBorrows() + ")");
    }

    private static void reportActiveBorrowers() {
        List<User> list = lib.activeBorrowers();
        System.out.println("Active borrowers:");
        for (User u : list)
            System.out.println(u.getName() + " - Borrowed: " + u.getBorrowedRecords().size());
    }

    private static void reportOverdueBooks() {
        List<BorrowRecord> list = lib.overdueBooks();
        System.out.println("Overdue records:");
        for (BorrowRecord r : list) {
            System.out.println(r.getBook().getTitle() + " - Borrower: " + r.getUser().getName() + " - Days late: "
                    + r.daysLate() + " - Fine: " + r.getUser().getFineStrategy().calculateFine(r.daysLate()));
        }
    }

    // seed sample data
    private static void seedSampleData() {
        Book b1 = new BookBuilder("B001", "Intro to Java").author("Author A").category("Programming").isbn("111")
                .build();
        Book b2 = new BookBuilder("B002", "Data Structures").author("Author B").category("CS").isbn("222").build();
        lib.addBook(b1);
        lib.addBook(b2);
        User s1 = new Student("U001", "Hashir", "hashir@gmail.com", "077111");
        User f1 = new Faculty("U002", "Dhasun", "dasun@gmail.com", "077222");
        lib.addUser(s1);
        lib.addUser(f1);
    }
}
