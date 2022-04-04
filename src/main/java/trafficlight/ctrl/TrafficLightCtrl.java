package trafficlight.ctrl;

import trafficlight.gui.TrafficLightGui;
import trafficlight.states.State;

public class TrafficLightCtrl {

    private State greenState;

    private State redState;

    private State yellowState;

    private State currentState;

    private State previousState;

    private final TrafficLightGui gui;

    private boolean doRun = true;

    public static TrafficLightCtrl singleton = null;

    public static TrafficLightCtrl getInstance(){
        if (singleton ==null){
            singleton = new TrafficLightCtrl();
        }
        return singleton;
    }
    private TrafficLightCtrl() {
        super();
        initStates();
        gui = new TrafficLightGui(this);
        gui.setVisible(true);

        //DONE useful to update the current state
        currentState.notifyObservers(currentState);
    }

    private void initStates() {
        greenState = new State() {
            @Override
            public State getNextState() {
                previousState = currentState;
                //DONE useful to update the current state and the old one
                yellowState.notifyObservers(currentState); //yellow state is after green
                notifyObservers(this);
                return yellowState;
            }
            @Override
            public String getColor() {
                return "green";
            }
        };

        redState = new State() {
            @Override
            public State getNextState() {
                previousState = currentState;
                //DONE useful to update the current state and the old one
                yellowState.notifyObservers(currentState); //yellow state is after red
                notifyObservers(this);
                return yellowState;
            }
            @Override
            public String getColor() {
                return "red";
            }
        };

        yellowState = new State() {
            @Override
            public State getNextState() {
                if (previousState.equals(greenState)) {
                    previousState = currentState;
                    //DONE useful to update the current state and the old one
                    redState.notifyObservers(currentState); //red comes after green --> yellow ==> red
                    notifyObservers(this);
                    return redState;
                }else {
                    previousState = currentState;
                    //DONE useful to update the current state and the old one
                    greenState.notifyObservers(currentState); //otherwise red --> yellow ==> green
                    notifyObservers(this);
                    return greenState;
                }
            }
            @Override
            public String getColor() {
                return "yellow";
            }
        };
        currentState = greenState;
        previousState = yellowState;
    }

    public State getGreenState() {
        return greenState;
    }

    public State getRedState() {
        return redState;
    }

    public State getYellowState() {
        return yellowState;
    }

    public void run()  {
        int intervall = 1500;
        while (doRun) {
            try {
                Thread.sleep(intervall);
                nextState();
            } catch (InterruptedException e) {
                gui.showErrorMessage(e);
            }
        }
        System.out.println("Stopped");
        System.exit(0);
    }

    public void nextState() {
        currentState = currentState.getNextState();
    }

    public void stop() {
        doRun = false;
    }

}