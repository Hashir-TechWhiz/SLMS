package librarymanagementsystem.decorators;

import librarymanagementsystem.books.BookComponent;

public abstract class ConcreteBookDecorator implements BookComponent {
    protected BookComponent decoratedBook;

    public ConcreteBookDecorator(BookComponent b) {
        this.decoratedBook = b;
    }

    public String getTitle() {
        return decoratedBook.getTitle();
    }
}