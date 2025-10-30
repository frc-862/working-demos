// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.util.LightningRobot;
import frc.util.shuffleboard.LightningShuffleboard;

public class Robot extends LightningRobot {

    @SuppressWarnings("resource")
    public Robot() {
        super(new RobotContainer());
    }

    @Override
    public void robotInit() {
        super.robotInit();

        LightningShuffleboard.setString("Demo", "Instructions", "Copilot triggers control shooter power. " 
            + "X/B control collector. Y/A control indexer.");

        LightningShuffleboard.setDouble("Demo", "Shooter Power Multiplier", 0.4);
        LightningShuffleboard.setDouble("Demo", "Drive Multiplier", 0.4);
    }

}
