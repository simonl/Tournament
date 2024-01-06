package domain.algorithms;

import domain.logic.Strategy;

public class TitForTat extends Strategy
{
    public byte Action(byte pastAction, byte pastSensor)
    {
        if (pastSensor == INIT) {
            return COOPERATE;
        }
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