import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
public class Tournament
{
    public static void main(String[] args)
    {
        Random rand = new Random();
        int numberOfRounds = 195 + rand.nextInt(11);
        ArrayList<Strategy> competitor = new ArrayList<Strategy>();
        competitor.add(new Constant((byte)0, "Coop")); competitor.add(new RandomMove());
        competitor.add(new Constant((byte)1, "Selfish")); competitor.add(new TitForTat());
        int[][] resultTable = new int[competitor.size()][competitor.size() + 1];
        for (int i = 0; i < resultTable.length; ++i)
        {
            for (int j = 0; j <= i; ++j)
            {
                Point result = fight(numberOfRounds, (Strategy)competitor.get(i), (Strategy)competitor.get(j));
                if (i != j)
                {
                    resultTable[i][j] = result.x;
                    resultTable[j][i] = result.y;
                    resultTable[i][resultTable.length] += result.x;
                    resultTable[j][resultTable.length] += result.y;
                }
                else
                {
                    int average = (result.x + result.y)/2;
                    resultTable[i][i] = average;
                    resultTable[j][resultTable.length] += average;
                }
                
            }
        }
        for (int i = 0; i < resultTable.length; ++i)
        {
            System.out.println(competitor.get(i).Name() + " " + resultTable[i][resultTable.length]);
        }
    }
    public static Point fight(int numberOfRounds, Strategy a, Strategy b)
    {
        a.Reset(); b.Reset();
        int totalA = 0; int totalB = 0;
        byte previousActionA = 2; byte previousActionB = 2;
        byte actionA; byte actionB;
        for (int i = 0; i < numberOfRounds; ++i)
        {
            actionA = a.Action(previousActionB);
            actionB = b.Action(previousActionA);
            if(actionA == 0)
            {
                if(actionB == 0)
                {
                    totalA += 3;
                    totalB += 3;
                }
                else
                {
                    totalB += 5;
                }
            }
            else
            {
                if (actionB == 0)
                {
                    totalA += 5;
                }
                else
                {
                    totalA += 1;
                    totalB += 1;
                }
            }
            previousActionA = actionA;
            previousActionB = actionB;
        }
        return new Point(totalA,totalB);
    }
}