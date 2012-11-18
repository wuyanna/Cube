package com.hackthon.cube;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CubeActivity extends Activity implements CubeSurfaceView.OnInfoListener {

	private TextView mInfo;
	private CubeSurfaceView mCubeView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        mInfo = (TextView) findViewById(R.id.info);
        mCubeView = (CubeSurfaceView) findViewById(R.id.surface);

        mCubeView.setOnInfoListener(this);

//        testNode();
//        testLayoutAnim();
    }

    private void testNode() {
    	NodeInfo ni = new NodeInfo("test", 0, 0, 0.8);
    	final Node n = new Node(ni, new Point(50, 80));
    	View v = new View(this) {

			@Override
			protected void onDraw(Canvas canvas) {
				super.onDraw(canvas);

				// draw background;
//				canvas.drawColor(Color.WHITE);
				n.draw(canvas);
			}
    	};

    	setContentView(v);
    }

    Timer testLayoutAnimTimer = new Timer();
    private void testLayoutAnim() {
    	NodeInfo ni = new NodeInfo("test", 0, 0, 0.8);
    	final Node n = new Node(ni, new Point(50, 80));
    	ArrayList<Node> from = new ArrayList<Node>();
    	from.add(n);
    	ArrayList<Node> to = new ArrayList<Node>();
    	NodeInfo ni2 = new NodeInfo("test", 0, 0, 0.8);
    	final Node n2 = new Node(ni2, new Point(200, 400));
    	to.add(n2);

    	final LayoutAnim la = new LayoutAnim(from, to);
    	final View v = new View(this) {

			@Override
			protected void onDraw(Canvas canvas) {
				super.onDraw(canvas);

				// draw background;
//				canvas.drawColor(Color.WHITE);
				ArrayList<Node> ns = la.getNextFrame();
				Node n = ns.get(0);
				n.draw(canvas);
			}
    	};

    	testLayoutAnimTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				v.postInvalidate();
			}

		}, 20, 20);
    	setContentView(v);
    }

	@Override
	public void onInfo(Node center) {
		StringBuilder sb = new StringBuilder();
		if (center.info().type() == 0) {
			sb.append("姓名: ").append(center.info().name()).append("\n");
			sb.append("签到357次 ").append("\n");
			sb.append("点评182次 ").append("\n");
		} else {
			sb.append("商户名: ").append(center.info().name()).append("\n");
			sb.append("粉丝 13654人 ").append("\n");
			sb.append("18793人来过 ").append("\n");
			sb.append("1893人点评过 ").append("\n");
		}

		mInfo.setText(sb.toString());
	}
}