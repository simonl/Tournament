public final class TitForTat extends Strategy
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
    public void Reset()
    {
        nextAction = 0;
    }
    public String Name()
    {
        return "TitForThat";
    }
}