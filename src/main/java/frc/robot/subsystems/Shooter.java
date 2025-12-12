// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotMap;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private Victor motor;

    /** Creates a new Shooter Subsystem. */
    public Shooter() {
        motor = new Victor(RobotMap.SHOOTER_MOTOR);

        motor.setInverted(ShooterConstants.SHOOTER_INVERTED);
    }

    @Override
    public void periodic() {}

    /**
     * Sets the power to the Shooter's motor
     * @param power from -1 to 1
     */
    public void setPower(double power) {
        motor.set(power);
    }

    /**
     * Stops any movement being applied to the Shooter motor
     */
    public void stop() {
        motor.stopMotor();
    }
}
