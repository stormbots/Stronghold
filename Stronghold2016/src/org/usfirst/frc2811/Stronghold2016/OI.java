// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2811.Stronghold2016;

import org.usfirst.frc2811.Stronghold2016.commands.IntakeBall;
import org.usfirst.frc2811.Stronghold2016.commands.IntakeDown;
import org.usfirst.frc2811.Stronghold2016.commands.IntakeHoming;
import org.usfirst.frc2811.Stronghold2016.commands.IntakeLifterOff;
import org.usfirst.frc2811.Stronghold2016.commands.IntakeOff;
import org.usfirst.frc2811.Stronghold2016.commands.IntakeToggleAngle;
import org.usfirst.frc2811.Stronghold2016.commands.IntakeUp;
import org.usfirst.frc2811.Stronghold2016.commands.SetAngle;
import org.usfirst.frc2811.Stronghold2016.commands.ShiftGears;
import org.usfirst.frc2811.Stronghold2016.commands.ShootBall;
import org.usfirst.frc2811.Stronghold2016.commands.SpitBall;
import org.usfirst.frc2811.Stronghold2016.commands.TestShooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;



/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 *  <br>
 *  Press F2, click popup, or view OI to see a diagram of the controller
 *  <pre>
 *           _____                    _____           
 *           |  7  |                  |  8  |         
 *           |_____|                  |_____|          
 *           |__5__|                  |__6__|         
 *         __________                __________       
 *        |  / | \   \______________/          |       
 *        | |--+--|                       4    |      
 *        |  \ | /      9       10      1   3  |      
 *        |  POV 0                        2    |      
 *       /      ___                    ___      \     
 *      /      / 3 \                  / 1 \      \    
 *     |      |4 + 4|________________|2 + 2|      |   
 *     |       \_3_/                  \_1_/       |   
 *     |       /                          \       |   
 *      \_____/                            \_____/    
 *</pre>
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public Joystick leftStick;
    public JoystickButton leftTrigger;
    
    public Joystick rightStick;
    public JoystickButton rightTrigger;
    
    public Joystick xBox;
    public JoystickButton x2;
    public JoystickButton x6;
    
    public Joystick gamePad;
    public JoystickButton gamePadButton1;
    public JoystickButton gamePadButton2;
    public JoystickButton gamePadButton4;
    public JoystickButton gamePadButton5;
    public JoystickButton gamePadButton6;
    public JoystickButton gamePadButton8;

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    	
////////TWO STICK    	
    	leftStick = new Joystick(0);
    	
    	leftTrigger = new JoystickButton(leftStick,1);
    	leftTrigger.whenPressed(new ShiftGears());
    	
    	rightStick = new Joystick(1);
    	
    	rightTrigger = new JoystickButton(rightStick,1);
    	rightTrigger.whenPressed(new SetAngle(30));
    	
    	
////////XBOX    	
    	xBox = new Joystick(2);
    	
    	x2 = new JoystickButton(xBox,2);
    	x2.whenPressed(new ShiftGears());
    	
    	x6 = new JoystickButton(xBox,6);
    	x6.whenPressed(new SetAngle(30));

////////GAMEPAD    	
    	gamePad = new Joystick(3);
    	
    	gamePadButton1 = new JoystickButton(gamePad,1);
    	gamePadButton1.whenPressed(new IntakeToggleAngle());
    	
    	gamePadButton2 = new JoystickButton(gamePad,2);
    	gamePadButton2.whileHeld(new IntakeDown() );
    	gamePadButton2.whenReleased(new IntakeLifterOff());

    	//gamePadButton2.whenPressed(new TogglePokingThing());
    	gamePadButton4 = new JoystickButton(gamePad,4);
    	gamePadButton4.whileHeld(new IntakeUp() );
    	gamePadButton4.whenReleased(new IntakeLifterOff());

    	gamePadButton5 = new JoystickButton(gamePad,5);
    	gamePadButton5.whenPressed(new ShootBall());
        
    	gamePadButton6 = new JoystickButton(gamePad, 6);
    	gamePadButton6.whileHeld(new SpitBall());
    	gamePadButton6.whenReleased(new IntakeOff());
    	
    	gamePadButton8 = new JoystickButton(gamePad, 8);
        gamePadButton8.whileHeld(new IntakeBall());
    	gamePadButton8.whenReleased(new IntakeOff());
            
        



        // SmartDashboard Buttons

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getGamePad() {
        return gamePad;
    }

    public double triggerMath(){
    	return Robot.oi.xBox.getRawAxis(2)-Robot.oi.xBox.getRawAxis(3);
    }
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

