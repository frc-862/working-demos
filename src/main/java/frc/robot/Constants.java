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
        public static final String CAN_BUS_NAME = "Rio";

        public static final int LEFT_ONE_CAN = 4;
        public static final int LEFT_TWO_CAN = 5;
        public static final int LEFT_THREE_CAN = 6;
        public static final boolean LEFT_ONE_INVERTED = true;
        public static final boolean LEFT_TWO_INVERTED = true;
        public static final boolean LEFT_THREE_INVERTED = true;

        public static final int RIGHT_ONE_CAN = 1;
        public static final int RIGHT_TWO_CAN = 2;
        public static final int RIGHT_THREE_CAN = 3;
        public static final boolean RIGHT_ONE_INVERTED = false;
        public static final boolean RIGHT_TWO_INVERTED = false;
        public static final boolean RIGHT_THREE_INVERTED = false;

        public static final int COLLECTOR_MOTOR_CAN = 15;
        public static final boolean COLLECTOR_INVERTED = false;

        public static final int INDEXER_MOTOR_CAN = 16;
        public static final boolean INDEXER_INVERTED = false;

        public static final int SHOOTER_MOTOR_CAN = 11;
        public static final boolean SHOOTER_INVERTED = false;
    }
    
    public static class ControllerConstants {
        public static final int DRIVER_CONTROLLER_PORT = 0;
        public static final int COPILOT_CONTROLLER_PORT = 1;
    }

    public final class DrivetrainConstants {
        public static final double ROTOR_TO_SENSOR_RATIO = 10.71;
        public static final double SENSOR_TO_MECHANISM_RATIO = 1/1; // 1:1 ratio

        public static final double STATOR_CURRENT_LIMIT = 120d;

        public static final boolean BRAKE_MODE = true;
    }

    public static class CollectorConstants {
        public static final double STATOR_CURRENT_LIMIT = 120d;

        public static final boolean BRAKE_MODE = true;
    }

    public static class IndexerConstants {
        public static final double INTAKE_POWER = 1d;

        public static final double SPIT_POWER = 1d;
    }

    public static class ShooterConstants {
        public static final double SHOOT_POWER = 1d;

        public static final double STATOR_CURRENT_LIMIT = 120d;

        public static final boolean BRAKE_MODE = true;
    }
}
