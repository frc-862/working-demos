// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Collector;

public class Collect extends Command {
    Collector collector;

    DoubleSupplier power;

    /** 
     * Creates a new Collect Command.
     * @param collector subsystem
     * @param power to put into the collector motor
     */
    public Collect(Collector collector, DoubleSupplier power) {
        this.collector = collector;

        this.power = power;

        addRequirements(collector);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        collector.setPower(power.getAsDouble());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        collector.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
