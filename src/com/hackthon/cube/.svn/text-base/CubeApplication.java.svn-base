package com.hackthon.cube;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class CubeApplication extends Application {

	private static Context sContext;

	public static Context context() {
		return sContext;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		sContext = this;
		loadRes();
	}

	static Bitmap planet;
	static Bitmap ufo;
	static Bitmap sun;
	static Bitmap light;
	static Bitmap light1;
	static ArrayList<SoftReference<Bitmap>> avatars = new ArrayList<SoftReference<Bitmap>>();

	static Point screenCenter;
	static Point screenSize;

	private void loadRes() {
		planet = BitmapFactory.decodeResource(CubeApplication.context().getResources(), R.drawable.shopicon);
		ufo = BitmapFactory.decodeResource(CubeApplication.context().getResources(), R.drawable.ufo);
		sun = BitmapFactory.decodeResource(CubeApplication.context().getResources(), R.drawable.sun);
		light = BitmapFactory.decodeResource(CubeApplication.context().getResources(), R.drawable.light);
		light1 = BitmapFactory.decodeResource(CubeApplication.context().getResources(), R.drawable.light1);
		int l = Data.useravatar.length;

		for (int i=0;i<l;i++) {
			Bitmap b = BitmapFactory.decodeResource(CubeApplication.context().getResources(), Data.useravatar[i]);
			SoftReference<Bitmap> sb = new SoftReference<Bitmap>(BitmapUtils.getRCB(b, 5));
			avatars.add(sb);
		}
		WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		screenSize = new Point(dm.widthPixels, dm.heightPixels);
		screenCenter = new Point(dm.widthPixels / 2, dm.heightPixels / 2);
	}

}
