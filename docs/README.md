# StrongHold Robot Explanation
Stormbots robot for the 2016 subsystem

## Major Subsystems

### Lifter 
This is a PID based system controlled by a Talon SRX, mag encoder

![caption](images/filename.jpg)

[text to display](../StrongHold2016/src/org/usfirst/frc2811/StrongHold2016/subsystem/IntakeLifter.java)

It is in the subsystem of the IntakeLifter. This used a hand written PID to control the positon of the intake
using current to control.Originally, we were going to use a PID position using the FRC PID for position, but 
at the first day of the competition, so we scraped the original idea of the FRC library PID, so it was a last
put together. Also, there was an advantage of using a dual pid control. One of the reasons we did that was so
we could limit the current output on the motors, and this made the positional with only a PI controller.

Our 

- why dual pid? - current limiting on controller, and positional control with simple PI controller
- where's our intake sensor?
- homing process / auto-correcting belt skips
- ideally move encoder to directly read output shaft 
	- belt skip doesn't matter
	- because it's clearly better
	- Less sensitive to gearbox changes
- belt feed and forward intake autostop
	- why we didn't use it
	- why it worked awesome

 
### Shooter


- Linking the Shooter with the Intake 
- Shooting Prossess
- Feed Forward 
	- Why it worked well
	- Why it didn't
- Motor Slaving
- PID
- Encoder
-

### Chassis

- PID was awesome, kinda
	- need time to tune properly
	- needs filtered output; Oscillations burn out motors, gearboxes
	- feed forward is necessary for responsiveness
	- worked really well for drivers and consistent driving
	- Those encoders are just, the worst.
	- Gyro was cool, and helpful when it worked
		- issues with turning the correct direction
	- brownouts are awful, and worse when PID control/gearing is capable of causing it
		- auto-shifting + current detection kinda helps
	
### Vision 
- Everything is awful! 
	- static IPs are absolutely required on everything
- Brownouts on Raspi cause problems
- field lighting is the worst.
- ideally, do more advanced detection of retroreflective based on flashing LEDs and comparing frames
- Make sure tools can rotate camera without incurring delays and problems BEFORE creating fixture to rotate camera
- always update the tools 






_italics_
*bold*


# Biggest Header

## Smaller Header

### Even smaller! :D 


Make a list
- item 1
- item 2
- item 3
	- sub item
-item 4



``` java
this is all code in the brackets
```

this is how you write a `snippet of code` and stuff

