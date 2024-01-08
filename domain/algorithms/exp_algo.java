//Samir
package domain.algorithms;
import domain.logic.Strategy;
import java.util.Random;

public class exp_algo extends Strategy {
    //Quantile d'une expo
    private static double expo(double rate) {
        Random random = new Random();
        double exponentialValue = Math.log(1 - random.nextDouble()) / (-rate);

        // Convert the exponential value to a binary value
        return (exponentialValue < 1.0) ? 1 : 0;
    }

    @Override
    public byte Action(byte previousMove) {
        double rate = 1.5;
        return (byte) expo(rate);
    }

    @Override
    public String Name() {
        return "expo_algo";
    }

    @Override
    public Strategy Duplicate() {
        return new exp_algo();
    }
}