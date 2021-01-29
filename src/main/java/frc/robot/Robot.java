/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public DifferentialDrive RobotDT;
  public Joystick JoyController;
  public XboxController XController;
  public Spark intakeSpark;
  private double x, y, z;
  private double xscale, yscale, zscale;
  private boolean val;



  NetworkTableEntry tankDriveOn;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //RobotDT = new DriveTrain();
    RobotDT = new DifferentialDrive(new Spark(1), new Spark(0));
    XController = new XboxController(0);
    JoyController = new Joystick(1);
    intakeSpark = new Spark(2);
    intakeSpark.enableDeadbandElimination(true);

    val = false;
    xscale = 0.7;
    yscale = 0.7;
    zscale = 0.65;

    tankDriveOn = Shuffleboard.getTab("MyTab").add("TankDrive", false).withWidget("Toggle Button").getEntry();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  @Override
  public void teleopInit() {
    
  }

  @Override 
  public void disabledInit() {
    super.disabledInit();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // x controls left / right movement of the robot
    // y controls forward / backward movement of the robot

    //tankDriveOn.getBoolean(false);
    //if (!tankDriveOn.getBoolean(false)) {
      if (val){
        //Xbox Controller GTA drive
      z = XController.getX(Hand.kLeft);
      y = (XController.getTriggerAxis(Hand.kRight) - XController.getTriggerAxis(Hand.kLeft));
  
      RobotDT.arcadeDrive(y*yscale , z*zscale);
    }
    else
    {

      y = JoyController.getY();
      z = JoyController.getZ();
      RobotDT.arcadeDrive(y*yscale, z*zscale);

      // Xbox Controller "Tank" Drive
      /*x = XController.getY(Hand.kLeft) * 0.7;
      y = XController.getY(Hand.kRight) * 0.7;
      // Negative needed to 
      RobotDT.tankDrive(-x*xscale, -y*yscale);*/
      // XboxController standard arcade drive - need to edit this?
      /*z = XController.getX(Hand.kLeft);
      y = XController.getY(Hand.kRight);
      RobotDT.arcadeDrive(-y*yscale,z*zscale);*/
    }
      
    // This code will assign a button to make motor go CW or CCW. 
    // For future reference you need to know channel where it plugs in and position of motor.
    if (XController.getAButton()) {
      intakeSpark.set(1);
      } 
    else if (XController.getBButton()) {
      intakeSpark.set(-1);
      }  
    else {
      intakeSpark.set(0);
      }

    
 }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
