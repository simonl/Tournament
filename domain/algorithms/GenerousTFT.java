package domain.algorithms;

import domain.logic.Strategy;

public class GenerousTFT extends Strategy {

    private final double FORGIVENESS_PROBABILITY = 0.1; // 10% because it was the number said in the video

    @Override
    public byte firstAction() {
        return COOPERATE;
    }

    @Override
    public byte Action(byte pastAction, byte pastSensor) {
        switch (pastSensor) {
            case 1:
                //Check if we should forgive after the algo defect
                if (Math.random() < FORGIVENESS_PROBABILITY)
                    return COOPERATE;
                else
                    return pastSensor;
        
            default:
                return pastSensor;
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
