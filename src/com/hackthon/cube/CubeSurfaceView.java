package com.hackthon.cube;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class CubeSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mHolder;

	private Data mData;
	private Bitmap mBgBmp;

	private Node mCenterNode;
	private ArrayList<Node> mFriendNodes = new ArrayList<Node>();
	private ArrayList<Node> mShopNodes = new ArrayList<Node>();
	private ArrayList<Node> mOriginNodes = new ArrayList<Node>();
	private ArrayList<Node> mNodes = new ArrayList<Node>();

	private Timer mAnimTimer;
	private LayoutAnim mLayoutAnim;
	private CircleAnim mShopAnim;
	private CircleAnim mUserAnim;
//	private FlyAnim mFlyAnim;

	private LayoutCalc mLayoutCalc = new LayoutCalc();
	private boolean mLayouted;

	public interface OnInfoListener {

		void onInfo(Node center);

	}

	private OnInfoListener mInfoListener;

	public void setOnInfoListener(OnInfoListener l) {
		mInfoListener = l;
	}

	public CubeSurfaceView(Context context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
	}

	public CubeSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHolder = getHolder();
		mHolder.addCallback(this);
	}

	public CubeSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mHolder = getHolder();
		mHolder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	private boolean issurfaceCreated = false;
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mAnimTimer = new Timer();

//		mAnimTimer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
		        mBgBmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bg);
		        issurfaceCreated = true;
				initData(0);
				issurfaceCreated = false;
//			}
//
//		}, 0);

		mAnimTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				redraw();
			}

		}, 0, 30);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mAnimTimer.cancel();
	}

	private void initData(int uid) {
		mLayouted = false;

		NodeInfo curNode = null;
		if (mData != null) {
			curNode = mData.getNodeInfoById(uid);
		}

		if ((curNode == null) || (curNode.type() == 0)) {
			mData = new Data();
		} else {
			if (mData == null) {
				mData = new Data();
			}
		}

		curNode = mData.getNodeInfoById(uid);

		ArrayList<NodeInfo> shops = mData.getShops(uid);
		mShopNodes.clear();

		ArrayList<NodeInfo> friends =null;
		if (curNode.type() == 0) {
			friends = mData.getFriends(uid);
		} else {
			friends = mData.getFriendByShop(uid);
		}
		mFriendNodes.clear();

		for (NodeInfo info : shops) {
			if (curNode.type() == 0) {
			    mShopNodes.add(new Node(info, new Point(0, 0)));
			} else {
				mFriendNodes.add(new Node(info, new Point(0, 0)));
			}
		}

		for (NodeInfo info : friends) {
			if (curNode.type() == 0) {
				mFriendNodes.add(new Node(info, new Point(0, 0)));
			} else {
				mShopNodes.add(new Node(info, new Point(0, 0)));
			}
		}

		mCenterNode = new Node(curNode, new Point(100, 100));
		mCenterNode.setIsCenterNode(true);

		synchronized (mNodes) {
			mNodes.clear();
			mNodes.addAll(mShopNodes);
			mNodes.addAll(mFriendNodes);
		}

		if (!issurfaceCreated && mOriginNodes.isEmpty()) {
		    mOriginNodes.addAll(mNodes);
		}

		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		mLayoutCalc.layout(mCenterNode, mFriendNodes, mShopNodes, dm.widthPixels, dm.heightPixels);

		mLayouted = true;

		mLayoutAnim = new LayoutAnim(mOriginNodes, mNodes);
		mShopAnim = new CircleAnim(mShopNodes, mCenterNode.center(), mLayoutCalc.perRelAngle());
		mUserAnim = new CircleAnim(mFriendNodes, mCenterNode.center(), mLayoutCalc.perRelAngle());

//		ArrayList<Node> from = new ArrayList<Node>();
//		from.add(new Node(new NodeInfo("", 1, 2000, 1.0), new Point(-100, -100)));
//		ArrayList<Node> to = new ArrayList<Node>();
//		from.add(new Node(new NodeInfo("", 1, 2000, 1.0), new Point(2000, 2000)));
//		mFlyAnim = new FlyAnim(from, to);
	}

	private Node mTouchDownNode;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Point pt = new Point((int)event.getX(), (int)event.getY());
		synchronized (mNodes) {
			for (Node node : mNodes) {
				if (node.hitTest(pt)) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						node.onTouched(false);
						mTouchDownNode = node;

						return true;
					}
				}
			}
		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (mTouchDownNode != null) {
				mTouchDownNode.onTouched(true);
				if (mInfoListener != null) {
				    mInfoListener.onInfo(mTouchDownNode);
				}
				initData (mTouchDownNode.info().id());
				mTouchDownNode = null;
			}
		}

		return false;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		if (!changed) {
			return;
		}

		if (mCenterNode == null) {
			return;
		}

		mOriginNodes.clear();
		mOriginNodes.addAll(mNodes);

		mLayoutCalc.layout(mCenterNode, mFriendNodes, mShopNodes, right-left, bottom-top);

		mLayoutAnim = new LayoutAnim(mOriginNodes, mNodes);
		mShopAnim = new CircleAnim(mShopNodes, mCenterNode.center(), mLayoutCalc.perRelAngle());
		mUserAnim = new CircleAnim(mFriendNodes, mCenterNode.center(), mLayoutCalc.perRelAngle());

		mLayouted = true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (canvas == null) {
			return;
		}
		if (!mLayouted) {
			super.onDraw(canvas);
			return;
		}

		canvas.drawColor(Color.GRAY);
		canvas.drawBitmap(mBgBmp, 0, 0, new Paint());

		if (!mLayoutAnim.isFinished()) {
			mNodes = mLayoutAnim.getNextFrame();
		} else {
			mShopNodes = mShopAnim.getNextFrame();
			mFriendNodes = mUserAnim.getNextUserFrame();

			mNodes.clear();
			mNodes.addAll(mShopNodes);
			mNodes.addAll(mFriendNodes);
		}

		mCenterNode.draw(canvas);

		for (Node node : mNodes) {
			node.draw(canvas);
		}

//		if (mFlyAnim != null) {
//			ArrayList<Node> nodes = mFlyAnim.getNextFrame();
//			if (nodes != null) {
//				for (Node node : nodes) {
//					node.draw(canvas);
//				}
//			}
//		}

		super.onDraw(canvas);
	}

	private void redraw() {
		if (mHolder == null) {
			return;
		}

		Canvas c = mHolder.lockCanvas(null);

		synchronized (mNodes) {
            onDraw(c);
		}

        mHolder.unlockCanvasAndPost(c);
	}

}
