// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.constants.IndexerConstants;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;

public class ExtraSmartCollect extends SmartCollect {

    private boolean isReversing = false;

    public ExtraSmartCollect(Indexer indexer, Collector collector) {

        super(indexer, collector);
    }

    @Override
    public void initialize() {
        isReversing = false;
        super.initialize();
    }

    @Override
    public void execute() {
        if ((isCollecting && !indexer.getCollectorBeamBreak()) || indexer.getShooterBeamBreak()){
            collector.stop();
            indexer.setPower(-IndexerConstants.DEFAULT_POWER);
            isReversing = true;
        }

        super.execute();
    }

    @Override
    public boolean isFinished() {
        return isReversing && indexer.getCollectorBeamBreak();
    }
}
