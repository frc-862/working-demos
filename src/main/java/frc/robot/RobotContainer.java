// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.CollectorConstants;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.DrivetrainConstants.DriveRequests;
import frc.robot.commands.ExtraSmartShoot;
import frc.robot.commands.SmartCollect;
import frc.robot.Constants.IndexerConstants;
import frc.robot.Constants.LEDConstants;
import frc.robot.Constants.LEDConstants.LED_STATES;
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
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
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
    private DoubleSubscriber driveMultiplier;
    private BooleanSubscriber useSingleController;

    @Override
    protected void initializeHardware() {
        collector = new Collector();
        indexer = new Indexer();
        shooter = new Shooter();
        leds = new LEDSubsystem(LED_STATES.values().length, LEDConstants.LED_LENGTH, LEDConstants.LED_PWM_PORT);
        drivetrain = DrivetrainConstants.TunerConstants.createDrivetrain();

        driver = new XboxController(ControllerConstants.DRIVER);
        copilot = new XboxController(ControllerConstants.COPILOT);
        storedCopilot = copilot;

        shooterPowerMultiplier = DemoShuffleboard.subscribeToDouble("Shooter Power Multiplier", 0.4);
        driveMultiplier = DemoShuffleboard.subscribeToDouble("Drive Multiplier", 0.4);
        useSingleController = DemoShuffleboard.subscribeToBoolean("Use Single Controller", false);
    }

    @Override
    protected void configureDefaultCommands() {
        
        // Set the default command for the drivetrain to be controlled by the driver's joysticks
        drivetrain.setDefaultCommand(drivetrain.applyRequest(DriveRequests.getDrive(
            () -> -driver.getLeftX() * driveMultiplier.get(), 
            () -> -driver.getLeftY() * driveMultiplier.get(), 
            () -> -driver.getRightX() * driveMultiplier.get())));

        // coast shooter when not being used
        shooter.setDefaultCommand(shooter.applyPower(() -> ShooterConstants.COAST_POWER));

        leds.setDefaultCommand(new RunCommand(() -> System.out.println(driveMultiplier.get()), leds));
    }

    @Override
    protected void configureButtonBindings() {
        // Collector, shooter, and Indexer manual controls
        // new Trigger(copilot::getXButton).onTrue(collector.applyPower(CollectorConstants.DEFAULT_POWER))
        //     .onFalse(collector.applyPower(0.0));
        // new Trigger(copilot::getBButton).onTrue(collector.applyPower(-CollectorConstants.DEFAULT_POWER))
        //     .onFalse(collector.applyPower(0.0));

        // new Trigger(copilot::getYButton).onTrue(indexer.applyPower(IndexerConstants.DEFAULT_POWER))
        //     .onFalse(indexer.applyPower(0.0));
        // new Trigger(copilot::getAButton).onTrue(indexer.applyPower(-IndexerConstants.DEFAULT_POWER))
        //     .onFalse(indexer.applyPower(0.0));

        new Trigger(copilot::getLeftBumperButton).onTrue(collector.applyPower(CollectorConstants.DEFAULT_POWER).alongWith(indexer.applyPower(IndexerConstants.DEFAULT_POWER)))
            .onFalse(collector.applyStop().alongWith(indexer.applyStop()))
            .whileTrue(leds.setState(LED_STATES.COLLECTING.ID()));
        new Trigger(copilot::getRightBumperButton).onTrue(collector.applyPower(-CollectorConstants.DEFAULT_POWER).alongWith(indexer.applyPower(-IndexerConstants.DEFAULT_POWER)))
            .onFalse(collector.applyStop().alongWith(indexer.applyStop()))
            .whileTrue(leds.setState(LED_STATES.COLLECTING.ID()));

        new Trigger(() -> Math.abs(getCopilotTriggerDifference()) > ControllerConstants.DEADBAND)
            .whileTrue(shooter.applyPower(() -> getCopilotTriggerDifference() * shooterPowerMultiplier.get()).deadlineFor(leds.setState(LED_STATES.SHOOTING.ID())));

        // collector, indexer, and shooter smart controls
        // new Trigger(() -> copilot.getLeftBumperButton()).whileTrue(new SmartCollect(indexer, collector));
        // new Trigger(() -> copilot.getRightBumperButton()).whileTrue(new ExtraSmartShoot(indexer, shooter, 
        //     () -> getCopilotTriggerDifference() * shooterPowerMultiplier.get()));

        // robot-centric driving while left trigger is held
        new Trigger(() -> (driver.getLeftTriggerAxis()) > ControllerConstants.DEADBAND).whileTrue(
            drivetrain.applyRequest(DriveRequests.getRobotCentric(() -> -driver.getLeftX() * driveMultiplier.get(), 
            () -> -driver.getLeftY() * driveMultiplier.get(), () -> -driver.getRightX() * driveMultiplier.get())));

        // brake
        new Trigger(driver::getXButton).whileTrue(drivetrain.applyRequest(DriveRequests.getBrake()));

        new Trigger(driver::getBButton).whileTrue(drivetrain.applyRequest(DriveRequests.getDrive(
            () -> -driver.getLeftX() * driveMultiplier.get() * DrivetrainConstants.SLOWMODE_MULTIPLIER, 
            () -> -driver.getLeftY() * driveMultiplier.get() * DrivetrainConstants.SLOWMODE_MULTIPLIER, 
            () -> -driver.getRightX() * driveMultiplier.get() * DrivetrainConstants.SLOWMODE_MULTIPLIER)));

        // reset field forward
        new Trigger(() -> driver.getStartButton() && driver.getBackButton()).onTrue(drivetrain.commandResetFieldForward());

        // switch to single controller mode when enabled
        new Trigger(useSingleController::get).onTrue(new InstantCommand(() -> copilot = driver))
            .onFalse(new InstantCommand(() -> copilot = storedCopilot));
    }

    @Override
    protected void configureLEDs() {
        leds.setDefaultBehavior(LEDBehaviorFactory.SwirlBehabior(LEDConstants.allLEDs, 10, 5, Color.BLUE, Color.ORANGE));

		leds.setBehavior(LED_STATES.COLLECTING.ID(), LEDBehaviorFactory.SolidColorBehavior(LEDConstants.allLEDs, Color.GREEN));
        leds.setBehavior(LED_STATES.SHOOTING.ID(), LEDBehaviorFactory.pulseColorBehavior(LEDConstants.allLEDs, 8, Color.PURPLE));
		leds.setBehavior(LED_STATES.AUTO.ID(), LEDBehaviorFactory.RainbowBehavior(LEDConstants.allLEDs, 3));
		leds.setBehavior(LED_STATES.TEST.ID(), LEDBehaviorFactory.TestStripBehavior(0, 
			() -> driver.getAButton(),
			() -> driver.getBButton(),
			() -> driver.getXButton(), 
			() -> driver.getYButton()));

        new Trigger(DriverStation::isTest).whileTrue(leds.setState(LED_STATES.TEST.ID()));

        new Trigger(() -> DriverStation.isAutonomous() && DriverStation.isEnabled()).whileTrue(leds.setState(LED_STATES.AUTO.ID()));
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
