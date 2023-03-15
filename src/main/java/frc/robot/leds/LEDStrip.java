package frc.robot.leds;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

// TODO: Make the LEDStrip abstract with a method on the AddressableLEDStrip allowing
// for flushing the buffer. Then, LEDController should act on a virtual LEDStrip rather
// than needing to make individual calls for every getLEDColor
public class LEDStrip {
    
    private final AddressableLEDBuffer buffer;
    private final AddressableLED led;
    private final double brightness;
    
    public LEDStrip (AddressableLED led, int length, double brightness) {
        this.led = led;
        this.buffer = new AddressableLEDBuffer(length);
        this.brightness = brightness;
        led.setLength(length);
        led.start();
    }
    
    public LEDStrip (AddressableLED led, int length) {
        this(led, length, 1);
    }
    
    public int getLength () {
        return buffer.getLength();
    }
    
    public void setColor (int index, Color color) {
        Color adjColor = new Color(color.red * brightness, color.green * brightness, color.blue * brightness);
        buffer.setLED(index, adjColor);
    }

    public void stopColor () {
        for (int i = 0; i < buffer.getLength(); i++) {
            buffer.setLED(i, Color.kBlack);
        }
    }
    
    public void flushBuffer () {
        led.setData(buffer);
    }
    
}
