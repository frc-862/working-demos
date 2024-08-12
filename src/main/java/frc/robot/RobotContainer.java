// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.AutoShoot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class RobotContainer {

    private final Joystick flightstickL = new Joystick(ControllerConstants.FLIGHTSTICKL);
    private final Joystick flightstickR = new Joystick(ControllerConstants.FLIGHTSTICKR);
    private final XboxController copilot = new XboxController(ControllerConstants.COPILOT);

    private Drivetrain drivetrain = new Drivetrain();
    private Indexer indexer = new Indexer();
    private Shooter shooter = new Shooter();

    public RobotContainer() {
        configureBindings();
        configureDefaultCommands();
    }

    private void configureBindings() {
        
        new Trigger(copilot::getLeftBumper).whileTrue(indexer.runIndexer(() -> -1d));
        new Trigger(copilot::getRightBumper).whileTrue(indexer.runIndexer(() -> 1d));
        
        new Trigger(copilot::getAButton).whileTrue(new AutoShoot(shooter, indexer));
    }

    private void configureDefaultCommands() {
        drivetrain.setDefaultCommand(drivetrain.runTank(flightstickL::getY, flightstickR::getY));
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
