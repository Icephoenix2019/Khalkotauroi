package frc.robot.leds;

import edu.wpi.first.wpilibj.util.Color;

public class FadedLEDController extends LEDController {
    
    private static final int FADE_CONSTANT = 25;
    private final LEDController controller;
    
    public FadedLEDController (LEDController controller) {
        this.controller = controller;
    }
    
    @Override
    public Color getLEDColor (int index, int length) {
        double p = getProportion(index, length);
        double scalar = Math.pow(FADE_CONSTANT, p) / FADE_CONSTANT;
        
        Color color = controller.getLEDColor(index, length);
        
        return new Color(color.red * scalar, color.green * scalar, color.blue * scalar);
    }
    
    private double getProportion (int index, int length) {
        if (length <= 1) return 0;
        return index / (double)(length-1);
    }
    
}
