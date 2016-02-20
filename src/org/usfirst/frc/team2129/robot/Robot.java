package org.usfirst.frc.team2129.robot;

import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	//important camera thing
	CameraServer server;
	
	//ports 0 and 1 are for the treads
	//RobotDrive robo = new RobotDrive(0,1);
	Talon treadLeft = new Talon(0);
	Talon treadRight = new Talon(1);
	
	//motor controller setup for ball launcher
	Talon spinerLeft = new Talon(5);
	Talon spinerRight = new Talon(4);
	Talon aimer = new Talon(2);//Both aim the arm
	Talon aimerBoost = new Talon(3);
	
	//arm that does something?
	Talon arm = new Talon(6);
	
	//booleans bc return of getRawButton function does ?? int/bool ??
	boolean suck = false;
	boolean eject = false;
	boolean up = false;
	boolean down = false;
	
	//joysticks are important
	Joystick leftJoystick = new Joystick(0);
	Joystick rightJoystick = new Joystick(1);
	
	//booleans for reversal
	boolean reverse = false;
	boolean push = false;
	
	//threshold config
	double threshold = .05;
	
	//config for speeds
	double spinSpeed = -1;//speed for spinners always be NEGATIVE
	double aimSpeedUp = -.40;//speed for aiming spinners up NEATIVE
	double aimSpeedDown = .15;//speed for aiming spinners down POSITIVE
	double armSpeed = -.3;//speed for the arm must be NEGATIVE
	
	//config for movement
	double treadMod = 1;//modifier for how fast the treads go
	double treadMax = .75;//limit for how fast the treads will go
	double treadMin = -.75;//same for other side
	
    public void robotInit() {
    	//all stuff for the webcam
    	server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam0");
    }
    
    public void autonomousInit() {
    	//put something here
    }

    public void autonomousPeriodic() {
    	//put something else here
    	//this is bad
    	move(.25,.25);
    }

    public void teleopPeriodic() {
    	//basic tank controls
    	//note to self improve later
    	if (reverse) {//inverted controls
    		if (Math.abs(leftJoystick.getY()) > threshold || Math.abs(rightJoystick.getY()) > threshold) {
    			move(rightJoystick.getY() * -1, leftJoystick.getY());
    		} else {
    			move(0, 0);
    		}
    	} else {//normal controls
    		if (Math.abs(leftJoystick.getY()) > threshold || Math.abs(rightJoystick.getY()) > threshold) {
    			move(leftJoystick.getY(), rightJoystick.getY() * -1);
    		} else {
    			move(0, 0);
    		}
    	}
    	
    	//Inverter
    	if (leftJoystick.getRawButton(3)) {
    		if (!push) {
    			push = true;
    			if (reverse) {
    				reverse = false;
    			} else {
    				reverse = true;
    			}
    		}
    	} else {
    		push = false;
    	}
    	
    	//either returns a boolean or integer not sure?
    	eject = rightJoystick.getRawButton(1);
    	suck = leftJoystick.getRawButton(1);
    	
    	//controls spinner party of launcher
    	if (eject) {
    		spinSpew();
    	} else if (suck) {
    		spinSuck();
    	} else {
    		spinStop();
    	}
    	
    	//if something doesent work its probaly this
    	if (rightJoystick.getRawButton(6)) {//controls aim of launcher
    		aimUp();
    	} else if (rightJoystick.getRawButton(4)) {
    		aimDown();
    	} else {
    		aimStop();
    	}
    	
    	//the arm, do you know what it does?
    	up = rightJoystick.getRawButton(5);
    	down = rightJoystick.getRawButton(3);
    	if (up) {
    		//move arm up
    		arm.set(Math.abs(armSpeed));
    	} else if (down) {
    		//move arm down
    		arm.set(armSpeed);
    	} else {
    		//stop the arm
    		arm.set(0);
    	}
    }
    
    //Sets the treads to a speed
    public void move(double left, double right) {
    	//speed mod
    	left *= treadMod;
    	right *= treadMod;
    	//Max speed
    	if (Math.abs(left) > treadMax) {
    		if (left > 0) {
    			left = treadMax;
    		} else {
    			left = treadMin;
    		}
    	}
    	if (Math.abs(right) > treadMax) {
    		if (right > 0) {
    			right = treadMax;
    		} else {
    			right = treadMin;
    		}
    	}
    	//set motors
    	treadLeft.set(left);
    	treadRight.set(right);
    }
    
    //tells launcher to launch balls
    public void spinSpew() {
    	spinerLeft.set(Math.abs(spinSpeed));
    	spinerRight.set(Math.abs(spinSpeed));
    }
    
    //tells launcher to pick up balls
    public void spinSuck() {
    	spinerLeft.set(spinSpeed);
    	spinerRight.set(spinSpeed);
    }
    
    //used to make launcher not spin
    public void spinStop() {
    	spinerLeft.set(0);
    	spinerRight.set(0);
    }
    
    //aims the launcher up
    public void aimUp() {
    	aimer.set(aimSpeedUp);
    	aimerBoost.set(aimSpeedUp);
    }
    
    //aims the launcher down
    public void aimDown() {
    	aimer.set(aimSpeedDown);
    	aimerBoost.set(aimSpeedDown);
    }
    
    //tells the launcher to stop moving
    public void aimStop() {
    	aimer.set(0);
    	aimerBoost.set(0 );
    }
    
    public void testPeriodic() {
    	//might be important for testing?
    }
    
}
