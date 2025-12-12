// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Indexer extends SubsystemBase {
    VictorSPX motor;

    /** Creates a new Indexer Subsystem. */
    public Indexer() {
        motor = new VictorSPX(RobotMap.INDEXER_MOTOR_CAN);
        motor.setInverted(RobotMap.INDEXER_INVERTED);
    }

    @Override
    public void periodic() {}

    /**
     * sets power to the indexer motor
     * @param power set to the motor (-1 to 1)
     */
    public void setPower(double power) {
        motor.set(ControlMode.PercentOutput, power);
    }

    /**
     * sets the power to zero for the indexer motor
     */
    public void stop() {
        motor.set(ControlMode.PercentOutput, 0d);
    }
}