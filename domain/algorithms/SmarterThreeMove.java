package domain.algorithms;
import java.util.LinkedList;

import domain.logic.Strategy;
public class SmarterThreeMove extends Strategy
{
    Boolean myself;
    Boolean TFT;
    LinkedList<Byte> last3Move;
    int phase;
    private static String BRAIN  = "10111011";
    public SmarterThreeMove()
    {
        myself = false;
        TFT = false;
        last3Move = new LinkedList<Byte>();
        phase = 0;
    }
    public byte Action(byte previousMove)
    {
        switch (phase) {
            case 0: 
                ++phase;
                return 0;
            case 1:
                last3Move.add(previousMove);
                ++phase;
                return 1;
            case 2:
                last3Move.add(previousMove);
                ++phase;
                if(last3Move.get(0) == 0 && previousMove == 1)
                {
                    myself = true;
                    return 0;
                }
                return 1;
            case 3:
                last3Move.add(previousMove);
                ++phase;
                if(last3Move.get(0) == 0 && last3Move.get(1) == 0 && previousMove == 1)
                {
                    TFT = true;
                    return 0;
                }
                return UsingBrain(previousMove);
            default:
                ++phase;
                last3Move.remove();
                last3Move.add(previousMove);
                return UsingBrain(previousMove);
        }
    }
    public byte UsingBrain(byte previousMove)
    {
        if(myself)
        {
            myself = previousMove == 0;
            if(myself)
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
        else if(TFT)
        {
            TFT = previousMove == 0;
            if(TFT)
            {
                return 0;
            }
            else
            {
                if(phase == 5)
                {
                    TFT = true;
                    return 0;
                }
                return 1;
            }
        }
        else
        {
            int index = 0;
            for (int i = 0; i <= 2; ++i)
            {
                index += (int)(Math.pow(2, i)*last3Move.get(2-i));
            }
            return (byte)((int)BRAIN.charAt(index) - 48);
        }
    }
    public Strategy Duplicate()
    {
        return new SmarterThreeMove();
    }
    public String Name()
    {
        return "Smarterner";
    }
}