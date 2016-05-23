package org.usfirst.frc.team2129.robot.subsystems;

import org.usfirst.frc.team2129.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterLauncher extends Subsystem {
    
	double spinSpeed = -0.75;//speed for spinners always be NEGATIVE
	double spinSpeedIn = -0.5;//Mod for collecting ball

    private final SpeedController spinnerLeft  = RobotMap.spinnerLeft;
    private final SpeedController spinnerRight = RobotMap.spinnerRight;
        
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //tells launcher to launch balls
    public void spew() {
    	spinnerLeft.set(Math.abs(spinSpeed));
    	spinnerRight.set(Math.abs(spinSpeed));
    }
    
    //tells launcher to pick up balls
    public void suck() {
    	spinnerLeft.set(spinSpeedIn);
    	spinnerRight.set(spinSpeedIn);
    }
    
    //used to make launcher not spin
    public void spinStop() {
    	spinnerLeft.set(0);
    	spinnerRight.set(0);
    }    

}

