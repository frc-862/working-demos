// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.RobotMap;

public class Drivetrain extends SubsystemBase {
    private Victor frontLeft;
    private Victor backLeft;

    private Victor frontRight;
    private Victor backRight;

    /** Creates a new Drivetrain Subsystem. */
    public Drivetrain() {
        frontLeft = new Victor(RobotMap.FRONT_LEFT_DRIVE);
        backLeft = new Victor(RobotMap.BACK_LEFT_DRIVE);

        frontLeft.setInverted(DrivetrainConstants.FRONT_LEFT_INVERTED);
        backLeft.setInverted(DrivetrainConstants.BACK_LEFT_INVERTED);

        frontRight = new Victor(RobotMap.FRONT_RIGHT_DRIVE);
        backRight = new Victor(RobotMap.BACK_RIGHT_DRIVE);

        frontRight.setInverted(DrivetrainConstants.FRONT_RIGHT_INVERTED);
        backRight.setInverted(DrivetrainConstants.BACK_RIGHT_INVERTED);
    }

    @Override
    public void periodic() {}

    /**
     * Sets the power to the Drivetrain's left wheels
     * @param power from -1 to 1
     */
    public void setLeftPower(double power) {
        frontLeft.set(power);
        backLeft.set(power);
    }

    /**
     * Sets the power to the Drivetrain's right wheels
     * @param power from -1 to 1
     */
    public void setRightPower(double power) {
        frontRight.set(power);
        backRight.set(power);
    }

    /**
     * Stops any movement being applied to the Drivetrain's left side
     */
    public void stopLeft() {
        frontLeft.stopMotor();
        backLeft.stopMotor();
    }

    /**
     * Stops any movement being applied to the Drivetrain's right side
     */
    public void stopRight() {
        frontRight.stopMotor();
        backRight.stopMotor();
    }
}
