// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotMap;

public class Indexer extends SubsystemBase {
    
    private Victor motor;

    public Indexer() {
        motor = new Victor(RobotMap.INDEXER);

        motor.setInverted(true);
    }

    public void setPower(double power) {
        motor.set(power);
    }

    public void stop() {
        setPower(0d);
    }

    public Command runIndexer(DoubleSupplier power) {
        return startEnd(() -> setPower(power.getAsDouble()), this::stop);
    }
}
