package org.usfirst.frc.team2129.robot.devices;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Rect;
import com.ni.vision.NIVision.ShapeMode;
import com.ni.vision.VisionException;

import edu.wpi.first.wpilibj.CameraServer;

public class SmartCamera {

//	private USBCamera usbCamera;
//	private NIVision.Image frame;

	int session;
	Image frame;
	CameraServer server;
	long counter = 0;
	//Rect rect;
	boolean visionBroken = false;
	NIVision.Rect rect    = new NIVision.Rect(10,10,100,100);

	
	public SmartCamera( String cameraName ) {
		
		try{
			frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	
	        // the camera name (ex "cam0") can be found through the roborio web interface
	        session = NIVision.IMAQdxOpenCamera("cam0",
	                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	        NIVision.IMAQdxConfigureGrab(session);
	        NIVision.IMAQdxStartAcquisition(session);
		}catch (VisionException vx){
			visionBroken=true;
		}

//		usbCamera = new USBCamera( cameraName );
//		frame=NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB,0);
//		
//		// TODO Auto-generated constructor stub
//		CameraServer server = CameraServer.getInstance();
//        server.setQuality(50);
//        server.startAutomaticCapture("cam0");

	}
	
	//private SmartCamera() {}
	
	public void stream()
	{
//		CameraServer  camServ = CameraServer.getInstance();
		NIVision.Rect rect    = new NIVision.Rect(10,10,100,100);
//		usbCamera.getImage(frame);
		NIVision.imaqDrawShapeOnImage(frame,frame,rect,DrawMode.DRAW_VALUE,ShapeMode.SHAPE_OVAL,0.0f);
//		camServ.setImage(frame);
		if (!visionBroken){
			try{
				NIVision.IMAQdxGrab(session, frame, 1);
				NIVision.imaqDrawShapeOnImage(frame,frame,rect,DrawMode.DRAW_VALUE,ShapeMode.SHAPE_OVAL,0.0f);
				CameraServer.getInstance().setImage(frame);
			}catch (VisionException vx){}
    	}

	
	}

}
