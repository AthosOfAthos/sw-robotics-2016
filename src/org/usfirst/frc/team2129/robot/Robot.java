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
	
	CameraServer server;
	
	RobotDrive robo = new RobotDrive(0,1);
	
	Talon spinerLeft = new Talon(2);
	Talon spinerRight = new Talon(3);
	Talon aimer = new Talon(4);
	
	boolean suck = false;
	boolean eject = false;
	
	Joystick leftJoystick = new Joystick(0);
	Joystick rightJoystick = new Joystick(1);
	
	//Config
	double spinSpeed = -1;//speed for spinners always be NEGATIVE
	double aimSpeed = -.5;//speed for aiming spinners must be NEGATIVE
	
    public void robotInit() {
    	server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam0");
    }
    
    public void autonomousInit() {
    	
    }

    public void autonomousPeriodic() {
    	
    }

    public void teleopPeriodic() {
    	if (Math.abs(leftJoystick.getY()) > .05 || Math.abs(rightJoystick.getY()) > .05) {
    		robo.setLeftRightMotorOutputs(leftJoystick.getY(), rightJoystick.getY());
    	} else {
    		robo.setLeftRightMotorOutputs(0, 0);
    	}
    	
    	eject = rightJoystick.getRawButton(1);//either returns a boolean or integer
    	suck = leftJoystick.getRawButton(1);
    	
    	if (eject) {//controls spinner party of launcher
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
    	
    }
    
    public void spinSpew() {
    	spinerLeft.set(Math.abs(spinSpeed));
    	spinerRight.set(Math.abs(spinSpeed));
    }
    
    public void spinSuck() {
    	spinerLeft.set(spinSpeed);
    	spinerRight.set(spinSpeed);
    }
    
    public void spinStop() {
    	spinerLeft.set(0);
    	spinerRight.set(0);
    }
    
    public void aimUp() {
    	aimer.set(Math.abs(aimSpeed));
    }
    
    public void aimDown() {
    	aimer.set(aimSpeed);
    }
    
    public void aimStop() {
    	aimer.set(0);
    }
    
    public void testPeriodic() {
    
    }
    
}
