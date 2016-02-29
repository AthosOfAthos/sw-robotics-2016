// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc.team2129.robot;

import edu.wpi.first.wpilibj.CameraServer;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController treadLeft;
    public static SpeedController treadRight;
    public static RobotDrive tankDriveRobotDrive;
    public static SpeedController spinnerLeft;
    public static SpeedController spinnerRight;
    public static SpeedController pusher;
    public static SpeedController aimer;
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public static SpeedController aimerBoost;
    
	public static CameraServer server;
	
    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        treadLeft = new Talon(0);
        LiveWindow.addActuator("TankDrive", "LeftDriveController", (Talon) treadLeft);
        
        treadRight = new Talon(1);
        LiveWindow.addActuator("TankDrive", "RightDriveController", (Talon) treadRight);
        
        tankDriveRobotDrive = new RobotDrive(treadLeft, treadRight);
        
        tankDriveRobotDrive.setSafetyEnabled(true);
        tankDriveRobotDrive.setExpiration(0.1);
        tankDriveRobotDrive.setSensitivity(0.05);
        tankDriveRobotDrive.setMaxOutput(0.75);

        tankDriveRobotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        spinnerLeft = new Talon(4);
        LiveWindow.addActuator("Shooter", "LeftWheel", (Talon) spinnerLeft);
        
        spinnerRight = new Talon(5);
        LiveWindow.addActuator("Shooter", "RightWheel", (Talon) spinnerRight);
        
        pusher = new Talon(6);
        LiveWindow.addActuator("Shooter", "BallPusher", (Talon) pusher);
        
        aimer = new Talon(2);
        LiveWindow.addActuator("Shooter", "Elevation", (Talon) aimer);
        

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        aimerBoost = new Talon(3);
        
        server = CameraServer.getInstance();
        server.setQuality(50);
        //server.startAutomaticCapture("cam0");
        
        // Arm not implemented in hardware yet
    	//Talon arm = new Talon(7);
    }
}
