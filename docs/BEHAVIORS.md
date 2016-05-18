# Robot Behaviors

## Auto Modes
In Auto mode, our robot can cross nearly all obstacles, and has performed them during competition. The only obstacles we could not handle were the Sally Port and Drawbridge. 

In addition, our robot is capable of crossing the obstacle, and then taking a shot at the high goal using computer vision for aiming. 

We also have a team-forward approach for our auto modes. Rather than move immediately, our auto modes have a delayed start, upwards of 10 seconds. This reduces the chance of bumping into friendly robots while they're taking a shot at the goal. This served us well in competitions, ensuring that friendly robots get maximum points, regardless of field orientation.

## Driver Assist Modes
In order to minimize the amount the human players have to handle, the robot is designed to have sensor inputs help enable or disable systems.

Some of these were disabled after testing due to various factors, such as foul potential, game play interference, or simple mechanical issues. 

#### Automatic Intake Shutoff
Optimal intake is very fast, but that can present unnecessary wear on the ball, or cause jams. A small switch is present to help aid in automatically cutting power to the intake when a ball is successfully obtained. This was removed, as sensor positioning resulted in lost boulders during certain obstacle crossings. 

#### Joystick Integration
For maximum control and accuracy during lifter operation, a joystick paddle directly controls arm position. This means the driver simply sets the stick at the desired angle, and the robot copies this to the lifter. This allows for very simple and precise movements on the field. 
 [See main control code here][lifterjoystick]

#### Ball Pickup Sequences
In addition to manual control, there's an option for a quick [ball aquisition sequence][grabball] that should fully intake a ball given the system mechanics. 

#### Automatic re-homing
Due to mechanics, it's possible for the lifter arm to skip. When the lifter position is "up", the robot [re-homes itself][lifterjoystick], ensuring that it's always operating as the driver expects.  [][lifterjoystick]

#### Automatic Ball Positioning
For maximum accuracy, the shooter requires a very consisted "feed in" speed. Due to the speed of the intake resulting in unreliable end positions, the robot goes through a [fancy sequence][shotseq] to position the ball accurately before attempting a shot. 

#### Automatic Shift
By doing current detection, we were able to automatically shift between high and low speeds. This was removed, as low speed resulted in several other non-ideal behaviors and brown-out scenarios. 

#### Target Aquisition
Using our vision system, we are able to [automatically focus][visionturn] on a target during our shot sequence. 

#### One click re-orientation
Using the NavX, we were able to implement a button that [aligned the robot to the field][setrotation]. This allowed for very quick and accurate alignment for obstacle crossing, or for automatic re-alignment during auto modes. 

#### PID Controlled Drive System
To enhance driver control over obstacles, we used a PID control loop to control the drive train. This would enable the driver to directly control the robot velocity, rather than the motor power. 

During our testing, this resulted in a very smooth driving experience. Even when the robot was commanded to drive at 1/4 speed, it would accurately cross obstacles, automatically increasing power as needed. This was an improvement over the open-loop system, which required the driver to explicitly focus on increasing drive train power at these times. 

The code was removed during competition, due to several drawbacks relating to insufficient time to tune the PID loop to the robot. The PID control also would exacerbate certain edge-cases encountered during field operation. 

#### Chassis Failure compensation
Mechanical wear eventually slows down the robot, and generally one side is more affected than the other. With open-loop drive systems, this results in the robot driving in an arc. This can seriously affect driver performance. 

As a result, we implemented a last-ditch [failure correction][chassisfailurecompchassisfailurecomp] in our chassis. This code determined from the joystick input and encoders if the robot was turning incorrectly, and would slow down one side to compensate. This resulted in the robot moving slower, but in the correct direction. 

During testing, this even kept the robot driving accurately with the loss of a drive motor, or if a gearbox jammed in the wrong gear.

This code wasn't used in the final code due to one encoder working intermittently. When the encoder failed to provide valid values, the error correction would result in the robot stopping entirely.

[shotseq]: ../Stronghold2016/src/org/usfirst/frc2811/Stronghold2016/commands/ShooterSequenceForAlignmentLine.java
[visionturn]: ../Stronghold2016/src/org/usfirst/frc2811/Stronghold2016/commands/VisionTurnToTarget.java
[grabball]: ../Stronghold2016/src/org/usfirst/frc2811/Stronghold2016/commands/IntakeBall.java
[lifterjoystick]:../Stronghold2016/src/org/usfirst/frc2811/Stronghold2016/commands/IntakeJoystickControl.java
[chassisfailurecomp]: ../Stronghold2016/src/org/usfirst/frc2811/Stronghold2016/subsystems/AltChassis.java#L64
[setrotation]: ../Stronghold2016/src/org/usfirst/frc2811/Stronghold2016/subsystems/Chassis.java#L112]