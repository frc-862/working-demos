// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.CurrentDriveMode;
import frc.robot.commands.FlashRSL;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RSL;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;

public class RobotContainer {

    private final Joystick leftJoystick = new Joystick(0);
    private final Joystick rightJoystick = new Joystick(1);
    private final XboxController controller = new XboxController(2);

    private Drivetrain drivetrain = new Drivetrain();
    private Collector collector = new Collector();
    private Shooter shooter = new Shooter();
    private Spindexer spindexer = new Spindexer(collector);
    private RSL rsl = new RSL();

    private CurrentDriveMode driveMode = CurrentDriveMode.TANK;

    public RobotContainer() {
        configureBindings();
        configureDefaultCommands();

        LightningShuffleboard.setStringSupplier("Demo", "Drive Mode", () -> driveMode.name());
    }

    private void configureBindings() {
        // Switch between arcade and tank drive
        new Trigger(controller::getStartButton).onTrue(new InstantCommand(() -> {
            driveMode = driveMode == CurrentDriveMode.TANK ? CurrentDriveMode.ARCADE : CurrentDriveMode.TANK;
            drivetrain.setDefaultCommand(
                    driveMode == CurrentDriveMode.TANK ? drivetrain.runTank(leftJoystick::getY, rightJoystick::getY)
                            : drivetrain.runArcade(controller::getLeftY, controller::getRightX));
        }));

        new Trigger(controller::getBButton).whileTrue(shooter.runStartEnd());
        new Trigger(controller::getLeftBumper).whileTrue(spindexer.runStartEnd(-1d));
        new Trigger(controller::getRightBumper).whileTrue(spindexer.runStartEnd(1d));

    }

    private void configureDefaultCommands() {
        // Default tank drive
        drivetrain.setDefaultCommand(drivetrain.runTank(leftJoystick::getY, rightJoystick::getY));

        collector.setDefaultCommand(
                collector.runCollector(() -> controller.getRightTriggerAxis() - controller.getLeftTriggerAxis()));

        rsl.setDefaultCommand(new FlashRSL(rsl));
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
