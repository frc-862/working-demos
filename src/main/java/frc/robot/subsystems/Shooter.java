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

public class Shooter extends SubsystemBase {

    private Victor motor;
    
    private double shooterPowerMult = DemoConstants.SHOOTER;
    
    public Shooter() {
        motor = new Victor(RobotMap.SHOOTER);
    }

    public void setPower(double power) {
        motor.set(power * shooterPowerMult);
    }

    public void stop() {
        setPower(0d);
    }

    public Command runShooter(DoubleSupplier power) {
        return startEnd(() -> setPower(power.getAsDouble()), this::stop);
    }

    @Override
    public void periodic() {
        this.shooterPowerMult = LightningShuffleboard.getDouble("Demo", "Shooter Power Mult", shooterPowerMult);
    }
}
