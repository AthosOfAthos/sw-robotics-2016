package org.usfirst.frc.team2129.robot.subsystems;

import org.usfirst.frc.team2129.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterElevator extends Subsystem {
    
	//config for speeds
	//NEGATIVE IS UP, POSITIVE IS DOWN!
	static double aimSpeedUp        = -.4;//speed for aiming spinners up NEGATIVE
	static double aimSpeedDown      = .15;//speed for aiming spinners down POSITIVE
	static double aimSpeedDownPower = .35;//speed for arms when you need a boost POSITIVE
	static double aimSpeedStop      = -0.15;//don't fall down NEGATIVE
	
	
	private final SpeedController aimer = RobotMap.aimer;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void up()
    {
    	aimer.set( aimSpeedUp );
    }
    
    public void down()
    {
    	aimer.set( aimSpeedDown );
    }
    
    public void downWithBoost()
    {
    	aimer.set( aimSpeedDownPower );
    }
    
    public void lockElevation()
    {
    	aimer.set( aimSpeedStop );
    }
    
    public void stop()
    {
    	aimer.set(0);
    }
}

