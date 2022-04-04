//Subject

package trafficlight.states;

//DONE implement a part of the pattern here

import trafficlight.observer.Subject;

public abstract class State extends Subject {

    public abstract State getNextState();

    public abstract String getColor();

    public String getSting(){
        return getColor();
    }

}