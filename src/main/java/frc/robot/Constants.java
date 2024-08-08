// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
    public static class OperatorConstants {
        public static final int FLIGHTSTICK_L = 0;
        public static final int FLIGHTSTICK_R = 1;
        public static final int XBOX_PORT = 2;
    }

    public static class RobotMap {
        public static final int LEFT1 = 2;
        public static final int LEFT2 = 3;
        public static final int RIGHT1 = 0;
        public static final int RIGHT2 = 1;
        public static final int PCM = 11;
        public static final int SHIFTER1 = 0;
        public static final int SHIFTER2 = 1;
        public static final int FLICKER1 = 2;
        public static final int FLICKER2 = 3;

        // SHOOTER:
        public static final int SHOOTER_TOP = 6;
        public static final int SHOOTER_BOTTOM = 5;
        public static final int GENERIC_SUB = 2;
    }

    public static class DemoConstants {
        public static final double DRIVETRAIN_SPEED = 0.5d;

        public static final double SHOOTER_SPEED = 0.35d;
    }

    public static final class RobotInverts {
        public static final boolean DRIVETRAIN_LEFT1_INVERT = true;
        public static final boolean DRIVETRAIN_LEFT2_INVERT = true;
        public static final boolean DRIVETRAIN_RIGHT1_INVERT = false;
        public static final boolean DRIVETRAIN_RIGHT2_INVERT = false;

        public static final boolean SHOOTER_TOP_INVERT = true;
        public static final boolean SHOOTER_BOTTOM_INVERT = true;
    }
}
