// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LightningShuffleboard;
import frc.robot.Constants.DemoConstants;
import frc.robot.Constants.RobotMap;

public class Drivetrain extends SubsystemBase {

    private Victor left1;
    private Victor left2;

    private Victor right1;
    private Victor right2;

    private double driveSpeedMult = DemoConstants.DRIVETRAIN;

    public Drivetrain() {
        left1 = new Victor(RobotMap.LEFT1);
        left2 = new Victor(RobotMap.LEFT2);

        right1 = new Victor(RobotMap.RIGHT1);
        right2 = new Victor(RobotMap.RIGHT2);

        right1.setInverted(true);
        right2.setInverted(true);
    }

    public void setLeftPower(double power) {
        left1.set(power * driveSpeedMult);
        left2.set(power * driveSpeedMult);
    }

    public void setRightPower(double power) {
        right1.set(power * driveSpeedMult);
        right2.set(power * driveSpeedMult);
    }

    public void setPower(double left, double right) {
        setLeftPower(left);
        setRightPower(right);
    }

    public void stop() {
        setPower(0d, 0d);
    }

    public Command runTank(DoubleSupplier left, DoubleSupplier right) {
        return startEnd(() -> setPower(left.getAsDouble(), right.getAsDouble()), this::stop);
    }

    @Override
    public void periodic() {
        this.driveSpeedMult = LightningShuffleboard.getDouble("Demo", "Drive Speed Mult", driveSpeedMult);
    }

}
