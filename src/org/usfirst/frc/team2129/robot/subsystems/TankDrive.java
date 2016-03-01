// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc.team2129.robot.subsystems;

import org.usfirst.frc.team2129.robot.RobotMap;
import org.usfirst.frc.team2129.robot.commands.AutonomousCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

// Steve questions:
// - Maybe the default tank drive is sufficient?
// 	- Is the track inversion function through the left button 3 still needed?
// 	- Is the tread modifier with the Right throttle still needed?

/**
 *
 */
public class TankDrive extends Subsystem {

	//config for movement
	
//	double treadMod = 1;
	// These are set in RobotMap now
//	double treadMax = .75;//limit for how fast the treads will go
//	double treadMin = -.75;//same for other side
	
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController leftDriveController = RobotMap.treadLeft;
    private final SpeedController rightDriveController = RobotMap.treadRight;
    private final RobotDrive robotDrive = RobotMap.tankDriveRobotDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new AutonomousCommand());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
	public void drive(Joystick left, Joystick right) {
		robotDrive.tankDrive(left, right);
	}
	
	public void drive( double left, double right )
	{
		robotDrive.tankDrive(left, right);
	}
	
	public void stop() {
		robotDrive.tankDrive( 0, 0 );
	}
}

