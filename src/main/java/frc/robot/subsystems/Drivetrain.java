// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.RobotMap;
import frc.util.hardware.ThunderBird;

public class Drivetrain extends SubsystemBase {
    public ThunderBird rightOne;
    public ThunderBird rightTwo;
    public ThunderBird rightThree;

    public ThunderBird leftOne;
    public ThunderBird leftTwo;
    public ThunderBird leftThree;

    /** Creates a new Drivetrain Subsystem. */
    public Drivetrain() {
        leftOne = new ThunderBird(RobotMap.LEFT_ONE_CAN, RobotMap.CAN_BUS_NAME, RobotMap.LEFT_ONE_INVERTED,
            DrivetrainConstants.STATOR_CURRENT_LIMIT, DrivetrainConstants.BRAKE_MODE);
        leftTwo = new ThunderBird(RobotMap.LEFT_TWO_CAN, RobotMap.CAN_BUS_NAME, RobotMap.LEFT_TWO_INVERTED,
            DrivetrainConstants.STATOR_CURRENT_LIMIT, DrivetrainConstants.BRAKE_MODE);
        leftThree = new ThunderBird(RobotMap.LEFT_THREE_CAN, RobotMap.CAN_BUS_NAME, RobotMap.LEFT_THREE_INVERTED,
            DrivetrainConstants.STATOR_CURRENT_LIMIT, DrivetrainConstants.BRAKE_MODE);

        rightOne = new ThunderBird(RobotMap.RIGHT_ONE_CAN, RobotMap.CAN_BUS_NAME, RobotMap.RIGHT_ONE_INVERTED,
            DrivetrainConstants.STATOR_CURRENT_LIMIT, DrivetrainConstants.BRAKE_MODE);
        rightTwo = new ThunderBird(RobotMap.RIGHT_TWO_CAN, RobotMap.CAN_BUS_NAME, RobotMap.RIGHT_TWO_INVERTED,
            DrivetrainConstants.STATOR_CURRENT_LIMIT, DrivetrainConstants.BRAKE_MODE);
        rightThree = new ThunderBird(RobotMap.RIGHT_THREE_CAN, RobotMap.CAN_BUS_NAME, RobotMap.RIGHT_THREE_INVERTED,
            DrivetrainConstants.STATOR_CURRENT_LIMIT, DrivetrainConstants.BRAKE_MODE);
    }

    @Override
    public void periodic() {}

    /**
     * sets power to the left side of the drivetrain
     * @param power set to the motors (-1 to 1)
     */
    public void setLeftPower(double power) {
        leftOne.setControl(new DutyCycleOut(power));
        leftTwo.setControl(new DutyCycleOut(power));
        leftThree.setControl(new DutyCycleOut(power));
    }

    /**
     * sets power to the right side of the drivetrain
     * @param power set to the motors (-1 to 1)
     */
    public void setRightPower(double power) {
        rightOne.setControl(new DutyCycleOut(power));
        rightTwo.setControl(new DutyCycleOut(power));
        rightThree.setControl(new DutyCycleOut(power));
    }

    /**
     * stops any movement being applied to the left side of the drivetrain
     */
    public void leftStop() {
        leftOne.stopMotor();
        leftTwo.stopMotor();
        leftThree.stopMotor();
    }

    /**
     * stops any movement being applied to the right side of the drivetrain
     */
    public void rightStop() {
        rightOne.stopMotor();
        rightTwo.stopMotor();
        rightThree.stopMotor();
    }
}
