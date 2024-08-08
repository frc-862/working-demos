// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class TankDrive extends Command {

    private Drivetrain drivetrain;

    private DoubleSupplier leftPower;
    private DoubleSupplier rightPower;

    public TankDrive(Drivetrain drivetrain, DoubleSupplier leftPower, DoubleSupplier rightPower) {
        this.drivetrain = drivetrain;
        this.leftPower = leftPower;
        this.rightPower = rightPower;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        drivetrain.tankDrive(leftPower.getAsDouble(), rightPower.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
