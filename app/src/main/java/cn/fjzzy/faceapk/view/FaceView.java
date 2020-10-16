package cn.fjzzy.faceapk.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class FaceView extends View {

    private Paint mPaint;
    private float screenHalfWidth;
    private float screenHalfHeight;
    private float angle;
    private Thread childThread;
    private Path linePath;

    public FaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScreenHalfWidthAndHeight(float screenHalfWidth, float screenHalfHeight, float angle) {
        this.screenHalfWidth = screenHalfWidth;
        this.screenHalfHeight = screenHalfHeight;
        this.angle = angle;
        invalidate();
    }

    public float getScreenHalfWidth() {
        return screenHalfWidth;
    }
    public float getScreenHalfHeight() {
        return screenHalfHeight;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化画笔
        mPaint = new Paint();
        //绘制背景
        canvas.drawColor(Color.rgb(150,150,200));
        //绘制外围路线
        if (linePath!=null){
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.WHITE);
            canvas.drawPath(linePath,mPaint);
        }
        mPaint.setStyle(Paint.Style.FILL);

        //转移画布绘制点
        canvas.translate(screenHalfWidth, screenHalfHeight);

        //绘制脸
        mPaint.setColor(Color.RED);
        canvas.drawCircle(0, 0, 150, mPaint);

        //绘制眼睛
        //绘制左眼
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(-50, -30, 20, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(-50,-20,10,mPaint);

        //绘制右眼
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(50, -30, 20, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(50,-20,10,mPaint);


        //绘制鼻子
        mPaint.setColor(Color.BLACK);
        Path path=new Path();
        path.moveTo(0,0);
        path.lineTo(-10,20);
        path.lineTo(10,20);
        path.close();
        canvas.drawPath(path,mPaint);

        //绘制嘴巴
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        canvas.translate(0,55);
        Path mouthPath=new Path();
        mouthPath.moveTo(-40,0);
        mouthPath.lineTo(40,0);
        mouthPath.quadTo(0,30,-40,0);
        canvas.drawPath(mouthPath,mPaint);
    }

    public void setLinePath(Path linePath) {
        this.linePath = linePath;
    }
}
