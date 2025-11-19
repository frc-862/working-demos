// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.CollectorConstants;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.DrivetrainConstants.DriveRequests;
import frc.robot.commands.ExtraSmartShoot;
import frc.robot.commands.SmartCollect;
import frc.robot.Constants.IndexerConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;
import frc.util.LightningContainer;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer extends LightningContainer {

    private Collector collector;
    private Indexer indexer;
    private Shooter shooter;
    private Swerve  drivetrain;

    private XboxController driver;
    private XboxController copilot;

    private XboxController storedCopilot;

    private final DoubleSubscriber shooterPowerMultiplier = 
        NetworkTableInstance.getDefault().getTable("Demo").getDoubleTopic("Shooter Power Multiplier").subscribe(0.4);
    private final DoubleSubscriber driveMultiplier = 
        NetworkTableInstance.getDefault().getTable("Demo").getDoubleTopic("Drive Multiplier").subscribe(0.4);
    private final BooleanSubscriber useSingleController = 
        NetworkTableInstance.getDefault().getTable("Demo").getBooleanTopic("Use Single Controller").subscribe(false);

    @Override
    protected void initializeSubsystems() {
        collector = new Collector();
        indexer = new Indexer();
        shooter = new Shooter();

        driver = new XboxController(ControllerConstants.DRIVER);
        copilot = new XboxController(ControllerConstants.COPILOT);
        storedCopilot = copilot;
    }

    @Override
    protected void configureDefaultCommands() {
        
        // Set the default command for the drivetrain to be controlled by the driver's joysticks
        drivetrain.setDefaultCommand(drivetrain.applyRequest(DriveRequests.getDrive(
            () -> -driver.getLeftX() * driveMultiplier.get(), () -> -driver.getLeftY() * driveMultiplier.get(), 
            () -> -driver.getRightX() * driveMultiplier.get())));

        // coast shooter when not being used
        shooter.setDefaultCommand(shooter.applyPower(() -> ShooterConstants.COAST_POWER));
    }

    @Override
    protected void configureButtonBindings() {
        // Collector, shooter, and Indexer manual controls
        new Trigger(copilot::getXButton).onTrue(collector.applyPower(-CollectorConstants.DEFAULT_POWER))
            .onFalse(collector.applyPower(0.0));
        new Trigger(copilot::getBButton).onTrue(collector.applyPower(CollectorConstants.DEFAULT_POWER))
            .onFalse(collector.applyPower(0.0));

        new Trigger(copilot::getYButton).onTrue(indexer.applyPower(-IndexerConstants.DEFAULT_POWER))
            .onFalse(indexer.applyPower(0.0));
        new Trigger(copilot::getAButton).onTrue(indexer.applyPower(IndexerConstants.DEFAULT_POWER))
            .onFalse(indexer.applyPower(0.0));

        new Trigger(() -> (getCopilotTriggerDifference() > ControllerConstants.DEADBAND))
            .whileTrue(shooter.applyPower(() -> getCopilotTriggerDifference() * shooterPowerMultiplier.get()));

        // collector, indexer, and shooter smart controls
        new Trigger(() -> copilot.getLeftBumperButton()).whileTrue(new SmartCollect(indexer, collector));
        new Trigger(() -> copilot.getRightBumperButton()).whileTrue(new ExtraSmartShoot(indexer, shooter, 
            () -> getCopilotTriggerDifference() * shooterPowerMultiplier.get()));

        // robot-centric driving while left trigger is held
        new Trigger(() -> driver.getLeftTriggerAxis() > ControllerConstants.DEADBAND).whileTrue(
            drivetrain.applyRequest(DriveRequests.getRobotCentric(() -> -driver.getLeftX() * driveMultiplier.get(), 
            () -> -driver.getLeftY() * driveMultiplier.get(), () -> -driver.getRightX() * driveMultiplier.get())));

        // brake
        new Trigger(driver::getXButton).whileTrue(drivetrain.applyRequest(DriveRequests.getBrake()));

        // reset field forward
        new Trigger(() -> driver.getStartButton() && driver.getBackButton()).onTrue(drivetrain.commandResetFieldForward());

        // switch to single controller mode when enabled
        new Trigger(useSingleController::get).onTrue(new InstantCommand(() -> copilot = driver))
            .onFalse(new InstantCommand(() -> copilot = storedCopilot));
    }

    @Override
    protected void initializeNamedCommands() {}

    @Override
    protected Command getAutonomousCommand() {
        return new InstantCommand();
    }

    private double getCopilotTriggerDifference() {
        // left trigger is disabled when in single controller mode
        return (copilot.getRightTriggerAxis() - (useSingleController.get() ? 0 : driver.getLeftTriggerAxis()));
    }
}
