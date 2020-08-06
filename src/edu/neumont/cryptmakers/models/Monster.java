package edu.neumont.cryptmakers.models;

public class Monster extends GameCharacter{
    boolean isAwake = false;

    public boolean isAwake() {
        return isAwake;
    }

    public void setAwake(boolean awake) {
        isAwake = awake;
    }

    public void wakeUp() {
        setAwake(true);
    }
}
