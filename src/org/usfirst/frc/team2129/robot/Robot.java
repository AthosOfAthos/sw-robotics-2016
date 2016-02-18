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
	RobotDrive robo = new RobotDrive(0,1);
	
	//motor controller setup for ball launcher
	Talon spinerLeft = new Talon(2);
	Talon spinerRight = new Talon(3);
	Talon aimer = new Talon(4);
	
	//arm that does something?
	Talon arm = new Talon(5);
	
	//booleans bc return of function does ?? int/bool ??
	boolean suck = false;
	boolean eject = false;
	
	//joysticks are important
	Joystick leftJoystick = new Joystick(0);
	Joystick rightJoystick = new Joystick(1);
	
	//booleans for reversal
	boolean reverse = false;
	boolean push = false;
	
	//threshold config
	double threshold = .10;
	
	//config for speeds
	double spinSpeed = -1;//speed for spinners always be NEGATIVE
	double aimSpeed = -.5;//speed for aiming spinners must be NEGATIVE
	double armSpeed = -.5;//speed for the arm must be NEGATIVE
	
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
    }

    public void teleopPeriodic() {
    	//basic tank controls
    	//note to self improve later
    	if (reverse) {//inverted controls
    		if (Math.abs(leftJoystick.getY()) > threshold || Math.abs(rightJoystick.getY()) > threshold) {
    			robo.setLeftRightMotorOutputs(rightJoystick.getY() * -1, leftJoystick.getY() * -1);
    		} else {
    			robo.setLeftRightMotorOutputs(0, 0);
    		}
    	} else {//normal controls
    		if (Math.abs(leftJoystick.getY()) > threshold || Math.abs(rightJoystick.getY()) > threshold) {
    			robo.setLeftRightMotorOutputs(leftJoystick.getY(), rightJoystick.getY());
    		} else {
    			robo.setLeftRightMotorOutputs(0, 0);
    		}
    	}
    	
    	//Inverter
    	if (leftJoystick.getRawButton(3) && !push) {
    		push = true;
    		if (reverse) {
    			reverse = false;
    		} else {
    			reverse = true;
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
    	aimer.set(Math.abs(aimSpeed));
    }
    
    //aims the launcher down
    public void aimDown() {
    	aimer.set(aimSpeed);
    }
    
    //tells the launcher to stop moving
    public void aimStop() {
    	aimer.set(0);
    }
    
    public void testPeriodic() {
    	//might be important for testing?
    }
    
}
