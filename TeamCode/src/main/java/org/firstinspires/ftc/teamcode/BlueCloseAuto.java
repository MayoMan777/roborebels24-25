package org.firstinspires.ftc.teamcode;

import android.util.Size;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.BluePropThreshold;
import org.firstinspires.ftc.teamcode.vision.Position;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
@Autonomous(name = "Blue Close")
public class BlueCloseAuto extends LinearOpMode {

  public static int dfDist = 700;

  public static int leftDist = 500;

  public static int leftAng = 90;
  public static int centerAng = -90;
  public static int lAng = 90;
  public static int rAng = -90;

  Robot robot;
  private VisionPortal portal;
  BluePropThreshold processor;

  @Override
  public void runOpMode() throws InterruptedException {
    processor = new BluePropThreshold();
    robot = new Robot(this);
    int ang = 0;

    portal = new VisionPortal.Builder()
            .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
            .setCameraResolution(new Size(640, 480))
            .addProcessor(processor)
            .build();

    while (opModeInInit()) {
      telemetry.addData("Location", processor.getElePos());
      telemetry.update();

    }
    Position x = processor.getElePos();
    switch (x) {
      case LEFT:
        ang = lAng;
        break;
      case RIGHT:
        ang = rAng;
        break;
    }

    //Start Movement

    if(x != Position.LEFT){
      robot.encodeDriveForward(dfDist, .3);
      if (x != Position.CENTER) {
        robot.turnByGyro(ang);
      }
      robot.encodeDriveForward(20, .3);
      robot.spitPixel();
      sleep(2000);
      robot.encodeDriveForward(-20, .3);
    } else {
      robot.encodeDriveForward(dfDist - 10, .3);
      if (x != Position.CENTER) {
        robot.turnByGyro(ang);
      }
      robot.encodeDriveForward(60, .3);
      robot.spitPixel();
      sleep(2000);
    }

    switch (x) {
      case LEFT:
        robot.encodeDriveStrafe(-dfDist, .3);
        sleep(300);
        robot.encodeDriveForward(dfDist-100, .3);
        sleep(300);
        robot.encodeDriveStrafe(200, .3);

        robot.turnByGyro(-leftAng);

        robot.encodeDriveStrafe(-leftDist + 250, .3);
        sleep(300);
        robot.encodeDriveForward(-415, .3);

        robot.setSlidePos(2800, 1);
        sleep(500);
        robot.setSlidePos(0, 1);
        break;
      case RIGHT:
        robot.encodeDriveForward(-dfDist-175 -40, .3);
        sleep(300);
        robot.encodeDriveStrafe(-140, .3);
        sleep(300);
        robot.encodeDriveForward(-85, 3);

        robot.setSlidePos(2800, 2);
        sleep(500);
        robot.setSlidePos(0, 2);
        break;

      case CENTER:
        robot.encodeDriveStrafe(-dfDist, .3);
        sleep(300);
        robot.turnByGyro(centerAng);

        robot.encodeDriveForward(-320, .3);

        robot.setSlidePos(2800, 1);
        sleep(500);
        robot.setSlidePos(0, 1);
        break;
    }

  }
}