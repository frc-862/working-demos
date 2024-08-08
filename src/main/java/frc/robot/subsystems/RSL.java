// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DefaultDemoSpeeds;
import frc.robot.Constants.RobotMap;

public class RSL extends SubsystemBase {

    private Victor rslMotor;

    public RSL() {
        rslMotor = new Victor(RobotMap.RSL);
    }

    public void setPower(double power) {
        rslMotor.set(power);
    }

    public void stop() {
        setPower(0d);
    }

    public double getPowerLimit() {
        return DefaultDemoSpeeds.RSL;
    }

}
