package trafficlight.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    //create ArrayList for Observers
    private List<Observer> observerList = new ArrayList<>();

    //add and remove observers from the list
    public void addObserver(Observer o){
        this.observerList.add(o);
    }
    public void removeObserver(Observer o){
        this.observerList.remove(o);
    }

    public void notifyObservers(Subject s){
        for (Observer o : this.observerList){
            o.update(s);
        }
    }
}
