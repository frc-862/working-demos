// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
    public static class ControllerConstants {
        public static final int FLIGHTSTICKL = 0;
        public static final int FLIGHTSTICKR = 1;
        public static final int COPILOT = 2;
    }

    public static class RobotMap {
        public static final int LEFT1 = 0;
        public static final int LEFT2 = 1;
        public static final int RIGHT1 = 2;
        public static final int RIGHT2 = 3;

        public static final int INDEXER = 4;
        public static final int SHOOTER = 5;
    }

    public static class DemoConstants {
        public static final double DRIVETRAIN = 0.5d;
        public static final double SHOOTER = 0.5d;
    }

    public static class OperatorConstants {

        // assigns a variable to a port
        public final static int motor0 = 0;
        public final static int motor1 = 1;
        public final static int motor2 = 2;
        public final static int motor3 = 3;

        // public final static int Controllerchan = 1;

        public final static int collectorPort = 4;
        public final static int launcherPort = 5;
        // TODO figure out power
        public final static double launchPower = 1;
        public final static double collectPower = 1;
        public final static double indexPower = 1;

        public final static double mult = 0.5;

    }
}