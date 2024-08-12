// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class AutoShoot extends Command {

    private static final double SHOOT_POWER = 1d;
    private static final double SHOOT_TIME = 2d;

    private Shooter shooter;
    private Indexer indexer;

    private Timer timer;

    public AutoShoot(Shooter shooter, Indexer indexer) {
        this.shooter = shooter;
        this.indexer = indexer;

        timer = new Timer();

        timer.reset();

        addRequirements(shooter, indexer);
    }

    @Override
    public void initialize() {
        timer.start();
        shooter.setPower(SHOOT_POWER);
    }

    @Override
    public void execute() {
        if (timer.get() > SHOOT_TIME) {
            indexer.setPower(1d);
        }
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        shooter.stop();
        indexer.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
