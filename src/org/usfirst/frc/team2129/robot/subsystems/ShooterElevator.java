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
	static double aimSpeedUp        = -.35;//speed for aiming spinners up NEATIVE
	static double aimSpeedDown      = .15;//speed for aiming spinners down POSITIVE
	static double aimSpeedDownPower = .35;//speed for arms when you need a boost POSITIVE
	static double aimSpeedStop      = -0.20;//don't fall down NEGATIVE
	static double armSpeed          = -.3;//speed for the arm must be NEGATIVE
	
	private final SpeedController elevator = RobotMap.shooterElevator;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void Raise()
    {
    	elevator.set( aimSpeed );
    }
    
    public void Lower()
    {
    	elevator.set( aimSpeed * -1.0 );
    }
    
    public void Stop()
    {
    	elevator.set(0);
    }
}

