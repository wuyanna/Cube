package com.hackthon.cube;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Point;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;

public class FlyAnim {

	private int mFrameCount = 60;
	private ArrayList<Node> from;
	private ArrayList<Node> to;
	private int currentFrame = 0;
//	Interpolator interpolator = new AccelerateDecelerateInterpolator();
	Interpolator interpolator = new AccelerateInterpolator();
//	Interpolator interpolator = new AnticipateOvershootInterpolator();


	HashMap<String, Node> fromMap = new HashMap<String, Node>();
	public FlyAnim(ArrayList<Node> from, ArrayList<Node> to) {
		this.from = from;
		this.to = to;
		
		int cf = from.size();
		
		for (int indexfrom=0; indexfrom<cf; indexfrom++) {
			Node n = from.get(indexfrom);
			fromMap.put(String.valueOf(n.info().id()), n);
		}
//		int ct = to.size();
		
//		ArrayList<Node> result = new ArrayList<Node>();
//		for (int i=0;i<ct;i++) {
//			Node nt = to.get(i);
//			
//			Node nf = null;
//			for (int j=0;j<cf;j++) {
//				Node n = from.get(j);
//				if (nt.info().id() == n.info().id()) {
//					nf = n;
//					break;
//				}
//			}
//
//			// not find from, set to directly
//			if (nf == null) {
//				result.add(nt);
//				continue;
//			}
//		}
	}
	
	private ArrayList<Node> getFrame(int frame) {
		int ct = to.size();

		ArrayList<Node> result = new ArrayList<Node>();
		for (int i=0;i<ct;i++) {
			Node nt = to.get(i);

			Node nf = fromMap.get(String.valueOf(nt.info().id()));
			
			// not find from, set to directly
			if (nf == null) {
				result.add(nt);
				continue;
			}

			float input = (float)frame / (float)mFrameCount;
			float inter = interpolator.getInterpolation(input);
			int stepx = (int)((float)(nt.center().x - nf.center().x) * inter);
			int stepy = (int)((float)(nt.center().y - nf.center().y) * inter);
			Point p = new Point(stepx + nf.center().x , stepy + nf.center().y);
			Node r = new Node(nf.info(), p);
			result.add(r);
		}
		return result;
	}

//	private ArrayList<Node> getFrame(int frame) {
//		int cf = from.size();
//		int ct = to.size();
//
//		ArrayList<Node> result = new ArrayList<Node>();
//		for (int i=0;i<ct;i++) {
//			Node nt = to.get(i);
//
//			Node nf = null;
//			for (int j=0;j<cf;j++) {
//				Node n = from.get(j);
//				if (nt.info().id() == n.info().id()) {
//					nf = n;
//					break;
//				}
//			}
//
//			// not find from, set to directly
//			if (nf == null) {
//				result.add(nt);
//				continue;
//			}
//
//			float input = (float)frame / (float)mFrameCount;
//			float inter = interpolator.getInterpolation(input);
//			int stepx = (int)((float)(nt.center().x - nf.center().x) * inter);
//			int stepy = (int)((float)(nt.center().y - nf.center().y) * inter);
//			Point p = new Point(stepx + nf.center().x , stepy + nf.center().y);
//			Node r = new Node(nf.info(), p);
//			result.add(r);
//		}
//		return result;
//	}

	public ArrayList<Node> getNextFrame() {
		if (currentFrame >= mFrameCount) {
			return this.to;
		}

		ArrayList<Node> result = getFrame(currentFrame++);
		return result;
	}

	public int frameCount() {
		return mFrameCount;
	}

	public boolean isFinished() {
		return currentFrame >= mFrameCount;
	}

}
