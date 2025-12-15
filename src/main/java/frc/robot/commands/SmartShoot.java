// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.DoubleSupplier;

import frc.robot.Constants.IndexerConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.util.leds.LEDCommand;

public class SmartShoot extends LEDCommand {

    protected Indexer indexer;
    protected Shooter shooter;

    protected double shooterPower;
    protected boolean isShooting;
    
    public SmartShoot(Indexer indexer, Shooter shooter, double shooterPower) {
        this.indexer = indexer;
        this.shooter = shooter;
        this.shooterPower = shooterPower;

        addRequirements(indexer, shooter);
    }

    @Override
    public void initialize() {

        isShooting = false;

        shooter.setPower(shooterPower);

        // // wait for shooter to spin up before starting indexer
        // new Timer().schedule(new TimerTask() {
        //     @Override
        //     public void run(){
        //         indexer.setPower(IndexerConstants.DEFAULT_POWER);
        //     }
        //  }, ShooterConstants.SHOOT_DELAY);
    }

    @Override
    public void execute() {

        // if (shooterPower)

        // if (!isShooting && indexer.getShooterBeamBreak()) {
        //     isShooting = true;
        // }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
        indexer.stop();;

        succeeded(true);
    }

    @Override
    public boolean isFinished() {
        return isShooting && !indexer.getShooterBeamBreak();
    }
}
