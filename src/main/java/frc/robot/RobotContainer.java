// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.CollectorConstants;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.DrivetrainConstants.DriveRequests;
import frc.robot.Constants.IndexerConstants;
import frc.robot.Constants.LEDConstants;
import frc.robot.Constants.LEDConstants.LED_STATES;
import frc.robot.commands.ExtraSmartShoot;
import frc.robot.commands.SmartCollect;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;
import frc.util.LightningContainer;
import frc.util.leds.Color;
import frc.util.leds.LEDBehaviorFactory;
import frc.util.leds.LEDSubsystem;
import frc.util.shuffleboard.DemoShuffleboard;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer extends LightningContainer {

    private Collector collector;
    private Indexer indexer;
    private Shooter shooter;
    private Swerve  drivetrain;
    private LEDSubsystem leds;

    private XboxController driver;
    private XboxController copilot;

    private XboxController storedCopilot;

    private DoubleSubscriber shooterPowerMultiplier;
    private DoubleSubscriber shooterRPS;
    private DoubleSubscriber driveMultiplier;
    private BooleanSubscriber useSingleController;

    @Override
    protected void initializeHardware() {
        drivetrain = DrivetrainConstants.TunerConstants.createDrivetrain();
        
        collector = new Collector();
        indexer = new Indexer();
        shooter = new Shooter();

        leds = new LEDSubsystem(LED_STATES.values().length, LEDConstants.LED_LENGTH, LEDConstants.LED_PWM_PORT);
        
        driver = new XboxController(ControllerConstants.DRIVER);
        copilot = new XboxController(ControllerConstants.COPILOT);
        storedCopilot = copilot;

        shooterPowerMultiplier = DemoShuffleboard.subscribeToDouble("Shooter Power Multiplier", 0.4);
        driveMultiplier = DemoShuffleboard.subscribeToDouble("Drive Multiplier", 0.4);
        useSingleController = DemoShuffleboard.subscribeToBoolean("Use Single Controller", false);
        shooterRPS = DemoShuffleboard.subscribeToDouble("Shooter RPS", 40);
    }

    @Override
    protected void configureDefaultCommands() {
        
        // default drive
        drivetrain.setDefaultCommand(drivetrain.applyRequest(DriveRequests.getDrive(
            () -> -driver.getLeftX() * driveMultiplier.get(), 
            () -> -driver.getLeftY() * driveMultiplier.get(), 
            () -> -driver.getRightX() * driveMultiplier.get())));

        // coast shooter in
        shooter.setDefaultCommand(shooter.applyPower(ShooterConstants.COAST_POWER));

    }

    @Override
    protected void configureButtonBindings() {
        // demo collect & index
        new Trigger(copilot::getLeftBumperButton).onTrue(collector.applyPower(CollectorConstants.DEFAULT_POWER).alongWith(indexer.applyPower(IndexerConstants.DEFAULT_POWER)))
            .onFalse(collector.applyStop().alongWith(indexer.applyStop()))
            .whileTrue(leds.enableState(LED_STATES.COLLECTING.ID()));
        new Trigger(copilot::getRightBumperButton).onTrue(collector.applyPower(-CollectorConstants.DEFAULT_POWER).alongWith(indexer.applyPower(-IndexerConstants.DEFAULT_POWER)))
            .onFalse(collector.applyStop().alongWith(indexer.applyStop()))
            .whileTrue(leds.enableState(LED_STATES.COLLECTING.ID()));

        // demo shoot
        new Trigger(() -> Math.abs(getCopilotTriggerDifference()) > ControllerConstants.DEADBAND)
            .whileTrue(shooter.applyPower(() -> getCopilotTriggerDifference() * shooterPowerMultiplier.get())
            .deadlineFor(leds.enableState(LED_STATES.SHOOTING.ID())));
        
        // Smart Collect
        new Trigger(copilot::getAButton)
            .whileTrue(new SmartCollect(indexer, collector)
            .onSuccess(leds.enableStateWithTimeout(LED_STATES.COLLECTED.ID(), 5))
            .deadlineFor(leds.enableState(LED_STATES.COLLECTING.ID())));
        
        // Extra Smart Shoot
        new Trigger(copilot::getXButton)
            .whileTrue(new ExtraSmartShoot(indexer, shooter, RotationsPerSecond.of(shooterRPS.get()))
            .onSuccess(leds.enableStateWithTimeout(LED_STATES.SHOT.ID(), 5))
            .deadlineFor(leds.enableState(LED_STATES.COLLECTING.ID())));

        // robot-centric
        new Trigger(() -> (driver.getLeftTriggerAxis()) > ControllerConstants.DEADBAND).whileTrue(
            drivetrain.applyRequest(DriveRequests.getRobotCentric(
                () -> -driver.getLeftX() * driveMultiplier.get(), 
                () -> -driver.getLeftY() * driveMultiplier.get(), 
                () -> -driver.getRightX() * driveMultiplier.get())));

        // slowmode
        new Trigger(() -> (driver.getLeftTriggerAxis()) > ControllerConstants.DEADBAND).whileTrue(drivetrain.applyRequest(DriveRequests.getDrive(
            () -> -driver.getLeftX() * driveMultiplier.get() * DrivetrainConstants.SLOWMODE_MULTIPLIER, 
            () -> -driver.getLeftY() * driveMultiplier.get() * DrivetrainConstants.SLOWMODE_MULTIPLIER, 
            () -> -driver.getRightX() * driveMultiplier.get() * DrivetrainConstants.SLOWMODE_MULTIPLIER)));

        // brake
        new Trigger(driver::getXButton).whileTrue(drivetrain.applyRequest(DriveRequests.getBrake()));

        // reset field forward
        new Trigger(() -> driver.getStartButton() && driver.getBackButton()).onTrue(drivetrain.commandResetFieldForward());

        // switch to single controller mode when enabled
        new Trigger(useSingleController::get)
            .onTrue(new InstantCommand(() -> copilot = driver))
            .onFalse(new InstantCommand(() -> copilot = storedCopilot))
            .whileTrue(leds.enableState(LED_STATES.SINGLE_CONTROLLER.ID()));
    }

    @Override
    protected void configureLEDs() {
        leds.setDefaultBehavior(LEDBehaviorFactory.SwirlBehabior(LEDConstants.allLEDs, 10, 5, Color.BLUE, Color.ORANGE));

        leds.setBehavior(LED_STATES.SINGLE_CONTROLLER.ID(), LEDBehaviorFactory.SolidColorBehavior(LEDConstants.strip2, Color.YELLOW).and(LEDBehaviorFactory.SolidColorBehavior(LEDConstants.strip4, Color.YELLOW)));

        leds.setBehavior(LED_STATES.COLLECTED.ID(), LEDBehaviorFactory.BlinkColorBehavior(LEDConstants.strip1, 4, Color.GREEN));
        leds.setBehavior(LED_STATES.SHOT.ID(), LEDBehaviorFactory.BlinkColorBehavior(LEDConstants.strip3, 4, Color.GREEN));

		leds.setBehavior(LED_STATES.COLLECTING.ID(), LEDBehaviorFactory.pulseColorBehavior(LEDConstants.allLEDs, 8, Color.ORANGE));
        leds.setBehavior(LED_STATES.SHOOTING.ID(), LEDBehaviorFactory.pulseColorBehavior(LEDConstants.allLEDs, 8, Color.PURPLE));

		leds.setBehavior(LED_STATES.AUTO.ID(), LEDBehaviorFactory.RainbowBehavior(LEDConstants.allLEDs, 3));
		leds.setBehavior(LED_STATES.TEST.ID(), LEDBehaviorFactory.TestStripBehavior(0, 
			() -> driver.getAButton(),
			() -> driver.getBButton(),
			() -> driver.getXButton(), 
			() -> driver.getYButton()));

        new Trigger(DriverStation::isTest).whileTrue(leds.enableState(LED_STATES.TEST.ID()));

        new Trigger(() -> DriverStation.isAutonomous() && DriverStation.isEnabled()).whileTrue(leds.enableState(LED_STATES.AUTO.ID()));
    }

    @Override
    protected void initializeNamedCommands() {}

    @Override
    protected Command getAutonomousCommand() {
        return new InstantCommand();
    }

    private double getCopilotTriggerDifference() {
        // left trigger is disabled when in single controller mode
        return (copilot.getRightTriggerAxis() - (useSingleController.get() ? 0 : copilot.getLeftTriggerAxis()));
    }
}
