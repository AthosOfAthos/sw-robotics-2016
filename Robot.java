
package org.usfirst.frc.team2129.robot;

import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	RobotDrive robo = new RobotDrive(0,1);
	CameraServer server;
	
	Joystick rightJoystick = new Joystick(1);
	Joystick leftJoystick = new Joystick(2);
	
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
        robo.setLeftRightMotorOutputs(leftJoystick.getY(), rightJoystick.getY());
    }
    
    public void testPeriodic() {
    
    }
    
}
