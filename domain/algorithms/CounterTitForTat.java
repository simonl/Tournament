package domain.algorithms;
import java.util.Random;

import domain.logic.Strategy;

public class CounterTitForTat extends Strategy {
    Random rand = new Random();
    byte nextAction = 0;
    int numForSetDeflect = rand.nextInt(100);
    int actualRound = 0;
    boolean setDeflect = false;

    public CounterTitForTat()
    {
        
    }
    public byte Action(byte previousMove)
    {
        if(previousMove != 2)
        {
            if (actualRound == numForSetDeflect) {
                setDeflect = true;
            }

            if (setDeflect){
                nextAction = 1;
            } else {
                nextAction = previousMove;
            }
            actualRound++;
        }
        
        actualRound++;
        return nextAction;
    }
    public void Reset()
    {
        nextAction = 0;
    }
    public String Name()
    {
        return "CounterTitForTat";
    }
}
