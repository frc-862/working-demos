// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;

public class Index extends Command {
    private Indexer indexer;
    
    private DoubleSupplier power;

    /** 
     * Creates a new Index Command.
     * @param indexer subsystem
     * @param power to put into the indexer motor
     */
    public Index(Indexer indexer, DoubleSupplier power) {
        this.indexer = indexer;

        this.power = power;

        addRequirements(indexer);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        indexer.setPower(power.getAsDouble());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        indexer.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
