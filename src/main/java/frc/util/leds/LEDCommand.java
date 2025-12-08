package frc.util.leds;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.Command;

public class LEDCommand extends Command {
    private ArrayList<Command> successCommands;
    private ArrayList<Command> failCommands;

    public LEDCommand() {
        super();

        successCommands = new ArrayList<>();
        failCommands = new ArrayList<>();
    }

    public Command onSuccess(Command command) {
        successCommands.add(command);

        return this;
    }

    public Command onFail(Command command) {
        failCommands.add(command);

        return this;
    }

    protected void succeeded(boolean succeeded) {
        if (succeeded) {
            for (Command command : successCommands) {
                command.schedule();
            }
        } else {
            for (Command command : failCommands) {
                command.schedule();
            }
        }
            
    }
}
