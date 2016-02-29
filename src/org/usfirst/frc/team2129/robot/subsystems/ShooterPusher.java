package org.usfirst.frc.team2129.robot.subsystems;

import org.usfirst.frc.team2129.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterPusher extends Subsystem {

	//config for ball pusher
	final static int cycles = 20;
	final static double pushOutSpeed = .3; //this is the wiggly
	final static double pushInSpeed = -.3;
	
	private final SpeedController pusher       = RobotMap.pusher;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void pushOut()
    {
    	pusher.set(pushOutSpeed);
    }
    
    public void pushIn()
    {
    	pusher.set(pushInSpeed);
    }
    
    public void stop()
    {
    	pusher.set(0);
    }
}

