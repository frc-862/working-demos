package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants.IndexerConstants;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class ExtraSmartShoot extends SmartShoot {

    private boolean stoppedShooting;
    private double timeStopped;

    public ExtraSmartShoot(Indexer indexer, Shooter shooter, double shooterPower) {
        super(indexer, shooter, shooterPower);
    }

    @Override
    public void initialize() {
        super.initialize();
        stoppedShooting = false;
        timeStopped = Double.POSITIVE_INFINITY;
    }

    @Override
    public void execute() {
        super.execute();

        // reverse indexer and stop shooter when shooting is finished
        if (isShooting && !indexer.getShooterBeamBreak()){
            shooter.setPower(0d);
            indexer.setPower(-IndexerConstants.DEFAULT_POWER);

            stoppedShooting = true;
            timeStopped = Timer.getFPGATimestamp();
        }
    }

    @Override
    public boolean isFinished() {
        // stop indexer when collector beam break is triggered after shooting
        return (stoppedShooting && indexer.getCollectorBeamBreak()) || 
            // or after 3 seconds of reversing indexer
            (stoppedShooting && (Timer.getFPGATimestamp() - timeStopped) >= IndexerConstants.ESShootTimeout);
    }

    // @Override
    // public void end(){
    //     super.end();
    // }
    
}
