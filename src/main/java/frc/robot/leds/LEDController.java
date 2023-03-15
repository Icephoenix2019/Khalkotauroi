package frc.robot.leds;

import edu.wpi.first.wpilibj.util.Color;

public abstract class LEDController {
    
    public static LEDController solidColor (Color color) {
        return LEDController.fromFunction((a, b) -> color);
    }
    
    public static LEDController fromFunction (LEDFunction function) {
        return new LEDController() {
            @Override
            public Color getLEDColor (int index, int length) {
                return function.getLEDColor(index, length);
            }
        };
    }
    
    public final LEDController flipped () {
        LEDController thisController = this;
        return LEDController.fromFunction((index, length) -> {
            return thisController.getLEDColor(length - index - 1, length);
        });
    }
    
    public final void display (LEDStrip led) {
        final int length = led.getLength();
        for (int i = 0; i < length; i ++) {
            led.setColor(i, getLEDColor(i, length));
        }
        
        led.flushBuffer();
    }
    
    public abstract Color getLEDColor (int index, int length);
    
    public static interface LEDFunction {
        public Color getLEDColor (int index, int length);
    }
    
}
