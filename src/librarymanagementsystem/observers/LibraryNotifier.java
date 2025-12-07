package librarymanagementsystem.observers;

import java.util.ArrayList;
import java.util.List;

public class LibraryNotifier implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private static LibraryNotifier instance = new LibraryNotifier();

    private LibraryNotifier() {
    }

    public static LibraryNotifier getInstance() {
        return instance;
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }
}
