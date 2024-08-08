// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.RSL;

public class FlashRSL extends Command {

    private RSL rsl;

    private int count = 0;
    private boolean state = true;

    public FlashRSL(RSL rsl) {
        this.rsl = rsl;

        addRequirements(rsl);
    }

    @Override
    public void initialize() {
        rsl.setPower(rsl.getPowerLimit());

    }

    @Override
    public void execute() {
        count++;
        if (count == 12) {
            state = !state;
            rsl.setPower(state ? rsl.getPowerLimit() : 0d);
            count = 0;
        }
    }

    @Override
    public void end(boolean interrupted) {
        rsl.setPower(0d);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
