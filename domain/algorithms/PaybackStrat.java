package domain.algorithms;

import domain.logic.Strategy;

public class PaybackStrat extends Strategy {
    private static final int COOPERATE = 0;
    private static final int DEFECT = 1;
    private static final int INIT = 2;

    int score = 0;
    byte pastAction = COOPERATE;

    @Override
    public byte Action(byte previousMove) {
        if (previousMove != pastAction) {
            switch (previousMove) {
                case INIT -> score = 0;
                case COOPERATE -> score++;
                case DEFECT -> score--;
            }
        }

        if (score < 0) {
            return (pastAction = DEFECT);
        }

        return (pastAction = COOPERATE);
    }

    @Override
    public Strategy Duplicate() {
        return new PaybackStrat();
    }

    @Override
    public String Name() {
        return "Simon-Payback";
    }
}
