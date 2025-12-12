// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static class RobotMap {
        // Drivetrain
        // Consumer is considered front of robot for reference
        public static int FRONT_LEFT_DRIVE = 0;
        public static int BACK_LEFT_DRIVE = 1;
        public static int FRONT_RIGHT_DRIVE = 2;
        public static int BACK_RIGHT_DRIVE = 3;

        // Shooter
        public static int SHOOTER_MOTOR = 5;

        // Consumer
        public static int CONSUMER_MOTOR = 4;

        // Driver
        public static int DRIVER_PORT = 0;

        // Copilot
        public static int COPILOT_PORT = 1;
    }

    public static class DrivetrainConstants {
        public static boolean FRONT_LEFT_INVERTED = true;
        public static boolean BACK_LEFT_INVERTED = true;
        public static boolean FRONT_RIGHT_INVERTED = false;
        public static boolean BACK_RIGHT_INVERTED = false;
    }

    public static class ShooterConstants {
        public static boolean SHOOTER_INVERTED = false;

        public static double SHOOT_POWER = 1d;
    }

    public static class ConsumerConstants {
        public static boolean CONSUMER_INVERTED = true;

        public static double CONSUME_POWER = 1d;
    }
}
