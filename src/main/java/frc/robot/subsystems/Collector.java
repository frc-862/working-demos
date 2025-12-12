// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CollectorConstants;
import frc.robot.Constants.RobotMap;
import frc.util.hardware.ThunderBird;

public class Collector extends SubsystemBase {
    ThunderBird motor;

    public Collector() {
        motor = new ThunderBird(RobotMap.COLLECTOR_MOTOR_CAN, RobotMap.CAN_BUS_NAME, RobotMap.COLLECTOR_INVERTED,
            CollectorConstants.STATOR_CURRENT_LIMIT, CollectorConstants.BRAKE_MODE);
    }

    @Override
    public void periodic() {}

    /**
     * sets power to the collector motor
     * @param power set to the motor (-1 to 1)
     */
    public void setPower(double power) {
        motor.setControl(new DutyCycleOut(power));
    }

    /**
     * stops any movement being applied to the collector motor
     */
    public void stop() {
        motor.stopMotor();
    }
}
