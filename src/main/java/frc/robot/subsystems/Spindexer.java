// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DefaultDemoSpeeds;
import frc.robot.Constants.RobotMap;
import frc.robot.LightningShuffleboard;

public class Spindexer extends SubsystemBase {

    private Collector collector;

    private Victor spindexMotor;

    private double demoLimit = DefaultDemoSpeeds.SPINDEXER;

    public Spindexer(Collector collector) {
        this.collector = collector;

        spindexMotor = new Victor(RobotMap.SPINDEXER);

        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public void setPower(double power) {
        spindexMotor.set(power * demoLimit);
        collector.setUpperBelt(power * demoLimit);
    }

    public void stop() {
        setPower(0d);
        collector.stopUpperBelt();
    }

    public Command runStartEnd(double power) {
        return startEnd(() -> setPower(power), this::stop);
    }

    @Override
    public void periodic() {
        this.demoLimit = LightningShuffleboard.getDouble("Demo", "Spindexer Limit", demoLimit);
    }
}
