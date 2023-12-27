package domain.algorithms;
import java.util.Random;

import domain.logic.Strategy;
public final class RandomMove extends Strategy
{
    Random rand;
    public RandomMove()
    {
        rand = new Random();
    }
    public byte Action(byte previousMove)
    {
        return (byte)rand.nextInt(2);
    }
    public void Reset()
    {

    }
    public String Name()
    {
        return "Random";
    }
}
