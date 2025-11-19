// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.CollectorConstants;
import frc.robot.Constants.IndexerConstants;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;

public class SmartCollect extends Command {

    private Indexer indexer;
    private Collector collector;

    private boolean isCollecting;
  
    public SmartCollect(Indexer indexer, Collector collector) {
        this.indexer = indexer;
        this.collector = collector;

        addRequirements(indexer, collector);
    }

    @Override
    public void initialize() {
        isCollecting = false;
        collector.setPower(CollectorConstants.DEFAULT_POWER);
    }

    @Override
    public void execute() {
        // start indexer when collector beam break is triggered
        if (indexer.getCollectorBeamBreak() && !isCollecting) {
            isCollecting = true;
            indexer.setPower(IndexerConstants.DEFAULT_POWER);
        }
    }

    @Override
    public void end(boolean interrupted) {
        collector.setPower(0.0);
        indexer.setPower(0.0);
    }

    @Override
    public boolean isFinished() {
        // stop collecting when collector beam break is no longer triggered or when shooter beam break is triggered
        return (isCollecting && !indexer.getCollectorBeamBreak()) || indexer.getShooterBeamBreak();
    }
}
