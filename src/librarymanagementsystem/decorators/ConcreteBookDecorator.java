package librarymanagementsystem.decorators;

import librarymanagementsystem.books.BookComponent;

public abstract class ConcreteBookDecorator implements BookComponent {
    protected BookComponent wrapped;

    public ConcreteBookDecorator(BookComponent b) {
        this.wrapped = b;
    }

    public String getTitle() {
        return wrapped.getTitle();
    }
}
