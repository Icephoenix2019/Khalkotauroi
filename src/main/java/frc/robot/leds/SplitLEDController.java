package frc.robot.leds;

import edu.wpi.first.wpilibj.util.Color;

public class SplitLEDController extends LEDController {
    
    private final LEDController[] controllers;
    
    public SplitLEDController (LEDController... controllers) {
        this.controllers = controllers;
    }
    
    public SplitLEDController withOffset (int offset) {
        LEDController[] newControllers = new LEDController[controllers.length];
        
        for (int i = 0; i < controllers.length; i ++) {
            newControllers[i] = controllers[(i + offset) % controllers.length];
        }
        
        return new SplitLEDController(newControllers);
    }
    
    public Color getLEDColor (int index, int stripLength) {
        
        // Get the index of the controller to use for this LED
        int controllerIndex = index % controllers.length;
        
        // Get the index the controller should see (the index divided by number of controllers, rounded down)
        int receivedIndex = index / controllers.length;
        
        // Get the strip length the controller should see
        int receivedLength = getLengthForController(controllerIndex, stripLength);
        
        return controllers[controllerIndex].getLEDColor(receivedIndex, receivedLength);
        
    }
    
    private int getLengthForController (int controllerIndex, int stripLength) {
        
        // baseLength is the number of LEDs that each controller will be able to set for the given
        // stripLength at a minimum
        int baseLength = stripLength / controllers.length;
        
        // additionalLEDs is the number of LEDs left over, which cannot be divided evenly between the controllers
        int additionalLEDs = stripLength - baseLength * controllers.length;
        
        // The first N additional LEDs go to the first N controllers:
        
        // If there are enough additional LEDs so that this controller can receive one more, then we can return
        // baseLength + 1, otherwise we must return baseLength
        if (additionalLEDs > controllerIndex)
            return baseLength + 1;
        else
            return baseLength;
        
    }
    
}
