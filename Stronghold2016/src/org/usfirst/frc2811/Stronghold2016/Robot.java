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

import  org.usfirst.frc2811.Stronghold2016.commands.AutonomousCommand;
import org.usfirst.frc2811.Stronghold2016.commands.AutonomousCrossLowBar;
import org.usfirst.frc2811.Stronghold2016.commands.AutonomousCrossLowBarAfterDelay;
import org.usfirst.frc2811.Stronghold2016.commands.AutonomousCrossMoat;
import org.usfirst.frc2811.Stronghold2016.commands.AutonomousCrossOther;
import org.usfirst.frc2811.Stronghold2016.commands.AutonomousCrossPortcullus;
import org.usfirst.frc2811.Stronghold2016.commands.AutonomousCrossRockWall;
import org.usfirst.frc2811.Stronghold2016.commands.AutonomousReachOnly;
import org.usfirst.frc2811.Stronghold2016.commands.IntakeJoystickControl;
import 	org.usfirst.frc2811.Stronghold2016.commands.JoystickDrive;
import org.usfirst.frc2811.Stronghold2016.commands.ShooterManual;
import org.usfirst.frc2811.Stronghold2016.subsystems.AltChassis;
import  org.usfirst.frc2811.Stronghold2016.subsystems.Chassis;
import org.usfirst.frc2811.Stronghold2016.subsystems.IntakeLifter;
import 	org.usfirst.frc2811.Stronghold2016.commands.AutonomousCrossLowBar;
import 	org.usfirst.frc2811.Stronghold2016.commands.AutonomousCrossLowBarAfterDelay;
import 	org.usfirst.frc2811.Stronghold2016.commands.AutonomousCrossOther;
import 	org.usfirst.frc2811.Stronghold2016.commands.AutonomousCrossPortcullus;
import 	org.usfirst.frc2811.Stronghold2016.commands.AutonomousReachOnly;
import 	org.usfirst.frc2811.Stronghold2016.commands.JoystickDrive;
import 	org.usfirst.frc2811.Stronghold2016.commands.ShooterManual;
import 	org.usfirst.frc2811.Stronghold2016.subsystems.AltChassis;
import	org.usfirst.frc2811.Stronghold2016.subsystems.IntakeLifter;
import  org.usfirst.frc2811.Stronghold2016.subsystems.Shooter;
import  org.usfirst.frc2811.Stronghold2016.subsystems.Vision;

