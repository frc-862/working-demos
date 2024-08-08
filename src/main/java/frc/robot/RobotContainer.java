// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class RobotContainer {

    private final Joystick flightstickL = new Joystick(Constants.OperatorConstants.FLIGHTSTICK_L);
    private final Joystick flightstickR = new Joystick(Constants.OperatorConstants.FLIGHTSTICK_R);
    private final XboxController xbox = new XboxController(Constants.OperatorConstants.XBOX_PORT);

    private Drivetrain drivetrain = new Drivetrain();
    private Shooter shooter = new Shooter();

    public RobotContainer() {
        configureBindings();
        configureDefaultCommands();
    }

    private void configureBindings() {
        // Left for slow Right for fast
        new Trigger(xbox::getLeftBumper).onTrue(drivetrain.runShifter(false));
        new Trigger(xbox::getRightBumper).onTrue(drivetrain.runShifter(true));

        // Push disc into shooter while A is held
        new Trigger(xbox::getAButton).onTrue(shooter.runSolenoid(true)).onFalse(shooter.runSolenoid(false));
    }

    private void configureDefaultCommands() {
        drivetrain.setDefaultCommand(new TankDrive(drivetrain, () -> flightstickL.getY(), () -> flightstickR.getY()));
        shooter.setDefaultCommand(shooter.runShooter(xbox::getRightTriggerAxis));
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
