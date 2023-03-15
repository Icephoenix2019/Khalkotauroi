// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import claw.CLAWRobot;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.limelight.Limelight;
import frc.robot.limelight.Limelight.LEDMode;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.leds.*;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private final LEDStrip leds = new LEDStrip(new AddressableLED(0), 14, 0.1);
  
  private final SplitLEDController cubeLEDs = new SplitLEDController(
    LEDController.solidColor(Color.kPurple),
    LEDController.solidColor(Color.kViolet)
  );

  private final SplitLEDController XLEDS = new SplitLEDController(
    LEDController.solidColor(Color.kDarkRed),
    LEDController.solidColor(Color.kRed)
  );

  private final SplitLEDController coneLEDs = new SplitLEDController(
    LEDController.solidColor(Color.kYellow),
    LEDController.solidColor(Color.kGoldenrod)
  );


  private final LEDController cubeController = new PhasedLEDController(1.5,
    cubeLEDs,
    cubeLEDs.withOffset(1)
  );

  private final LEDController endcontroller = new SplitLEDController(
    LEDController.solidColor(Color.kBlack)
  );

  private final LEDController coneController = new PhasedLEDController(1.5,
    coneLEDs,
    coneLEDs.withOffset(1)
  );

  private final LEDController xModeController = new PhasedLEDController(1.5,
    XLEDS,
    XLEDS.withOffset(1)
  );

  @Override
  public void startCompetition () {
    CLAWRobot.startCompetition(this, super::startCompetition);
  }
  
  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    Limelight.update();
  }

  @Override
  public void disabledInit() {
    Limelight.setLEDMode(LEDMode.PIPELINE_DEFAULT);
  }

  @Override
  public void disabledPeriodic() {}

  //TODO: Make auton commands
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    if (m_robotContainer.isXon.getAsBoolean()) {
      xModeController.display(leds);
    } else if (m_robotContainer.isRBumper.getAsBoolean()) {
      coneController.display(leds);
    } else if (m_robotContainer.isLBumper.getAsBoolean()) {
      cubeController.display(leds);
    } else {
      endcontroller.display(leds);
    }
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
    Limelight.takeSnapshot();
    Limelight.setLEDMode(LEDMode.FORCE_BLINK);
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
