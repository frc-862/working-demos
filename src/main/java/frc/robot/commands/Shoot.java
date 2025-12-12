// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import frc.util.shuffleboard.LightningShuffleboard;

public class Shoot extends Command {
    private Shooter shooter;

    private DoubleSupplier power;

    /** 
     * Creates a new Shoot Command.
     * @param shooter subsystem
     * @param power to put into the shooter motor
     */
    public Shoot(Shooter shooter, DoubleSupplier power) {
        this.shooter = shooter;

        this.power = power;

        addRequirements(shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double mult = LightningShuffleboard.getDouble("Demo", "Shoot Mult (0 to 1)", 1);

        shooter.setPower(mult * power.getAsDouble());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
