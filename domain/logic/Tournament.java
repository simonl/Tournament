package domain.logic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import domain.algorithms.Constant;
import domain.algorithms.CounterTitForTat;
import domain.algorithms.RandomMove;
import domain.algorithms.TitForTat;

import java.awt.*;
import java.util.List;
public class Tournament
{
    public static void main(String[] args)
    {
        Random rand = new Random();
        int numberOfRounds = 195 + rand.nextInt(11);
        ArrayList<Strategy> competitor = new ArrayList<Strategy>();
        competitor.add(new Constant((byte)0, "Coop")); competitor.add(new RandomMove());
        competitor.add(new Constant((byte)1, "Selfish")); competitor.add(new TitForTat());
        competitor.add(new CounterTitForTat());

        // Initializing result map
        Map<String, Integer> resultTable = new TreeMap();
        for (Strategy strategy : competitor) {
            resultTable.put(strategy.Name(), 0);
        }

        for (int i = 0; i < competitor.size(); ++i)
        {
            for (int j = 0; j <= i; ++j)
            {
                Map<String, Integer> result = fight(numberOfRounds, (Strategy)competitor.get(i), (Strategy)competitor.get(j));
                String algo1Name = competitor.get(i).Name();
                String algo2Name = competitor.get(j).Name();

                int actualValue1 = resultTable.get(algo1Name);
                int newValue1 = actualValue1 + result.get(algo1Name);
                resultTable.put(algo1Name, newValue1);

                int actualValue2 = resultTable.get(algo2Name);
                int newValue2 = actualValue2 + result.get(algo2Name);
                resultTable.put(algo2Name, newValue2);
                
            }
        }

        // Sorting the results for display
        Map<Integer, String> result = new TreeMap();
        for (int i = 0; i < competitor.size(); i++) {
            int competitorPoint = resultTable.get(competitor.get(i).Name());
            result.put(competitorPoint, competitor.get(i).Name() + ": " + competitorPoint);
        }

        List<String> orderedResult = new ArrayList<>(result.values());

        for (int i = 0; i < orderedResult.size(); i++) {
            if (i == 0) 
                System.out.println("1st --> " + " " + orderedResult.get(orderedResult.size() - (i + 1)));
            else if (i == 1)
                System.out.println("2nd --> " + " " + orderedResult.get(orderedResult.size() - (i + 1)));
            else if (i == 2)
                System.out.println("3rd --> " + " " + orderedResult.get(orderedResult.size() - (i + 1)));
            else
                System.out.println(orderedResult.get(orderedResult.size() - (i + 1)));
        }
    }
    
    public static Map<String, Integer> fight(int numberOfRounds, Strategy a, Strategy b)
    {
        a.Reset(); b.Reset();
        Map<String, Integer> result = new HashMap<>();
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
        result.put(a.Name(), totalA);
        result.put(b.Name(), totalB);
        return result;
    }
}