import 	edu.wpi.first.wpilibj.BuiltInAccelerometer;
import 	edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import  edu.wpi.first.wpilibj.IterativeRobot;
import 	edu.wpi.first.wpilibj.PowerDistributionPanel;
import  edu.wpi.first.wpilibj.command.Command;
import  edu.wpi.first.wpilibj.command.Scheduler;
import  edu.wpi.first.wpilibj.livewindow.LiveWindow;
import 	edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import  edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static int counter = 1;
	
    SendableChooser autonomousOptions;
	Command autonomousCommand;
    Command joystickDrive;
	public static IntakeJoystickControl intakeJoystickControl;
    //Command shooterManual;


    //Command testIntake;
    //public static Command resetAlignment;
    
    //TODO Don't re-enable this until we have a proper oi.getShooterSpeed() function that provides the joystick value
    //Raw joystick reads from commands are insanely hard to troubleshoot
    //Command testShooter;

    public static PowerDistributionPanel powerPanel;
    public static Compressor compressor;
    public static BuiltInAccelerometer onboardAccelerometer;
   
    public static OI oi;
    public static Vision vision;
    public static Shooter shooter;
    
    //public static Chassis chassis;
    public static AltChassis chassis;
	public static IntakeLifter intakeLifter;
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	System.out.println("Start of RobotInit, Statement #" + counter);
    	compressor = new Compressor();
        powerPanel = new PowerDistributionPanel();
        onboardAccelerometer = new BuiltInAccelerometer();
        
        vision = new Vision();
        shooter = new Shooter();

        //TODO Find/Set Chassis rotation PID values
        //chassis = new Chassis();
        chassis = new AltChassis();
        intakeLifter = new IntakeLifter();

        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // instantiate the command used for the autonomous period
        autonomousCommand = new AutonomousCommand(0,0);
        joystickDrive = new JoystickDrive();
        intakeJoystickControl =new IntakeJoystickControl();
        
        //shooterManual = new ShooterManual();
        //testIntake = new TestIntake();

        // instantiate the command used for the autonomous period
        autonomousCommand = new AutonomousCommand(0,0);

        //resetAlignment = new ResetAlignment();
        //testShooter= new TestShooter();
        
        System.out.println("RobotInit End, Statement #" + counter);
        counter++;

        //Initialize optiosn for autonomous selector
        
        autonomousOptions=new SendableChooser();
        autonomousOptions.addObject("Reach Obstacle", new AutonomousReachOnly());
        autonomousOptions.addDefault("Cross Low Bar", new AutonomousCrossLowBar());
        autonomousOptions.addObject("Reach Low Bar After Delay", new AutonomousCrossLowBarAfterDelay());
        autonomousOptions.addObject("Cross Rock Wall", new AutonomousCrossRockWall());
        autonomousOptions.addObject("Cross Moat", new AutonomousCrossMoat());
        autonomousOptions.addObject("Cross Rampart(untested)", new AutonomousCrossOther());
        autonomousOptions.addObject("Cross Portcullus (point lifter toward gate!)", new AutonomousCrossPortcullus());
        SmartDashboard.putData("Select autonomous mode", autonomousOptions);
        
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

        if (intakeJoystickControl != null) intakeJoystickControl.cancel();
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        //System.out.println("Intake encoder Ticks" + intakeLifter.getTicks());
        //System.out.println("Current Angle"+intakeLifter.getCurrentAngle());

    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
    	autonomousCommand= (Command) autonomousOptions.getSelected();
    	
        intakeLifter.resetEncoderPosition();
    	Robot.intakeJoystickControl.cancel();
    	
        if (autonomousCommand != null) autonomousCommand.start();
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();


    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        joystickDrive.start();

        //if(shooterManual !=null){ shooterManual.start();}
        //else {System.out.println("Could not start ShooterManual; Was null");}

        intakeLifter.resetEncoderPosition();
        
        if (intakeJoystickControl != null) intakeJoystickControl.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        SmartDashboard.putData("PDP", powerPanel);
        SmartDashboard.putData("Accelerometer",onboardAccelerometer);
        SmartDashboard.putData("Compressor", compressor);
        
        /*
        SmartDashboard.putData("Gyro", chassis.navxGyro);
        
        
        SmartDashboard.putBoolean("On Target?", chassis.isOnTarget());
        SmartDashboard.putNumber("Z Accel", onboardAccelerometer.getZ());
        SmartDashboard.putBoolean("Is It Flat?", chassis.isRobotStable());
        SmartDashboard.putBoolean("Shifter State", chassis.gearShifter.get());
        
        SmartDashboard.putNumber("Left Write Value", chassis.chassisDrive.leftSide.getSideSetpoint());
        SmartDashboard.putNumber("Left Rate", chassis.chassisDrive.leftSide.getSideEncoderRate());
        SmartDashboard.putNumber("Left Error", chassis.chassisDrive.leftSide.getPIDController().getError());
        SmartDashboard.putNumber("Right Write Value", chassis.chassisDrive.rightSide.getSideSetpoint());
        SmartDashboard.putNumber("Right Rate", chassis.chassisDrive.rightSide.getSideEncoderRate());
        SmartDashboard.putNumber("Right Error", chassis.chassisDrive.rightSide.getPIDController().getError());
        
        
        SmartDashboard.putNumber("Rotation Error", chassis.getError());
        SmartDashboard.putNumber("Rotation Output", chassis.getPIDOutput());
        SmartDashboard.putNumber("Rotation Setpoint", chassis.getSetpoint());
        SmartDashboard.putNumber("Rotation Current Position", chassis.navxGyro.getAngle());
        SmartDashboard.putNumber("NavX Yaw", chassis.navxGyro.getYaw());

		*/




    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
        intakeLifter.readStuff();
    }
}
