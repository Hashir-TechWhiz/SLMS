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
import librarymanagementsystem.utils.DateUtil;
import librarymanagementsystem.books.BookComponent;
import librarymanagementsystem.decorators.FeaturedBook;
import librarymanagementsystem.decorators.RecommendedBook;
import librarymanagementsystem.decorators.SpecialEditionBook;

import java.util.List;

public class LibraryManagementSystem {

    private static Library lib = Library.getInstance();
    private static Scanner sc = new Scanner(System.in);

    // Entry point of the Smart Library Management System
    public static void main(String[] args) {

        seedSampleData();

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
                    System.out.println("Please select a valid option (0-4).");
            }
        }

        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println(" Thank you for using SLMS. Goodbye!");
        System.out.println("--------------------------------------------------");

        sc.close();
    }

    // Displays the main menu of the system
    private static void showMainMenu() {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("|                                                |");
        System.out.println("|   SMART LIBRARY MANAGEMENT SYSTEM (SLMS)       |");
        System.out.println("|                                                |");
        System.out.println("==================================================");
        System.out.println("|                                                |");
        System.out.println("|  1. Book Management                            |");
        System.out.println("|  2. User Management                            |");
        System.out.println("|  3. Borrow / Return / Reserve                  |");
        System.out.println("|  4. Reports                                    |");
        System.out.println("|  0. Exit System                                |");
        System.out.println("|                                                |");
        System.out.println("==================================================");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

    // Displays and handles the book management menu
    private static void bookMenu() {

        System.out.println();
        System.out.println("----- Book Menu -----");
        System.out.println("1. Add book");
        System.out.println("2. Update book");
        System.out.println("3. List books");
        System.out.println("4. Search book by ID");
        System.out.println("5. Remove book");
        System.out.println("0. Back");
        System.out.println("---------------------");
        System.out.println();
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
                searchBookById();
                break;
            case "5":
                removeBook();
                break;
            default:
                break;
        }
    }

    // Adds a new book to the library with optional features
    private static void addBook() {

        System.out.println();

        System.out.print("Book ID    : ");
        String id = sc.nextLine();

        System.out.print("Title      : ");
        String title = sc.nextLine();

        System.out.print("Author     : ");
        String author = sc.nextLine();

        System.out.print("Category   : ");
        String cat = sc.nextLine();

        System.out.print("ISBN       : ");
        String isbn = sc.nextLine();

        // Create base book using Builder
        Book book = new BookBuilder(id, title)
                .author(author)
                .category(cat)
                .isbn(isbn)
                .build();

        // Decorator Selection
        System.out.println();
        System.out.println("Select Book Features");
        System.out.println("1. Featured");
        System.out.println("2. Recommended");
        System.out.println("3. Special Edition");
        System.out.println("0. None");
        System.out.print("Enter choice (you can chain e.g. 1,2): ");

        String choice = sc.nextLine().trim();

        BookComponent decorated = book;

        if (!choice.equals("0") && !choice.isEmpty()) {

            String[] selections = choice.split(",");

            for (String sel : selections) {
                switch (sel.trim()) {
                    case "1":
                        decorated = new FeaturedBook(decorated);
                        book.addTag("FEATURED");
                        break;
                    case "2":
                        decorated = new RecommendedBook(decorated);
                        book.addTag("RECOMMENDED");
                        break;
                    case "3":
                        decorated = new SpecialEditionBook(decorated);
                        book.addTag("SPECIAL");
                        break;
                    default:
                        break;
                }
            }
        }

        lib.addBook(book);

        System.out.println();
        System.out.println("Book added successfully.");
        System.out.println("Book Details:");
        System.out.println("----------------------------------");
        System.out.println(book);
        System.out.println("----------------------------------");
    }

    // Updates existing book details and optional features
    private static void updateBook() {

        System.out.print("Enter Book ID to update: ");
        String id = sc.nextLine();

        Book book = lib.findBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.print("New Title (leave blank to keep unchanged): ");
        String title = sc.nextLine();

        System.out.print("New Author (leave blank to keep unchanged): ");
        String author = sc.nextLine();

        System.out.print("New Category (leave blank to keep unchanged): ");
        String category = sc.nextLine();

        System.out.print("New ISBN (leave blank to keep unchanged): ");
        String isbn = sc.nextLine();

        // Existing update logic
        lib.updateBook(id, title, author, category, isbn);

        System.out.println();
        System.out.println("Update Book Features (optional)");
        System.out.println("1. Featured");
        System.out.println("2. Recommended");
        System.out.println("3. Special Edition");
        System.out.println("0. Keep existing features");
        System.out.print("Enter choice (you can chain e.g. 1,2): ");

        String choice = sc.nextLine().trim();

        if (!choice.equals("0") && !choice.isEmpty()) {

            book.clearTags();

            String[] selections = choice.split(",");

            for (String sel : selections) {
                switch (sel.trim()) {
                    case "1":
                        book.addTag("FEATURED");
                        break;
                    case "2":
                        book.addTag("RECOMMENDED");
                        break;
                    case "3":
                        book.addTag("SPECIAL");
                        break;
                    default:
                        break;
                }
            }
        }

        System.out.println("Book updated successfully.");
    }

    // Displays all books in a tabular format
    private static void listBooks() {

        System.out.println();
        System.out.println("All Books in the Library");
        System.out.println();
        System.out
                .println(
                        "--------------------------------------------------------------------------------------------------------------------------");
        System.out.printf(
                "| %-6s | %-20s | %-12s | %-15s | %-10s | %-12s | %-12s | %-10s |\n",
                "ID", "Title", "Author", "Category", "ISBN", "Status", "Features", "Borrows");
        System.out
                .println(
                        "--------------------------------------------------------------------------------------------------------------------------");

        if (lib.getBooks().isEmpty()) {
            System.out.println(
                    "| No books available.                                                                                                          |");
            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------------");
            return;
        }

        for (Book b : lib.getBooks()) {

            String features = "None";
            if (!b.getTags().isEmpty()) {
                features = String.join(", ", b.getTags());
            }

            System.out.printf(
                    "| %-6s | %-20s | %-12s | %-15s | %-10s | %-12s | %-12s | %-10s |\n",
                    b.getBookId(),
                    b.getTitle().length() > 25 ? b.getTitle().substring(0, 22) + "..." : b.getTitle(),
                    b.getAuthor(),
                    b.getCategory(),
                    b.getIsbn(),
                    b.getState().getStatus(),
                    features,
                    b.getTotalBorrows());
        }

        System.out
                .println(
                        "--------------------------------------------------------------------------------------------------------------------------");
    }

    // Searches a book by ID and displays its details
    private static void searchBookById() {

        System.out.println();
        System.out.print("Enter Book ID to search: ");
        String id = sc.nextLine();

        Book b = lib.findBookById(id);

        if (b == null) {
            System.out.println();
            System.out.println("Book not found.");
            return;
        }

        System.out.println();
        System.out.println("Book Found");
        System.out.println();

        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------");
        System.out.printf(
                "| %-6s | %-20s | %-12s | %-15s | %-10s | %-12s | %-12s | %-10s |\n",
                "ID", "Title", "Author", "Category", "ISBN", "Status", "Features", "Borrows");
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------");

        String features = "None";
        if (!b.getTags().isEmpty()) {
            features = String.join(", ", b.getTags());
        }

        System.out.printf(
                "| %-6s | %-20s | %-12s | %-15s | %-10s | %-12s | %-12s | %-10s |\n",
                b.getBookId(),
                b.getTitle().length() > 25 ? b.getTitle().substring(0, 22) + "..." : b.getTitle(),
                b.getAuthor(),
                b.getCategory(),
                b.getIsbn(),
                b.getState().getStatus(),
                features,
                b.getTotalBorrows());

        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------");
    }

    // Removes a book from the library by ID
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

    // Displays and handles the user management menu
    private static void userMenu() {

        System.out.println();
        System.out.println("----- User Menu -----");
        System.out.println("1. Add user");
        System.out.println("2. List users");
        System.out.println("0. Back");
        System.out.println("---------------------");
        System.out.print("Choose: ");

        String ch = sc.nextLine();

        switch (ch) {
            case "1":
                addUser();
                break;
            case "2":
                listUsers();
                break;
            default:
                break;
        }
    }

    // Adds a new user to the system
    private static void addUser() {

        System.out.println();

        System.out.print("User ID      : ");
        String id = sc.nextLine();

        System.out.print("Name         : ");
        String name = sc.nextLine();

        System.out.print("Email        : ");
        String email = sc.nextLine();

        System.out.print("Contact No   : ");
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

    // Displays all registered users in a tabular format
    private static void listUsers() {

        System.out.println();
        System.out.println("All Registered Users");
        System.out.println();

        System.out.println(
                "------------------------------------------------------------------------------------------------------------------");
        System.out.printf(
                "| %-6s | %-15s | %-10s | %-22s | %-12s | %-6s | %-8s | %-10s |\n",
                "ID", "Name", "Type", "Email", "Contact", "Limit", "Borrowed", "Fine Type");
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------");

        if (lib.getUsers().isEmpty()) {
            System.out.println(
                    "| No users available.                                                                                            |");
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------");
            return;
        }

        for (User u : lib.getUsers()) {

            String fineType = u.getFineStrategy().getClass().getSimpleName()
                    .replace("FineStrategy", "");

            System.out.printf(
                    "| %-6s | %-15s | %-10s | %-22s | %-12s | %-6d | %-8d | %-10s |\n",
                    u.getUserId(),
                    u.getName(),
                    u.getMembershipType(),
                    u.getEmail(),
                    u.getContactNo(),
                    u.getBorrowLimit(),
                    u.getBorrowedRecords().size(),
                    fineType);
        }

        System.out.println(
                "------------------------------------------------------------------------------------------------------------------");
    }

    // Displays and handles borrow, return, and reservation menu
    private static void borrowReturnMenu() {

        System.out.println();
        System.out.println("----- Borrow / Return / Reserve -----");
        System.out.println("1. Borrow book");
        System.out.println("2. Return book");
        System.out.println("3. Reserve book");
        System.out.println("4. Cancel reservation");
        System.out.println("0. Back");
        System.out.println("-----------------------------------");
        System.out.print("Choose: ");

        String ch = sc.nextLine();

        switch (ch) {
            case "1":
                borrowBook();
                break;
            case "2":
                returnBook();
                break;
            case "3":
                reserveBook();
                break;
            case "4":
                cancelReservation();
                break;
            default:
                break;
        }
    }

    // Borrows a book and displays borrowing summary
    private static void borrowBook() {

        System.out.println();

        System.out.print("User ID : ");
        String uid = sc.nextLine();

        System.out.print("Book ID : ");
        String bid = sc.nextLine();

        User u = lib.findUserById(uid);
        Book b = lib.findBookById(bid);

        if (u == null || b == null) {
            System.out.println("User or book not found.");
            return;
        }

        Command c = new BorrowCommand(b, u);
        lib.executeCommand(c);

        System.out.println();
        System.out.println("----- Borrow Summary -----");

        int allowedDays = 0;
        if (u instanceof Student)
            allowedDays = 14;
        else if (u instanceof Faculty)
            allowedDays = 30;
        else if (u instanceof Guest)
            allowedDays = 7;

        List<BorrowRecord> records = u.getBorrowedRecords();
        if (!records.isEmpty()) {
            BorrowRecord latest = records.get(records.size() - 1);

            System.out.println("Borrower        : " + u.getName());
            System.out.println("Book            : " + b.getTitle());
            System.out.println("Borrow Period   : " + allowedDays + " days");
            System.out.println("Return Due Date : " + DateUtil.format(latest.getDueDate()));
        }
        System.out.println("--------------------------");
    }

    // Returns a borrowed book
    private static void returnBook() {

        System.out.println();

        System.out.print("Book ID : ");
        String bid = sc.nextLine();

        Book b = lib.findBookById(bid);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }

        Command c = new ReturnCommand(b);
        lib.executeCommand(c);
    }

    // Reserves a borrowed book for a user
    private static void reserveBook() {

        System.out.println();

        System.out.print("User ID : ");
        String uid = sc.nextLine();

        System.out.print("Book ID : ");
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

    // Cancels an existing book reservation
    private static void cancelReservation() {

        System.out.println();

        System.out.print("User ID : ");
        String uid = sc.nextLine();

        System.out.print("Book ID : ");
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

    // Displays and handles the reports menu
    private static void reportsMenu() {

        System.out.println();
        System.out.println("----- Reports -----");
        System.out.println("1. Most borrowed book");
        System.out.println("2. Active borrowers");
        System.out.println("3. Overdue books");
        System.out.println("0. Back");
        System.out.println("-------------------");
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

    // Displays the most borrowed book report
    private static void reportMostBorrowed() {

        System.out.println();
        System.out.println("Most Borrowed Book");
        System.out.println("------------------");

        Book b = lib.mostBorrowedBook();

        if (b == null) {
            System.out.println("No books available.");
        } else {
            System.out.println("Title          : " + b.getTitle());
            System.out.println("Total Borrows  : " + b.getTotalBorrows());
        }
    }

    // Displays users who currently have borrowed books
    private static void reportActiveBorrowers() {

        System.out.println();
        System.out.println("Active Borrowers");
        System.out.println("----------------");

        List<User> list = lib.activeBorrowers();

        if (list.isEmpty()) {
            System.out.println("No active borrowers.");
            return;
        }

        for (User u : list) {
            System.out.println(
                    "- " + u.getName() +
                            " | Borrowed Books: " + u.getBorrowedRecords().size());
        }
    }

    // Displays overdue books with fine calculations
    private static void reportOverdueBooks() {

        System.out.println();
        System.out.println("Overdue Books");
        System.out.println("-------------");

        List<BorrowRecord> list = lib.overdueBooks();

        if (list.isEmpty()) {
            System.out.println("No overdue books.");
            return;
        }

        for (BorrowRecord r : list) {
            System.out.println("- Book      : " + r.getBook().getTitle());
            System.out.println("  Borrower  : " + r.getUser().getName());
            System.out.println("  Days Late : " + r.daysLate());
            System.out.println("  Fine      : " +
                    r.getUser().getFineStrategy().calculateFine(r.daysLate()));
            System.out.println();
        }
    }

    // Seeds initial sample data for testing the system
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
