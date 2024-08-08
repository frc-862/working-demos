// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DefaultDemoSpeeds;
import frc.robot.Constants.RobotMap;
import frc.robot.LightningShuffleboard;

public class Collector extends SubsystemBase {

    private Victor lowerBelt;
    private Victor upperBelt;
    private Victor spinny;

    private double demoLimit = DefaultDemoSpeeds.COLLECTOR;

    public Collector() {

        lowerBelt = new Victor(RobotMap.LOWER_BELT);
        upperBelt = new Victor(RobotMap.UPPER_BELT);
        spinny = new Victor(RobotMap.SPINNY);

        spinny.setInverted(true);

        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public Command getStartEndCommand(DoubleSupplier power) {
        return startEnd(() -> setPower(power.getAsDouble()), this::stop);
    }

    public Command runCollector(DoubleSupplier power) {
        return run(() -> setPower(power.getAsDouble()));
    }
    
    public void setLowerBelt(double power) {
        lowerBelt.set(power * demoLimit);
    }

    public void setUpperBelt(double power) {
        upperBelt.set(power * demoLimit);
    }

    public void setSpinny(double power) {
        spinny.set(power * demoLimit);
    }

    public void stopUpperBelt() {
        setUpperBelt(0d);
    }

    public void stopLowerBelt() {
        setLowerBelt(0d);
    }

    public void stopSpinny() {
        setSpinny(0d);
    }

    public void setPower(double power) {
        setLowerBelt(power);
        setUpperBelt(power);
        setSpinny(power);
    }

    public void stop() {
        setPower(0d);
    }

    @Override
    public void periodic() {
        this.demoLimit = LightningShuffleboard.getDouble("Demo", "Collector Limit", demoLimit);
    }
}
