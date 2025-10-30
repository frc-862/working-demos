// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {

    public static class RobotMap {
        // Motor CAN IDs
        public static final int COLLECTOR_MOTOR_ID = 9;
        public static final int INDEXER_MOTOR_ID = 10;
        public static final int SHOOTER_ONE_MOTOR_ID = 11;
        public static final int SHOOTER_TWO_MOTOR_ID = 12;

        public static final String CANIVORE_CAN_NAME = "Canivore";

        public static final int DRIVER_PORT = 0;
        public static final int COPILOT_PORT = 1;
    }

    public static class ShooterConstants {
        public static final boolean MOTOR_TWO_OPPOSE_MASTER_DIRECTION = false;

        public static final double STATOR_LIMIT = 60;
        public static final boolean INVERT = false;
        public static final boolean BRAKE_MODE = false;
    }

    public static class IndexerConstants {
        public static final boolean INVERT = false;
        public static final double STATOR_LIMIT = 60;
        public static final boolean BRAKE_MODE = false;
    }

    public static class CollectorConstants {
        public static final boolean INVERT = false;
        public static final double STATOR_LIMIT = 60;
        public static final boolean BRAKE_MODE = false;
    }
}
