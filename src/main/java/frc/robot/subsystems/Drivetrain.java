// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LightningShuffleboard;
import frc.robot.Constants.DefaultDemoSpeeds;
import frc.robot.Constants.RobotMap;

public class Drivetrain extends SubsystemBase{

    private Victor left;
    private Victor right;

    private DifferentialDrive drive;

    private double demoLimit = DefaultDemoSpeeds.DRIVETRAIN;

    public Drivetrain() {

        left = new Victor(RobotMap.LEFT);
        right = new Victor(RobotMap.RIGHT);

        left.setInverted(true);
        right.setInverted(false);

        drive = new DifferentialDrive(left, right);

        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public void tankDrive(double leftPower, double rightPower) {
        drive.tankDrive(leftPower * demoLimit, rightPower * demoLimit, false);
    }

    public void arcadeDrive(double speed, double rotation) {
        drive.arcadeDrive(speed * demoLimit, rotation * demoLimit, false);
    }

    public Command runTank(DoubleSupplier leftPower, DoubleSupplier rightPower) {
        return run(() -> tankDrive(leftPower.getAsDouble(), rightPower.getAsDouble()));
    }

    public Command runArcade(DoubleSupplier speed, DoubleSupplier rotation) {
        return run(() -> arcadeDrive(speed.getAsDouble(), rotation.getAsDouble()));
    }

    public void stop() {
        tankDrive(0d, 0d);
    }

    @Override
    public void periodic() {
        this.demoLimit = LightningShuffleboard.getDouble("Demo", "Drivetrain Limit", demoLimit);
    }
}
