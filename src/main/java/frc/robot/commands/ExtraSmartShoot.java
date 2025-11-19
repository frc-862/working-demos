package frc.robot.commands;

import java.util.function.DoubleSupplier;

import frc.robot.Constants.IndexerConstants;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class ExtraSmartShoot extends SmartShoot {

    private boolean stoppedShooting;

    public ExtraSmartShoot(Indexer indexer, Shooter shooter, DoubleSupplier shooterPower) {
        super(indexer, shooter, shooterPower);
    }

    @Override
    public void execute() {
        super.execute();

        // reverse indexer and stop shooter when shooting is finished
        if (isShooting && !indexer.getShooterBeamBreak()){
            shooter.setPower(0d);
            indexer.setPower(-IndexerConstants.DEFAULT_POWER);

            stoppedShooting = true;
        }
    }

    @Override
    public boolean isFinished() {
        // stop indexer when collector beam break is triggered after shooting
        return stoppedShooting && indexer.getCollectorBeamBreak();
    }
    
}
