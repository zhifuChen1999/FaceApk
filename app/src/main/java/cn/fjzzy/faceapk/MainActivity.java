package cn.fjzzy.faceapk;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.fjzzy.faceapk.view.FaceView;

public class MainActivity extends AppCompatActivity {

    private FaceView faceView;
    private Path path;
    private PathMeasure pathMeasure;
    private Thread thread;
    private ScheduledExecutorService scheduledExecutorService;
    private float length;
    private float curLength;
    private float angle;
    private float bili;
    private Display display;
    private float anglebili;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = getWindowManager().getDefaultDisplay();
        initView();
    }

    private void initView() {

        faceView = (FaceView) findViewById(R.id.faceView);
        path = new Path();
//        path.moveTo(500,150);
//        path.rQuadTo(800,500,0,1000);
//        path.close();

        path.addCircle(display.getWidth()/2,display.getHeight()/2,display.getWidth()/2, Path.Direction.CW);
        faceView.setLinePath(path);

        pathMeasure = new PathMeasure(path,false);
        length = pathMeasure.getLength();
        bili = length / (192f);
        anglebili=360/(192f);
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                curLength=0;
                scheduledExecutorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        while (curLength<length) {
                            curLength += bili;
                            angle+=anglebili;
                            float[] pos = new float[2];
                            float[] tan = new float[2];
                            boolean posTan = pathMeasure.getPosTan(curLength, pos, tan);
                            faceView.setScreenHalfWidthAndHeight(pos[0], pos[1],angle);
                            try {
                                Thread.sleep(15);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        },0,3, TimeUnit.SECONDS);
    }
}