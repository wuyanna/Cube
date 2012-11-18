package com.hackthon.cube;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.renderscript.Font.Style;

public class Node {

	private NodeInfo mInfo;
	private Point mCenter;
	private long mRadius = 40;
	private Paint circlePaint;
	private Paint textPaint;
	private Paint circleStrokePaint;

	private long mRelRadius;
	private double mRelAngle;

	private boolean mIsCenterNode;
	
	private Bitmap planet;
	private Bitmap ufo;
	private Bitmap sun;
	boolean isShine = false;
//	private Bitmap avatar;
	private Bitmap light;
	private static final int shine_time = 500;
	private long starttime;
	

	public Node(NodeInfo info, Point center) {
		starttime = System.currentTimeMillis();
		mInfo = info;
		mCenter = center;
		circlePaint = new Paint();
		circlePaint.setColor(Color.WHITE);
		circlePaint.setStrokeWidth(2);
		circlePaint.setStyle(Paint.Style.STROKE);
		textPaint = new Paint();
		textPaint.setTextSize(18);
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);
		textPaint.setColor(Color.WHITE);
		textPaint.setShadowLayer(2, 2, 2, 0xFFF6941D);
		
//		planet = BitmapFactory.decodeResource(CubeApplication.context().getResources(), R.drawable.planet);
//		ufo = BitmapFactory.decodeResource(CubeApplication.context().getResources(), R.drawable.ufo);
//		sun = BitmapFactory.decodeResource(CubeApplication.context().getResources(), R.drawable.sun);
		planet = CubeApplication.planet;
//		ufo = BitmapUtils.getRCB(CubeApplication.ufo, 5);
		ufo = CubeApplication.ufo;
		sun = CubeApplication.sun;
//		if(mInfo.type()==0 && mInfo.id() < 10000)
//			avatar = BitmapFactory.decodeResource(CubeApplication.context().getResources(), Data.useravatar[mInfo.id()]);
		light = CubeApplication.light;
	}

	public NodeInfo info() {
		return mInfo;
	}


	public Point center() {
		return mCenter;
	}

	public void setCenter(Point center) {
		mCenter = center;
	}

	public void setIsCenterNode(boolean b) {
		mIsCenterNode = b;
	}

	public void setRelRadius(long relRadius) {
		mRelRadius = relRadius;
	}

	public long relRadius() {
		return mRelRadius;
	}

	public void setRelAngle(double relAngle) {
		mRelAngle = relAngle;
	}

	public double relAngle() {
		return mRelAngle;
	}

	public boolean hitTest(Point pt) {
		if ((pt.x - mCenter.x)*(pt.x - mCenter.x) + (pt.y - mCenter.y)*(pt.y - mCenter.y) <= mRadius*mRadius) {
			return true;
		}
		return false;
	}

	public void onTouched(boolean isTouchUp) {

	}

	private int degree = 0;
	public void draw(Canvas canvas) {
//		canvas.save();
//
//		
//		canvas.rotate(degree, canvas.getWidth() / 2, canvas.getHeight() / 2);
		
//		Matrix matrix = new Matrix();
//		matrix.postRotate(degree);
//		
//		Path circle = new Path();
//	    circle.transform(matrix);
	    
		
//		Bitmap resizedplanet = Bitmap.createBitmap(planet, 0, 0, planet.getWidth(), planet.getHeight(), matrix, true);
//		Bitmap resizedufo = Bitmap.createBitmap(ufo, 0, 0, ufo.getWidth(), ufo.getHeight(), matrix, true);
		float txtLength = textPaint.measureText(mInfo.name());
		
//		if (mIsCenterNode) {
//			
//			circlePaint.setColor(Color.RED);
//			mRadius = 50;
//			int w = sun.getWidth();
//			int h = sun.getHeight();
//			w = w * 3 / 4;
//			h = h * 3 / 4;
//			int w1 = 700;
//			int h1 = 700;
//			
//			long ct = System.currentTimeMillis();
//			if (ct - starttime > shine_time) {
//				
//				isShine = !isShine;
//				starttime = ct;
//			}
//			if (isShine) {
//				canvas.drawBitmap(light, null, new Rect(mCenter.x-w1/2,mCenter.y-h1/2,mCenter.x+w1/2,mCenter.y+h1/2), circlePaint);
//			} else {
//				canvas.drawBitmap(CubeApplication.light1, null, new Rect(mCenter.x-w1/2,mCenter.y-h1/2,mCenter.x+w1/2,mCenter.y+h1/2), circlePaint);
//			}
//			
////			canvas.drawBitmap(sun, null, new Rect(mCenter.x-w/2,mCenter.y-h/2,mCenter.x+w/2,mCenter.y+h/2), circlePaint);
//			
//			//canvas.drawCircle(mCenter.x, mCenter.y, mRadius, circlePaint);
//		} 
//		else {
			if (mInfo.type() == 1) {
				circlePaint.setColor(Color.GREEN);
				textPaint.setColor(Color.WHITE);
				textPaint.setShadowLayer(2, 2, 2, Color.BLACK);
//				canvas.drawCircle(mCenterr.x, mCenter.y, mRadius - 4, circlePaint);
				mRadius = 40;
				int w = planet.getWidth();
				int h = planet.getHeight();
				w = w * 2 / 4;
				h = h * 2 / 4;
				canvas.drawBitmap(planet, null, new Rect(mCenter.x-w/2,mCenter.y-h/2,mCenter.x+w/2,mCenter.y+h/2), circlePaint);
//				canvas.drawBitmap(planet, mCenter.x-planet.getWidth()/2,  mCenter.y-planet.getHeight()/2, circlePaint);
				canvas.drawText(mInfo.name(), mCenter.x - txtLength/2, mCenter.y+20, textPaint);
			} else if (mInfo.type() == 0){
				circlePaint.setColor(Color.BLUE);
				textPaint.setColor(Color.WHITE);
				textPaint.setShadowLayer(2, 2, 2, 0xFFF6941D);
				mRadius = 20;
//				canvas.drawBitmap(ufo, mCenter.x+ufo.getWidth()/2,  mCenter.y+ufo.getHeight()/2, circlePaint);
				int w = ufo.getWidth();
				int h = ufo.getHeight();
				w = 40;
				h = 40;
				canvas.drawBitmap(ufo, null, new Rect(mCenter.x-w/2,mCenter.y-h/2,mCenter.x+w/2,mCenter.y+h/2), circlePaint);
				SoftReference<Bitmap> sb = CubeApplication.avatars.get(mInfo.id());
				Bitmap avatar = sb.get();
				if (avatar == null) {
					Bitmap tmpBitmap = BitmapFactory.decodeResource(CubeApplication.context().getResources(), Data.useravatar[mInfo.id()]);
					SoftReference<Bitmap> tmpsb = new SoftReference<Bitmap>(BitmapUtils.getRCB(tmpBitmap, 5));
					CubeApplication.avatars.add(mInfo.id(), tmpsb);
					sb = tmpsb;
					avatar = tmpBitmap;
				}
				canvas.drawBitmap(avatar, null, new Rect(mCenter.x-w/2,mCenter.y-h/2,mCenter.x+w/2,mCenter.y+h/2), circlePaint);
				textPaint.setTextSize(16);
				canvas.drawText(mInfo.name(), mCenter.x - txtLength/2, mCenter.y-mRadius, textPaint);
			}
//		}
		
		
		//canvas.drawCircle(mCenter.x, mCenter.y, mRadius, circlePaint);

		
		
		//canvas.drawBitmap(planet, mCenter.x+bitmap.getWidth()/2,  mCenter.y+bitmap.getHeight()/2, circlePaint);
		
//		canvas.drawTextOnPath(mInfo.name(), circle, 0, 0, textPaint);
//		degree = degree + 15;
		
//		canvas.restore();
	}

	@Override
	public String toString() {
		return "mCenter x: " + mCenter.x + " y: " + mCenter.y + " | mInfo: " + mInfo.toString();
	}
}

