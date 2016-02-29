package org.usfirst.frc.team2129.robot.commands;

import org.usfirst.frc.team2129.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PusherIn extends Command {

	private int cycleCount;
	final static private int pusherCycleLimit = 20;
	
    public PusherIn() {
    	super.setInterruptible(false);
    	requires( Robot.shooterPusher);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cycleCount = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooterPusher.pushOut();
    	cycleCount++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (cycleCount >= pusherCycleLimit)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterPusher.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
