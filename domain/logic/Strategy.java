package domain.logic;

public abstract class Strategy
{
    // Previous move can receive 3 different imput
    // 0 the opponent cooperated on the previous move
    // 1 the opponent deflected on the previous move
    // 2 the game is starting (so their wasn't a previous move)
    public abstract byte Action(byte previousMove);
    // The fonction must return 0 if it wants to cooperate and 1 if it wants to deflect.
    
    //This fonction reset an strategy has if a new one was initiated.
    //For example, if your strategy create a list of all of the previous move, 
    //the list must be reset when this fonction is call.
    public abstract void Reset();

    //This fonction return the name of the strategy
    public abstract String Name();

}