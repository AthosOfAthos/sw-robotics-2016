package org.usfirst.frc.team2129.robot.devices;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * RightPOVStateCheckTrigger
 * 
 * Implements the get() function to check whether a joystick's POV switch is at a given value.  
 * The purpose of this class is so that a command can be triggered from the POV being put in that state.   
 */
public class JoystickPOVStateCheckTrigger extends Trigger {
    
	private final int stateCheckValue;
	private final Joystick joystick;
	
	public JoystickPOVStateCheckTrigger( Joystick newJoystick, int newStateCheckValue )
	{
		stateCheckValue = newStateCheckValue;
		joystick = newJoystick;
	}
	
    public boolean get() {
    	return joystick.getPOV() == stateCheckValue;
    }
}
