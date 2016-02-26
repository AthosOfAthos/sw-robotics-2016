package org.usfirst.frc.team2129.robot.subsystems;

import org.usfirst.frc.team2129.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterLauncher extends Subsystem {
    
	double spinSpeed = -1;//speed for spinners always be NEGATIVE
	double spinSpeedMod = 0.33;//Mod for sucking on balls

    private final SpeedController spinnerLeft  = RobotMap.shooterLeftWheel;
    private final SpeedController spinnerRight = RobotMap.shooterRightWheel;
    private final SpeedController pusher       = RobotMap.shooterBallProd;
    
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
    	spinnerLeft.set(spinSpeed * spinSpeedMod);
    	spinnerRight.set(spinSpeed * spinSpeedMod);
    }
    
    //used to make launcher not spin
    public void spinStop() {
    	spinnerLeft.set(0);
    	spinnerRight.set(0);
    }    
    public void BallProdExtend()
    {
    	
    }
    
    public void BallProdRetract()
    {
    	
    }
}

