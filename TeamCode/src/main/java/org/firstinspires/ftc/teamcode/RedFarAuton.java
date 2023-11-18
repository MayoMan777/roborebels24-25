package org.firstinspires.ftc.teamcode;

import android.util.Size;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.Position;
import org.firstinspires.ftc.teamcode.vision.RedPropThreshold;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
@Autonomous(name = "Red Far")
public class RedFarAuton extends LinearOpMode {

  public static int dfDist = 700;
  public static int dsDist = 800;
  public static int lAng = 90;
  public static int rAng = -90;

  public static int place = -600;

  Robot robot;
  private VisionPortal portal;
  RedPropThreshold processor;

  @Override
  public void runOpMode() throws InterruptedException {
    processor = new RedPropThreshold();
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
  }
}