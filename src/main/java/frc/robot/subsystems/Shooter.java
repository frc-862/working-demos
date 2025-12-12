// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotMap;
import frc.robot.Constants.ShooterConstants;
import frc.util.hardware.ThunderBird;

public class Shooter extends SubsystemBase {
    private ThunderBird motor;

    /** Creates a new Shooter Subsytem. */
    public Shooter() {
        motor = new ThunderBird(RobotMap.SHOOTER_MOTOR_CAN, RobotMap.CAN_BUS_NAME, RobotMap.SHOOTER_INVERTED,
            ShooterConstants.STATOR_CURRENT_LIMIT, ShooterConstants.BRAKE_MODE);
    }

    @Override
    public void periodic() {}

    /**
     * sets power to the shooter motor
     * @param power set to the motor (-1 to 1)
     */
    public void setPower(double power) {
        motor.setControl(new DutyCycleOut(power));
    }

    /**
     * stops any movement being applied to the shooter motor
     */
    public void stop() {
        motor.stopMotor();
    }
}
