// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;
import frc.robot.LightningShuffleboard;
import frc.robot.Constants.DemoConstants;
import frc.robot.Constants.RobotMap;

public class Shooter extends SubsystemBase {

    private TalonSRX topMotor;
    private TalonSRX bottomMotor;

    private DoubleSolenoid pieceMover;

    private double demoLimit = DemoConstants.SHOOTER_SPEED;

    public Shooter() {
        topMotor = new TalonSRX(RobotMap.SHOOTER_TOP);
        bottomMotor = new TalonSRX(RobotMap.SHOOTER_BOTTOM);
        pieceMover = new DoubleSolenoid(RobotMap.PCM, PneumaticsModuleType.CTREPCM, RobotMap.FLICKER1,
                RobotMap.FLICKER2);

        topMotor.configFactoryDefault();
        bottomMotor.configFactoryDefault();

        topMotor.setInverted(Constants.RobotInverts.SHOOTER_TOP_INVERT);
        bottomMotor.setInverted(Constants.RobotInverts.SHOOTER_BOTTOM_INVERT);

        topMotor.setNeutralMode(NeutralMode.Coast);
        bottomMotor.setNeutralMode(NeutralMode.Coast);

        bottomMotor.follow(topMotor);

        LightningShuffleboard.setDoubleSupplier("Shooter", "Top Power", () -> topMotor.getMotorOutputPercent());
        LightningShuffleboard.setDoubleSupplier("Shooter", "Bottom Power", () -> bottomMotor.getMotorOutputPercent());
        LightningShuffleboard.setBoolSupplier("Shooter", "Is Shooting", () -> pieceMover.get() == Value.kForward);
    }

    public void setPower(double power) {
        topMotor.set(TalonSRXControlMode.PercentOutput, power * this.demoLimit);
    }

    public void setSolenoidValue(boolean in) {
        pieceMover.set(in ? Value.kReverse : Value.kForward);
    }

    public Command runSolenoid(boolean in) {
        return runOnce(() -> setSolenoidValue(in));
    }

    public Command runShooter(DoubleSupplier power) {
        return run(() -> setPower(power.getAsDouble()));
    }

    public void stop() {
        setPower(0d);
    }

    @Override
    public void periodic() {
        this.demoLimit = LightningShuffleboard.getDouble("Demo", "Shooter Limit", this.demoLimit);
    }
}
