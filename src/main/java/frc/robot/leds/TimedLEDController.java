package frc.robot.leds;

import edu.wpi.first.wpilibj.util.Color;

public abstract class TimedLEDController extends LEDController {
    
    private final double intervalSecs;
    
    public TimedLEDController (double intervalSecs) {
        this.intervalSecs = intervalSecs;
    }
    
    public final double getIntervalSecs () {
        return intervalSecs;
    }
    
    public TimedLEDController animationReversed () {
        return new TimedLEDController (intervalSecs) {
            TimedLEDController thisController = this;
            
            @Override
            public Color getLEDColor (int index, int length, double time) {
                return thisController.getLEDColor(index, length, 1 - time);
            }
        };
    }
    
    @Override
    public final Color getLEDColor (int index, int length) {
        long intervalMillis = (long)(intervalSecs * 1000);
        long timeInInterval = System.currentTimeMillis() % intervalMillis;
        double time = timeInInterval / (double)intervalMillis;
        return getLEDColor(index, length, time);
    }
    
    public abstract Color getLEDColor (int index, int length, double time);
    
}
