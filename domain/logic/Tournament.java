package domain.logic;
import java.util.*;

import domain.algorithms.*;
import domain.algorithms.PaybackStrat;

public class Tournament
{
    private static final int[][] SCORES = new int[][] {
            { 1, -1 },
            { 2,  0 }
    };

    public static void main(String[] args)
    {
        Random rand = new Random();
        int numberOfRounds = 195 + rand.nextInt(11);
        double mistakeProbability = 0.05;
        int maxPad = 11;
        int samples = 100;

        ArrayList<Strategy> competitor = new ArrayList<Strategy>();
        competitor.add(new Constant(Strategy.COOPERATE, "Coop"));
        competitor.add(new Constant(Strategy.DEFECT, "Selfish"));
        competitor.add(new RandomMove(rand));
        competitor.add(new TitForTat());
        competitor.add(new GenerousTFT());
        //competitor.add(new And());
        //competitor.add(new exp_algo());
        competitor.add(new PaybackStrat(false));
        competitor.add(new PaybackStrat(true));
        //competitor.add(new gauss_algo());
        competitor.add(new GoodToBad());
        //competitor.add(new ImpLR());
        //competitor.add(new Jerk());
        //competitor.add(new NastyThreeMove());
        //competitor.add(new NiceThreeMove());
        //competitor.add(new Or());
        competitor.add(new SmarterThreeMove());
        //competitor.add(new sSi());
        //competitor.add(new XOR());

        // Initializing result map
        double[][] resultTable = new double[competitor.size()][competitor.size()];
        double[][] varianceTable = new double[competitor.size()][competitor.size()];

        for (int i = 0; i < competitor.size(); ++i)
        {
            for (int j = i; j < competitor.size(); ++j)
            {
                for (int n = 0; n < samples; ++n)
                {
                    Map<String, Double> result = fight(rand, numberOfRounds, mistakeProbability, (Strategy)competitor.get(i), (Strategy)competitor.get(j));
                    String algo1Name = competitor.get(i).Name();
                    String algo2Name = competitor.get(j).Name();

                    resultTable[i][j] += result.get(algo1Name);
                    resultTable[j][i] += result.get(algo2Name);

                    varianceTable[i][j] += Math.pow(result.get(algo1Name), 2);
                    varianceTable[j][i] += Math.pow(result.get(algo2Name), 2);
                }

                if (i == j) {
                    resultTable[i][j] /= 2.0 * samples;
                    varianceTable[i][j] /= 2.0 * samples;
                    varianceTable[i][j] -= Math.pow(resultTable[i][j], 2);
                    varianceTable[i][j] = Math.sqrt(varianceTable[i][j]);
                } else {
                    resultTable[i][j] /= samples;
                    varianceTable[i][j] /= samples;
                    varianceTable[i][j] -= Math.pow(resultTable[i][j], 2);
                    varianceTable[i][j] = Math.sqrt(varianceTable[i][j]);

                    resultTable[j][i] /= samples;
                    varianceTable[j][i] /= samples;
                    varianceTable[j][i] -= Math.pow(resultTable[j][i], 2);
                    varianceTable[j][i] = Math.sqrt(varianceTable[j][i]);
                }
            }
        }

        PrintTable(maxPad, competitor, resultTable);
        PrintTable(maxPad, competitor, varianceTable);

        List<String> names = new ArrayList<>(competitor.stream().map(c -> c.Name()).toList());
        List<Double> points = new ArrayList<>(Arrays.stream(resultTable).map(xs -> Arrays.stream(xs).sum()).toList());

        List<String> orderedNames = new ArrayList<>();
        List<Double> orderedPoints = new ArrayList<>();

        while (!points.isEmpty()) {
            double max = Collections.max(points);
            int maxIndex = points.indexOf(max);
            orderedNames.add(names.remove(maxIndex));
            orderedPoints.add(points.remove(maxIndex));
        }

        Map<Integer, String> positions = new HashMap<>();
        positions.put(0, "1st");
        positions.put(1, "2nd");
        positions.put(2, "3rd");
        positions.put(3, "4th");
        positions.put(4, "5th");

        for (int i = 0; i < orderedNames.size(); i++) {
            if (i == 0)
                System.out.println("1st ---> " + orderedNames.get(i) + ": " + orderedPoints.get(i));
            else if (i > 0 && i < 5)
                if (orderedPoints.get(i).equals(orderedPoints.get(i - 1)))
                    System.out.println(positions.get(i-1) + " ---> " + orderedNames.get(i) + ": " + orderedPoints.get(i));
                else
                    System.out.println(positions.get(i) + " ---> " + orderedNames.get(i) + ": " + orderedPoints.get(i));
            else
                System.out.println(orderedNames.get(i) + ": " + orderedPoints.get(i));
        }
    }

    private static void PrintTable(int maxPad, ArrayList<Strategy> competitor, double[][] resultTable) {
        System.out.print(" ".repeat(maxPad + 2));
        for (int i = 0; i < competitor.size(); ++i) {
            String name = competitor.get(i).Name();
            System.out.print(Padding(maxPad, name) + " |");
        }
        System.out.println();

        for (int i = 0; i < competitor.size(); ++i) {
            String name = competitor.get(i).Name();
            System.out.print(Padding(maxPad, name) + ": ");

            for (int j = 0; j < competitor.size(); ++j) {
                double percent = (int)(1000* resultTable[i][j])/10.0;
                String result = ((Double) percent).toString();

                System.out.print(Padding(maxPad-1, result) + "% |");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static Map<String, Double> fight(Random rand, int numberOfRounds, double mistakeProbability, Strategy a, Strategy b)
    {
        Strategy playerA = a.Duplicate();
        Strategy playerB = b.Duplicate();
        int totalA = 0; int totalB = 0;
        byte previousActionA = Strategy.INIT; byte previousActionB = Strategy.INIT;
        byte actionA; byte actionB;
        for (int i = 0; i < numberOfRounds; ++i)
        {
            actionA = playerA.Action(previousActionA, previousActionB);
            actionB = playerB.Action(previousActionB, previousActionA);

            if (rand.nextFloat() < mistakeProbability) {
                actionA = (actionA == Strategy.COOPERATE ? Strategy.DEFECT : Strategy.COOPERATE);
            }

            if (rand.nextFloat() < mistakeProbability) {
                actionB = (actionB == Strategy.COOPERATE ? Strategy.DEFECT : Strategy.COOPERATE);
            }

            totalA += SCORES[actionA][actionB];
            totalB += SCORES[actionB][actionA];

            previousActionA = actionA;
            previousActionB = actionB;
        }

        Map<String, Double> result = new HashMap<>();
        result.put(playerA.Name(), (double)totalA / numberOfRounds);
        result.put(playerB.Name(), (double)totalB / numberOfRounds);
        return result;
    }

    private static String Padding(int length, String name) {
        if (length < name.length()) {
            return name.substring(0, length);
        }

        return " ".repeat(length - name.length()) + name;
    }
}
