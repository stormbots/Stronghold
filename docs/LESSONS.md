# Lessons Learned


## Lifter system
#### Discussion topics
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


## Shooter systems

#### Discussion Topis
- Linking the Shooter with the Intake 
- Shooting Prossess
- Feed Forward 
	- Why it worked well
	- Why it didn't
- Motor Slaving
- PID
- Encoder
- Disabling forward velocity on PID to improve efficiency

## Chassis

#### Discussion topics
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

##Vision
#### Discussion topics
- Everything is awful! 
	- static IPs are absolutely required on everything
- Brownouts on Raspi cause problems
- field lighting is the worst.
- ideally, do more advanced detection of retroreflective based on flashing LEDs and comparing frames
- Make sure tools can rotate camera without incurring delays and problems BEFORE creating fixture to rotate camera
- always update the tools 

## Robot Behaviours