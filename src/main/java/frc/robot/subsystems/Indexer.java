// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.controls.DutyCycleOut;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IndexerConstants;
import frc.robot.Constants.RobotMap;
import frc.util.hardware.ThunderBird;

public class Indexer extends SubsystemBase {
    
    private ThunderBird indexerMotor;
    private DigitalInput collectorBeamBreak;
    private DigitalInput shooterBeamBreak;
    private final DutyCycleOut indexerDutyCycle = new DutyCycleOut(0d);

    public Indexer() {
        // Initialize the indexer motor with its configuration
        indexerMotor = new ThunderBird(RobotMap.INDEXER_MOTOR_ID, RobotMap.CANIVORE_CAN_NAME,
            IndexerConstants.INVERT, IndexerConstants.STATOR_LIMIT, IndexerConstants.BRAKE_MODE);

        // initialize beam breaks
        collectorBeamBreak = new DigitalInput(RobotMap.COLLECTOR_BEAM_BREAK);
        shooterBeamBreak = new DigitalInput(RobotMap.SHOOTER_BEAM_BREAK);
    }

    /**
     * sets the power for the indexer motor
     * @param power
     */
    public void setPower(double power) {
        indexerMotor.setControl(indexerDutyCycle.withOutput(power));
    }

    public void stop() {
        indexerMotor.setControl(indexerDutyCycle.withOutput(0));
    }

    public Command applyStop() {
        return runOnce(() -> stop());
    }

    /**
     * @param power
     * @return instantCommand that sets the power
     */
    public Command applyPower(double power) {
        return runOnce(() -> setPower(power));
    }

    /**
     * @param power
     * @return runCommand that sets the power
     */
    public Command applyPower(DoubleSupplier power){
        return run(() -> setPower(power.getAsDouble()));
    }

    public boolean getCollectorBeamBreak() {
        return collectorBeamBreak.get();
    }

    public boolean getShooterBeamBreak() {
        return shooterBeamBreak.get();
    }
}
