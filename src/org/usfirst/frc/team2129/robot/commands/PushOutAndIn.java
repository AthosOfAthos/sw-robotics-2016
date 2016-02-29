package org.usfirst.frc.team2129.robot.commands;

import org.usfirst.frc.team2129.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PushOutAndIn extends CommandGroup {
    
    public  PushOutAndIn() {
    	super.setInterruptible(false);
    	addSequential( new PusherOut() );
    	addSequential( new PusherIn () );
    	
    	requires(Robot.shooterPusher);
 
    }
}
