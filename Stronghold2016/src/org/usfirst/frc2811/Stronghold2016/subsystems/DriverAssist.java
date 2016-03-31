package org.usfirst.frc2811.Stronghold2016.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.FlipAxis;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;

public class DriverAssist {

	int session;
	boolean killSwitch = false;
    Image frame;
    //Image frame2;
	
	public DriverAssist() {
        //frame2 = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        //frame2 = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        // the camera name (ex "cam0") can be found through the roborio web interface
        
		try { 
			frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
			session = NIVision.IMAQdxOpenCamera("cam0",
	                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	        NIVision.IMAQdxConfigureGrab(session);
		} catch (Exception e) {
			killSwitch=true;
			System.err.println("Cannot initialize camera stream! Driver Assist disabled until next robotInit.");
		}
	}
	
	public void periodic() {
		if (killSwitch) {
			return;
		}
		
		NIVision.IMAQdxStartAcquisition(session);

        /**
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */
        NIVision.Rect rect = new NIVision.Rect(255, 230, 55, 55); // front wheels on ramp
        NIVision.Rect rect1_1 = new NIVision.Rect(254, 229, 57, 57);
        NIVision.Rect rect1_2 = new NIVision.Rect(253, 228, 59, 59);
        
        NIVision.Rect rect2 = new NIVision.Rect(379, 232, 45, 45); // ~11' from roughly DS wall
        NIVision.Rect rect2_1 = new NIVision.Rect(378, 231, 47, 47);
        NIVision.Rect rect2_2 = new NIVision.Rect(377, 230, 49, 49);
        
        NIVision.Rect rect3 = new NIVision.Rect(490, 210, 50, 50); // ~17'6" from roughly DS wall
        NIVision.Rect rect3_1 = new NIVision.Rect(489, 209, 52, 52);
        NIVision.Rect rect3_2 = new NIVision.Rect(488, 208, 54, 54);

        NIVision.IMAQdxGrab(session, frame, 1);
        
        NIVision.imaqTranspose(frame, frame);
        NIVision.imaqFlip(frame, frame, FlipAxis.HORIZONTAL_AXIS);
        
        NIVision.imaqDrawShapeOnImage(frame, frame, rect,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 5000f);
        NIVision.imaqDrawShapeOnImage(frame, frame, rect1_1,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 5000f);
        NIVision.imaqDrawShapeOnImage(frame, frame, rect1_2,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 5000f);
        
        NIVision.imaqDrawShapeOnImage(frame, frame, rect2,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 5000f);
        NIVision.imaqDrawShapeOnImage(frame, frame, rect2_1,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 5000f);
        NIVision.imaqDrawShapeOnImage(frame, frame, rect2_2,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 5000f);
        
        NIVision.imaqDrawShapeOnImage(frame, frame, rect3,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 5000f);
        NIVision.imaqDrawShapeOnImage(frame, frame, rect3_1,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 5000f);
        NIVision.imaqDrawShapeOnImage(frame, frame, rect3_2,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 5000f);
        
        CameraServer.getInstance().setImage(frame);

        /** robot code here! **/
        //Timer.delay(0.005);		// wait for a motor update time
        NIVision.IMAQdxStopAcquisition(session);
	}
	
}
