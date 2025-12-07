package librarymanagementsystem.books;

public class BookBuilder {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private String edition;

    public BookBuilder(String bookId, String title) {
        this.bookId = bookId;
        this.title = title;
    }

    public BookBuilder author(String a) {
        this.author = a;
        return this;
    }

    public BookBuilder category(String c) {
        this.category = c;
        return this;
    }

    public BookBuilder isbn(String i) {
        this.isbn = i;
        return this;
    }

    public BookBuilder edition(String e) {
        this.edition = e;
        return this;
    }

    public Book build() {
        Book b = new Book(bookId, title, author, category, isbn);
        if (edition != null)
            b.setEdition(edition);
        return b;
    }
}
