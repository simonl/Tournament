package domain.algorithms;

import domain.logic.Strategy;

public class TitForTat extends Strategy
{
    byte nextAction = 0;

    @Override
    public byte firstAction() {
        return COOPERATE;
    }

    public byte Action(byte pastAction, byte pastSensor)
    {
        return pastSensor;
    }

    public Strategy Duplicate()
    {
        return new TitForTat();
    }
    public String Name()
    {
        return "TitForTat";
    }
}