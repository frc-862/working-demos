package frc.robot;

public class Constants {
    
    public class RobotMap {
        // Drivetrain
        public static final int LEFT = 1;
        public static final int RIGHT = 0;

        // Collecter
        public static final int LOWER_BELT = 2;
        public static final int UPPER_BELT = 3;
        public static final int SPINNY = 5;

        // Spindexer
        public static final int SPINDEXER = 4;

        // shooter
        public static final int FLYWHEEL = 6;

        // RSL
        public static final int RSL = 7;
    }

    public class DefaultDemoSpeeds {
        public static final double RSL = 0.75d;

        public static final double DRIVETRAIN = 0.5d;
        public static final double SPINDEXER = 1d;
        public static final double COLLECTOR = 0.75d;
        public static final double SHOOTER = 0.75d;
    }

    public enum CurrentDriveMode {
        TANK, ARCADE
    }
}
