// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LightningShuffleboard;
import frc.robot.Constants.DefaultDemoSpeeds;
import frc.robot.Constants.RobotMap;

public class Shooter extends SubsystemBase {

    private Victor shooterMotor;

    private double demoLimit = DefaultDemoSpeeds.SHOOTER;

    public Shooter() {
        shooterMotor = new Victor(RobotMap.FLYWHEEL);
    }

    public void setPower(double power) {
        shooterMotor.set(power * demoLimit);
    }

    public void stop() {
        setPower(0d);
    }

    public Command runStartEnd() {
        return startEnd(() -> setPower(1d), this::stop);
    }

    @Override
    public void periodic() {
        this.demoLimit = LightningShuffleboard.getDouble("Demo", "Shooter Limit", demoLimit);
    }
}
