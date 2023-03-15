package frc.robot.leds;

import edu.wpi.first.wpilibj.util.Color;

public class BlinkingLEDController extends TimedLEDController {
    
    private final LEDController controller;
    private final double flashDurationSecs;
    
    public BlinkingLEDController (double flashDurationSecs, double hideDurationSecs, LEDController controller) {
        super(flashDurationSecs + hideDurationSecs);
        this.flashDurationSecs = flashDurationSecs;
        this.controller = controller;
    }
    
    @Override
    public Color getLEDColor (int index, int length, double time) {
        if (time < flashDurationSecs / getIntervalSecs())
            return controller.getLEDColor(index, length);
        else return Color.kBlack;
    }
    
}
