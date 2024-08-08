// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DemoConstants;
import frc.robot.Constants.RobotInverts;
import frc.robot.Constants.RobotMap;
import frc.robot.LightningShuffleboard;

public class Drivetrain extends SubsystemBase {

    private Talon left1;
    private Talon left2;
    private Talon right1;
    private Talon right2;

    private DoubleSolenoid shifter;

    private double demoLimit = DemoConstants.DRIVETRAIN_SPEED;

    public Drivetrain() {
        left1 = new Talon(RobotMap.LEFT1);
        left2 = new Talon(RobotMap.LEFT2);
        right1 = new Talon(RobotMap.RIGHT1);
        right2 = new Talon(RobotMap.RIGHT2);

        left1.setInverted(RobotInverts.DRIVETRAIN_LEFT1_INVERT);
        left2.setInverted(RobotInverts.DRIVETRAIN_LEFT2_INVERT);
        right1.setInverted(RobotInverts.DRIVETRAIN_RIGHT1_INVERT);
        right2.setInverted(RobotInverts.DRIVETRAIN_RIGHT2_INVERT);

        shifter = new DoubleSolenoid(RobotMap.PCM, PneumaticsModuleType.CTREPCM, RobotMap.SHIFTER1, RobotMap.SHIFTER2);
        
        LightningShuffleboard.setBoolSupplier("Drivetrain", "Fast", () -> getShifter());
    }

    public void setShifter(boolean fast) {
        shifter.set(fast ? Value.kForward : Value.kReverse);
    }

    public boolean getShifter() {
        return shifter.get() == Value.kForward;
    }

    public Command runShifter(boolean fast) {
        return runOnce(() -> setShifter(fast));
    }

    public void tankDrive(double leftPower, double rightPower) {
        left1.set(leftPower * demoLimit);
        left2.set(leftPower * demoLimit);
        right1.set(rightPower * demoLimit);
        right2.set(rightPower * demoLimit);
    }

    public void stop() {
        tankDrive(0d, 0d);
    }

    @Override
    public void periodic() {
        this.demoLimit = LightningShuffleboard.getDouble("Demo", "Drivetrain Speed", this.demoLimit);
    }
}
