package org.usfirst.frc.team2129.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;
import com.ni.vision.VisionException;

import edu.wpi.first.wpilibj.CameraServer;

public class SmartCamera {


	int cameraSession;
	Image frame;
	CameraServer server;
	boolean visionBroken = false;
	NIVision.Rect rect    = new NIVision.Rect(10,10,100,100);
	int framesPerSecond = 20;
	int frameIntervalMs = 1000/framesPerSecond; // Haven't implemented frequency limits yet
		
	public SmartCamera( String cameraName ) {
		
		CameraServer.getInstance().setQuality(50);
		
		try{
			frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	
	        // the camera name (ex "cam0") can be found through the roborio web interface
	        cameraSession = NIVision.IMAQdxOpenCamera( cameraName,
	                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	        NIVision.IMAQdxConfigureGrab(cameraSession);
	        NIVision.IMAQdxStartAcquisition(cameraSession);
		}catch (VisionException vx){
			visionBroken=true;
		}
	}
	
	
	public void stream()
	{
		NIVision.Rect rect    = new NIVision.Rect(10,10,100,100);
		if (!visionBroken){
			try{
				NIVision.IMAQdxGrab(cameraSession, frame, 1);
				NIVision.imaqDrawShapeOnImage(frame,frame,rect,DrawMode.DRAW_VALUE,ShapeMode.SHAPE_OVAL,0.0f);
				CameraServer.getInstance().setImage(frame);
			}catch (VisionException vx){}
    	}
	}

}
