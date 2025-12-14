package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.IndexerConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.Collect;
import frc.robot.commands.Index;
import frc.robot.commands.Shoot;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.util.LightningContainer;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer extends LightningContainer {
    private Drivetrain drivetrain;
    private Shooter shooter;
    private Indexer indexer;
    private Collector collector;

    private static XboxController driver;
    private static XboxController copilot;

    @Override
    protected void initializeSubsystems() {
        drivetrain = new Drivetrain();
        shooter = new Shooter();
        indexer = new Indexer();
        collector = new Collector();

        driver = new XboxController(ControllerConstants.DRIVER_CONTROLLER_PORT);
        copilot = new XboxController(ControllerConstants.COPILOT_CONTROLLER_PORT);
    }

    @Override
    protected void configureDefaultCommands() {
        drivetrain.setDefaultCommand(new TankDrive(drivetrain, () -> MathUtil.applyDeadband(driver.getLeftY(), 0.1d), () -> MathUtil.applyDeadband(driver.getRightY(), 0.1d)));

        collector.setDefaultCommand(new Collect(collector, () -> (copilot.getRightTriggerAxis() - copilot.getLeftTriggerAxis())));
    }

    @Override
    protected void configureButtonBindings() {
        new Trigger(copilot::getAButton).whileTrue(new Shoot(shooter, () -> ShooterConstants.SHOOT_POWER));

        new Trigger(copilot::getLeftBumperButton).whileTrue(new Index(indexer, () -> IndexerConstants.SPIT_POWER));
        new Trigger(copilot::getRightBumperButton).whileTrue(new Index(indexer, () -> IndexerConstants.INTAKE_POWER));
    }

    @Override
    protected void initializeNamedCommands() {}
    
    @Override
    protected Command getAutonomousCommand() {
        return new WaitCommand(1);
    }
}
