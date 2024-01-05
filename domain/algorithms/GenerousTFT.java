package domain.algorithms;

import domain.logic.Strategy;

public class GenerousTFT extends Strategy {

    private final double FORGIVENESS_PROBABILITY = 0.1; // 10% because it was the number said in the video

    @Override
    public byte Action(byte previousMove) {

        switch (previousMove) {
            case 2:
                return 0;

            case 1:
                //Check if we should forgive after the algo defect
                if (Math.random() < FORGIVENESS_PROBABILITY)
                    return 0;
                else
                    return previousMove;
        
            default:
                return previousMove;
        }
    }

    @Override
    public Strategy Duplicate() {
        return new GenerousTFT();
    }

    @Override
    public String Name() {
        return "GenerousTFT";
    }
    
}
