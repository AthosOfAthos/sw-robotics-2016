package org.usfirst.frc.team2129.robot;

import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	//important camera thing
	CameraServer server;
	//SmartCamera smartCam;
	
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
	//nope
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
	int cycles = 20;
	double pushOut = .3; //this is the wiggly
	double pushIn = -.3;
	
	//threshold config
	double threshold = .05;
	
	//config for speeds
	//NEGATIVE IS UP, POSITIVE IS DOWN!
	double spinSpeed = -.75;//speed for spinners always be NEGATIVE
	double spinSpeedMod = 0.33;//Mod for sucking up balls
	double aimSpeedUp = -.35;//speed for aiming spinners up NEATIVE
	double aimSpeedDown = .15;//speed for aiming spinners down POSITIVE
	double aimSpeedDownPower = .35;//speed for arms when you need a boost POSITIVE
	double aimSpeedStop = -0.20;//don't fall down NEGATIVE
	double armSpeed = -.3;//speed for the arm must be NEGATIVE
	
	//config for movement
	double treadMod = 1;
	double treadMax = .75;//limit for how fast the treads will go
	double treadMin = -.75;//same for other side
	
	//variables for autonomous
	boolean doesItMove = true;
	
	//POV shit
	int pov = -1;
	
    public void robotInit() {
    	//all stuff for the webcam
    	server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam0");
//    	smartCam = new SmartCamera( "cam0" );
    	
        //invert
        spinerLeft.setInverted(true);
    }
    
    public void autonomousInit() {
    	//put something here
    }

    public void autonomousPeriodic() {
    	//put something else here

    	//this is bad 
    	// TRU
    	if (doesItMove) {
    		//uses simple move to not get ANY controller input
    		simpleMove(-0.25, 0.25);
    		Timer.delay(2);
    		simpleMove(0,0);
    		doesItMove = false;
    	}

    	// Camera processing
    	//smartCam.stream();

    }

    public void teleopPeriodic() {

    	// Camera processing
    	//smartCam.stream();
    	
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
    	
    	//controls spinner part of launcher
    	if (rightJoystick.getRawButton(1)) {
    		spinSpew();
    	} else if (leftJoystick.getRawButton(1)) {
    		spinSuck();
    	} else {
    		spinStop();
    	}
    	
    	//aims the ball launcher
    	pov = rightJoystick.getPOV();
    	if (pov == 0) {//controls aim of launcher
    		aimerMove(aimSpeedUp);
    	} else if (pov == 180) {
    		aimerMove(aimSpeedDown);
    	} else if (pov == 90) {
    		aimerMove(aimSpeedDownPower);
    	} else {
    		if (leftJoystick.getRawButton(5)) {
    			aimerMove(aimSpeedStop);
    		} else {
    			aimerMove(0);
    		}
    	}
    	
    	//the arm, do you know what it does?
    	// it actually doesn't exist :o)
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
    	if (rightJoystick.getRawButton(2) && !pushing) {
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
    	//treadMod Thingy
    	treadMod = rightJoystick.getThrottle() + 1;
    	treadMod *= 0.5;
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
    
    //just sets the motor values, simple as that
    public void simpleMove(double left, double right) {
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
    	spinerLeft.set(spinSpeed * spinSpeedMod);
    	spinerRight.set(spinSpeed * spinSpeedMod);
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
