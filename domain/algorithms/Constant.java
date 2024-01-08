package domain.algorithms;
//This class always return the same thing.
//It is a abstraction of Cooperation and Selfish

import domain.logic.Strategy;

public final class Constant extends Strategy
{
    byte action;
    String name;
    public Constant(byte Action, String Name)
    {
        action = Action;
        name = Name;
    }

    public byte Action(byte pastAction, byte pastSensor)
    {
        return action;
    }
    public Strategy Duplicate()
    {
        return new Constant(action, name);
    }
    public String Name()
    {
        return name;
    }
}