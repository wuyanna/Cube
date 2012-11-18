package com.hackthon.cube;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Point;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

public class LayoutAnim {

	private int mFrameCount = 60;
	private static final int fangda = 3;
	private ArrayList<Node> from;
	private ArrayList<Node> to;
	private int currentFrame = 0;
	Interpolator interpolator = new AccelerateDecelerateInterpolator();
	// Interpolator interpolator = new AnticipateOvershootInterpolator();
	ArrayList<ArrayList<Node>> framenodes = new ArrayList<ArrayList<Node>>();

	HashMap<String, Node> fromMap = new HashMap<String, Node>();
	HashMap<String, Node> toMap = new HashMap<String, Node>();

	public LayoutAnim(ArrayList<Node> from, ArrayList<Node> to) {
		this.from = from;
		this.to = to;

		// for (int indexfrom=0; indexfrom<cf; indexfrom++) {
		// Node n = from.get(indexfrom);
		// fromMap.put(String.valueOf(n.info().id()), n);
		// }

		filterFromTo();

		for (int i = 0; i < mFrameCount; i++) {
			ArrayList<Node> nodes = new ArrayList<Node>();
			nodes = getFrame1(i);
			framenodes.add(nodes);
		}
	}

	private ArrayList<Node> getFrame3(int frame) {
		return framenodes.get(frame);
	}

	private ArrayList<Node> getFrame2(int frame) {
		long begin = System.currentTimeMillis();
		int ct = to.size();
		long looopbegin = begin;
		ArrayList<Node> result = new ArrayList<Node>();
		for (int i = 0; i < ct; i++) {
			Node nt = to.get(i);

			Node nf = fromMap.get(String.valueOf(nt.info().id()));

			// not find from, set to directly
			if (nf == null) {
				result.add(nt);
				continue;
			}

			float input = (float) frame / (float) mFrameCount;
			float inter = interpolator.getInterpolation(input);
			int stepx = (int) ((float) (nt.center().x - nf.center().x) * inter);
			int stepy = (int) ((float) (nt.center().y - nf.center().y) * inter);
			Point p = new Point(stepx + nf.center().x, stepy + nf.center().y);
			Node r = new Node(nf.info(), p);
			result.add(r);
			long loopend = System.currentTimeMillis();
			Log.i("LayoutAnim", "i: " + i + " time: " + (loopend - looopbegin)
					+ "ms");
			looopbegin = loopend;
		}
		long end = System.currentTimeMillis();
		Log.i("LayoutAnim", "ct: " + ct + " time: " + (end - begin) + "ms");
		if (end - begin > 500) {
			Log.e("LayoutAnim", "ct: " + ct + " time: " + (end - begin) + "ms");
		}
		return result;
	}

	private void filterFromTo() {
		synchronized (from) {

			synchronized (to) {

				int cf = from.size();
				int ct = to.size();

				for (int indexfrom = 0; indexfrom < cf; indexfrom++) {
					Node n = from.get(indexfrom);
					fromMap.put(String.valueOf(n.info().id()), n);
				}
				for (int indexfrom = 0; indexfrom < ct; indexfrom++) {
					Node n = to.get(indexfrom);
					toMap.put(String.valueOf(n.info().id()), n);
				}

				// 从无到有
				for (int i = 0; i < ct; i++) {
					Node nt = to.get(i);

					Node nf = fromMap.get(String.valueOf(nt.info().id()));

					if (nf == null) {
						int x1 = nt.center().x - CubeApplication.screenCenter.x;
						int y1 = nt.center().y - CubeApplication.screenCenter.y;
						int x2 = x1 * fangda + CubeApplication.screenCenter.x;
						int y2 = y1 * fangda + CubeApplication.screenCenter.y;
						Node newFromNode = new Node(nt.info(),
								new Point(x2, y2));
						this.from.add(newFromNode);

					} else {
						String s = "s";
						s = s + "dsf";
					}
				}

				// 从有到无
				for (int i = 0; i < cf; i++) {
					Node nf = from.get(i);

					Node nt = toMap.get(String.valueOf(nf.info().id()));

					if (nt == null) {
						int x1 = nf.center().x - CubeApplication.screenCenter.x;
						int y1 = nf.center().y - CubeApplication.screenCenter.y;
						int x2 = x1 * fangda + CubeApplication.screenCenter.x;
						int y2 = y1 * fangda + CubeApplication.screenCenter.y;
						Node newFromNode = new Node(nf.info(),
								new Point(x2, y2));
						this.to.add(newFromNode);

					} else {
						String s = "s";
						s = s + "dsf";
					}
				}
			}
		}
		// ArrayList<Node> result = new ArrayList<Node>();
		// for (int i=0;i<ct;i++) {
		// Node nt = to.get(i);
		//
		// Node nf = null;
		// for (int j=0;j<cf;j++) {
		// Node n = from.get(j);
		// if (nt.info().id() == n.info().id()) {
		// nf = n;
		// break;
		// }
		// }
		//
		// // not find from, set to directly
		// if (nf == null) {
		// result.add(nt);
		// continue;
		// }
		// }
	}

	private ArrayList<Node> getFrame1(int frame) {
		synchronized (to) {

			long begin = System.currentTimeMillis();
			long looopbegin = begin;
			int cf = from.size();
			int ct = to.size();

			ArrayList<Node> result = new ArrayList<Node>();
			for (int i = 0; i < ct; i++) {
				if (i >= to.size()) {
					String s = "";
					s = s + "fd";
				}
				Node nt = to.get(i);

				Node nf = null;
				for (int j = 0; j < cf; j++) {
					Node n = from.get(j);
					if (nt.info().id() == n.info().id()) {
						nf = n;
						break;
					}
				}

				// not find from, set to directly
				if (nf == null) {
					result.add(nt);
					continue;
				}

				float input = (float) frame / (float) mFrameCount;
				float inter = interpolator.getInterpolation(input);
				int stepx = (int) ((float) (nt.center().x - nf.center().x) * inter);
				int stepy = (int) ((float) (nt.center().y - nf.center().y) * inter);
				Point p = new Point(stepx + nf.center().x, stepy
						+ nf.center().y);
				Node r = new Node(nf.info(), p);
				result.add(r);
				long loopend = System.currentTimeMillis();
				Log.i("LayoutAnim", "i: " + i + " time: "
						+ (loopend - looopbegin) + "ms");
				looopbegin = loopend;
			}
			long end = System.currentTimeMillis();
			Log.i("LayoutAnim", "ct: " + ct + " time: " + (end - begin) + "ms");
			if (end - begin > 500) {
				Log.e("LayoutAnim", "ct: " + ct + " time: " + (end - begin)
						+ "ms");
			}
			return result;
		}
	}

	public ArrayList<Node> getNextFrame() {
		if (currentFrame >= mFrameCount) {
			return this.to;
		}

		ArrayList<Node> result = getFrame3(currentFrame++);
		return result;
	}

	public int frameCount() {
		return mFrameCount;
	}

	public boolean isFinished() {
		return currentFrame >= mFrameCount;
	}

}
