package com.hackthon.cube;

import java.util.ArrayList;

import android.graphics.Point;

public class LayoutCalc {

	private long mFriendRadius = 200;
	private long mShopRadius = 400;

	private double mPerRelAngle = 0.3;

	public void layout(Node center, ArrayList<Node> friends, ArrayList<Node> shops, int screenWidth, int screenHeight) {
		center.setCenter(new Point(screenWidth/2, screenHeight/2));

		if (!friends.isEmpty()) {
			double perAngle = 2 * Math.PI / friends.size();
			double relAngle = 0;
			for (Node node : friends) {
				node.setRelRadius(mFriendRadius);
				node.setRelAngle(relAngle);

				int dx = (int) (mFriendRadius * Math.cos(relAngle));
				int dy = (int) (mFriendRadius * Math.sin(relAngle));
				node.setCenter(new Point(screenWidth/2 + dx, screenHeight/2 + dy));

				relAngle += perAngle;
			}
		}

		if (!shops.isEmpty()) {
			mPerRelAngle = 2 * Math.PI / shops.size();
			double relAngle = 0;
			for (Node node : shops) {
				long radius = (long) (mShopRadius * node.info().weight());
				node.setRelRadius(radius);
				node.setRelAngle(relAngle);

				int dx = (int) (radius * Math.cos(relAngle));
				int dy = (int) (radius * Math.sin(relAngle));
				node.setCenter(new Point(screenWidth/2 + dx, screenHeight/2 + dy));

				relAngle += mPerRelAngle;
			}
		}
	}

	public double perRelAngle() {
		//return mPerRelAngle;
		return 0.008;
	}

}
