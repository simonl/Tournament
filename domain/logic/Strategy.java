package domain.logic;

public abstract class Strategy
{
    public static final byte COOPERATE = 0;
    public static final byte DEFECT = 1;
    public static final byte INIT = 2;

    // Previous move can receive 3 different imput
    // 0 the opponent cooperated on the previous move
    // 1 the opponent deflected on the previous move
    // 2 the game is starting (so their wasn't a previous move)
    public abstract byte Action(byte pastAction, byte pastSensor);
    // The fonction must return 0 if it wants to cooperate and 1 if it wants to deflect.
    
    //This fonction return a newly instantiate strategy
    //This reset the strategy before each battle
    public abstract Strategy Duplicate();

    //This fonction return the name of the strategy
    public abstract String Name();

}