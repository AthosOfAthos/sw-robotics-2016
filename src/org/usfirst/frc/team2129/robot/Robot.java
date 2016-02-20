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
	Talon spinerRight = new Talon(5);
	Talon spinerLeft = new Talon(4);
	Talon aimer = new Talon(2);//Both aim the arm
	Talon aimerBoost = new Talon(3);
	Talon pusher = new Talon(6);//pushes the ball out
	
	//arm that does something?
	Talon arm = new Talon(7);
	
	//joysticks are important
	Joystick leftJoystick = new Joystick(0);
	Joystick rightJoystick = new Joystick(1);
	
	//booleans for reversal
	boolean reverse = false;
	boolean push = false;
	
	//variables for launcher
	boolean pushing = false;
	boolean goingOut = false;
	int cyclesCompleted = 0;
	
	//config for ball pusher
	int cycles = 10;
	double pushOut = .3;
	double pushIn = -.3;
	
	//threshold config
	double threshold = .05;
	
	//config for speeds
	double spinSpeed = -0.75;//speed for spinners always be NEGATIVE
	double aimSpeedUp = -.45;//speed for aiming spinners up NEATIVE
	double aimSpeedDown = .15;//speed for aiming spinners down POSITIVE
	double aimSpeedDownPower = .35;//speed for arms when you need a boost POSITIVE
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
        
        //invert
        spinerLeft.setInverted(true);
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
    			move(rightJoystick.getY(), leftJoystick.getY());
    		} else {
    			move(0, 0);
    		}
    	} else {//normal controls
    		if (Math.abs(leftJoystick.getY()) > threshold || Math.abs(rightJoystick.getY()) > threshold) {
    			move(leftJoystick.getY() , rightJoystick.getY());
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
    	
    	//controls spinner party of launcher
    	if (rightJoystick.getRawButton(1)) {
    		spinSpew();
    	} else if (leftJoystick.getRawButton(1)) {
    		spinSuck();
    	} else {
    		spinStop();
    	}
    	
    	//aims the ball launcher
    	if (rightJoystick.getRawButton(6)) {//controls aim of launcher
    		aimerMove(aimSpeedUp);
    	} else if (rightJoystick.getRawButton(4)) {
    		aimerMove(aimSpeedDown);
    	} else if (rightJoystick.getRawButton(2)) {
    		aimerMove(aimSpeedDownPower);
    	} else {
    		aimerMove(0);
    	}
    	
    	//the arm, do you know what it does?
    	if (rightJoystick.getRawButton(5)) {
    		//move arm up
    		arm.set(Math.abs(armSpeed));
    	} else if (rightJoystick.getRawButton(3)) {
    		//move arm down
    		arm.set(armSpeed);
    	} else {
    		//stop the arm
    		arm.set(0);
    	}
    	
    	//push the ball out
    	//starts the push
    	if (leftJoystick.getRawButton(2) && !pushing) {
    		pushing = true;
    		goingOut = true;
    		cyclesCompleted = 0;
    	}
    	if (pushing) {
    		//handles time
    		if (cyclesCompleted >= cycles) {
    			if (goingOut) {
    				goingOut = false;
    				cyclesCompleted = 0;
    			} else {
    				pushing = false;
    			}
    		}
    		//tells pusher to go in/out
    		if (goingOut) {
    			pusher.set(pushOut);
    			cyclesCompleted += 1;
    		} else {
    			pusher.set(pushIn);
    			cyclesCompleted += 1;
    		}
    	} else {
    		//stops the pusher
    		pusher.set(0);
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
    	spinerLeft.set(spinSpeed * .5);
    	spinerRight.set(spinSpeed * .5);
    }
    
    //used to make launcher not spin
    public void spinStop() {
    	spinerLeft.set(0);
    	spinerRight.set(0);
    }
    
    //makes aimer move
    public void aimerMove(double aimingSpeed) {
    	aimer.set(aimingSpeed);
    	aimerBoost.set(aimingSpeed);
    }
    
    public void testPeriodic() {
    	//might be important for testing?
    }
    
}
