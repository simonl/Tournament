package domain.algorithms;

import domain.logic.Strategy;

public class PaybackStrat extends Strategy {
    private int score = 0;
    private final boolean sneaky;

    public PaybackStrat(boolean sneaky) {
        this.sneaky = sneaky;
    }

    @Override
    public byte Action(byte pastAction, byte pastSensor) {
        if (pastSensor != pastAction) {
            switch (pastSensor) {
                case COOPERATE -> score++;
                case DEFECT -> score--;
            }
        }

        if (score < 0) {
            return DEFECT;
        }

        if (sneaky) {
            if (Math.random() > Math.pow(2, -score)) {
                return DEFECT;
            }
        }

        return COOPERATE;
    }

    @Override
    public Strategy Duplicate() {
        return new PaybackStrat(this.sneaky);
    }

    @Override
    public String Name() {
        return this.sneaky ? "Sneaky" : "Payback";
    }
}
