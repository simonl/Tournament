package domain.algorithms;

import domain.logic.Strategy;

public class TitForTat extends Strategy
{
    byte nextAction = 0;
    public TitForTat()
    {
        
    }
    public byte Action(byte previousMove)
    {
        if(previousMove != 2)
        {
            nextAction = previousMove;
        }
        return nextAction;
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