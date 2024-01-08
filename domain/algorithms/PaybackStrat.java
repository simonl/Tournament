package domain.algorithms;

import domain.logic.Strategy;

public class PaybackStrat extends Strategy {
    int score = 0;

    @Override
    public byte Action(byte pastAction, byte pastSensor) {
        if (pastSensor != pastAction) {
            switch (pastSensor) {
                case COOPERATE -> score++;
                case DEFECT -> score--;
            }
        }

        return score < 0 ? DEFECT : COOPERATE;
    }

    @Override
    public Strategy Duplicate() {
        return new PaybackStrat();
    }

    @Override
    public String Name() {
        return "Payback";
    }
}
