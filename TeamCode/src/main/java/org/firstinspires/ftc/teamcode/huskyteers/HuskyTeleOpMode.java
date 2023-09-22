package org.firstinspires.ftc.teamcode.huskyteers;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@Config
@TeleOp(name = "Husky TeleOp Mode", group = "Teleop")
public class HuskyTeleOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {

        // region INITIALIZATION
        HuskyBot huskyBot = new HuskyBot(this);
        huskyBot.init();

        Gamepad currentGamepad1;
        Gamepad currentGamepad2;

        waitForStart();
        if (isStopRequested()) return;
        // endregion

        // region TELEOP LOOP
        while (opModeIsActive() && !isStopRequested()) {
            currentGamepad1 = gamepad1;
            currentGamepad2 = gamepad2;

            if (currentGamepad1.start) {
                huskyBot.setCurrentHeadingAsForward();
            }

            if(currentGamepad1.left_bumper && huskyBot.huskyVision.backdropAprilTagDetection.getAprilTagById(583).isPresent()){
                PoseVelocity2d pw = huskyBot.alignWithAprilTag(583);
                telemetry.addData("drive" ,pw.component1().y );
                telemetry.addData("strafe" , pw.component1().x);
                telemetry.addData("turn" , pw.component2());
                huskyBot.driveRobot(pw.component1().y, pw.component1().x, pw.component2(), 1.0);

            }
            else{
                huskyBot.fieldCentricDriveRobot(
                        -currentGamepad1.left_stick_y,
                        currentGamepad1.left_stick_x,
                        currentGamepad1.right_stick_x,
                        (0.35 + 0.5 * currentGamepad1.left_trigger));
            }

            telemetry.addData("Left Bumper", currentGamepad1.left_bumper);
            telemetry.addData("April Detected", huskyBot.huskyVision.backdropAprilTagDetection.getAprilTagById(583).isPresent());
            telemetry.update();
        }
    }
    // endregion
}