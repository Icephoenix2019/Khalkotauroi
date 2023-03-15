package frc.robot.leds;

import edu.wpi.first.wpilibj.util.Color;

public class PhasedLEDController extends TimedLEDController {
    
    private final LEDController[] controllers;
    
    public PhasedLEDController (double intervalSecs, LEDController... controllers) {
        super(intervalSecs);
        this.controllers = controllers;
    }
    
    @Override
    public Color getLEDColor (int index, int length, double time) {
        int controllerIndex = (int)(time * controllers.length) % controllers.length;
        return controllers[controllerIndex].getLEDColor(index, length);
    }
    
}
