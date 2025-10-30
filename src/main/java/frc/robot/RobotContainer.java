// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.RobotMap;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.util.LightningContainer;
import frc.util.shuffleboard.LightningShuffleboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer extends LightningContainer {

    private Collector collector;
    private Indexer indexer;
    private Shooter shooter;

    private XboxController driver = new XboxController(RobotMap.DRIVER_PORT);
    private XboxController copilot = new XboxController(RobotMap.COPILOT_PORT);

    @Override
    protected void initializeSubsystems() {
        collector = new Collector();
        indexer = new Indexer();
        shooter = new Shooter();
    }

    @Override
    protected void configureDefaultCommands() {
        // Set the default command for the shooter to be controlled by the copilot's triggers
        shooter.setDefaultCommand(shooter.applyPower(() -> (copilot.getRightTriggerAxis() - copilot.getLeftTriggerAxis())
            * LightningShuffleboard.getDouble("Shooter", "Shooter Power Multiplier", 0.4)));
    }

    @Override
    protected void configureButtonBindings() {
        // Collector and Indexer controls
        new Trigger(copilot::getXButton).onTrue(collector.applyPower(1.0)).onFalse(collector.applyPower(0.0));
        new Trigger(copilot::getBButton).onTrue(collector.applyPower(-1.0)).onFalse(collector.applyPower(0.0));

        new Trigger(copilot::getYButton).onTrue(indexer.applyPower(1.0)).onFalse(indexer.applyPower(0.0));
        new Trigger(copilot::getAButton).onTrue(indexer.applyPower(1.0)).onFalse(indexer.applyPower(0.0));
    }

    @Override
    protected void initializeNamedCommands() {}

    @Override
    protected Command getAutonomousCommand() {
        return new InstantCommand();
    }
}
