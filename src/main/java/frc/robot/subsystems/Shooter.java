// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.controls.DutyCycleOut;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotMap;
import frc.robot.Constants.ShooterConstants;
import frc.util.hardware.ThunderBird;

public class Shooter extends SubsystemBase {

    private ThunderBird shooterMotorBottom;
    private ThunderBird shooterMotorTop;

    private DutyCycleOut shooterDutyCycle;
    
    public Shooter() {

        // Initialize the shooter motors with their configuration
        shooterMotorBottom = new ThunderBird(RobotMap.SHOOTER_MOTOR_BOTTOM_ID, RobotMap.CANIVORE_CAN_NAME,
            ShooterConstants.INVERT_TOP_MOTOR, ShooterConstants.STATOR_LIMIT, ShooterConstants.BRAKE_MODE);

        shooterMotorTop = new ThunderBird(RobotMap.SHOOTER_MOTOR_TOP_ID, RobotMap.CANIVORE_CAN_NAME,
            ShooterConstants.INVERT_BOTTOM_MOTOR, ShooterConstants.STATOR_LIMIT, ShooterConstants.BRAKE_MODE);

            shooterDutyCycle = new DutyCycleOut(0d);

        // Set motor two to follow motor one
        // shooterMotorTop.setControl(new Follower(RobotMap.SHOOTER_MOTOR_BOTTOM_ID, ShooterConstants.MOTOR_TWO_OPPOSE_MASTER_DIRECTION));
    }

    /**
     * sets the power for the shooter motors
     * @param power
     */
    public void setPowerTop(double power) {
        shooterMotorTop.setControl(shooterDutyCycle.withOutput(power));
    }

    /**
     * sets the power for the shooter motors
     * @param power
     */
    public void setPowerBottom(double power) {
        shooterMotorBottom.setControl(shooterDutyCycle.withOutput(power));
    }

    // public double getRPM() {
    //     return shooterMotorTop.getVelocity().getValue().magnitude();
    // }

    public void setPower(double power) {
        setPower(power, power);
    }
    
    public void setPower(double powerTop, double powerBottom) {
        setPowerTop(powerTop);
        setPowerBottom(powerBottom);
    }

    /**
     * @param power
     * @return instantCommand that sets the power
     */
    public Command applyPower(double power) {
        return runOnce(() -> {
            setPowerTop(power);
            setPowerBottom(power);
        });
    }

    /**
     * @param powerTop
     * @param powerBottom
     * @return instantCommand that sets the power
     */
    public Command applyPower(double powerTop, double powerBottom) {
        return runOnce(() -> {
            setPowerTop(powerTop);
            setPowerBottom(powerBottom);
        });
    }

    /**
     * @param power
     * @return runCommand that sets the power
     */
    public Command applyPower(DoubleSupplier power){
        return run(() -> {
            setPowerTop(power.getAsDouble());
            setPowerBottom(power.getAsDouble());
        });
    }

    /**
     * @param powerTop
     * @param powerBottom
     * @return runCommand that sets the power
     */
    public Command applyPower(DoubleSupplier powerTop, DoubleSupplier powerBottom){
        return run(() -> {
            setPowerTop(powerTop.getAsDouble());
            setPowerBottom(powerBottom.getAsDouble());
        });
    }
    
}
