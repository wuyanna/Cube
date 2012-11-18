package com.hackthon.cube;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Point;

public class CircleAnim {

	private double angle = 15 / 180 * Math.PI;
	private ArrayList<Node> nodes;
	private Point center;

	public CircleAnim(ArrayList<Node> nodes, Point center, double angle) {
		this.nodes = nodes;
		this.center = center;
		if (angle != 0)
			this.angle = angle;

	}
	
	private ArrayList<Node> changeUserNodes() {
		for (int i = 0; i < this.nodes.size(); i++) {
			
			Node node = nodes.get(i);
			int px,py;
			if (Math.random()>0.5)
				px=1;
			else px=-1;
			if (Math.random()>0.5)
				py=1;
			else py=-1;
			if ((node.center().x+px>1280)||(node.center().x+px<0))
			px = 0-px;
			if ((node.center().y+py>800)||(node.center().y+py<0))
				py = 0-py;
			node.setCenter(new Point(node.center().x+px,node.center().y+py));
		}
		return nodes;
	}

	private ArrayList<Node> changeNodes() {
		for (int i = 0; i < this.nodes.size(); i++) {
			int x = 0, y = 0;
			Node node = nodes.get(i);
			// double r =
			// Math.sqrt(((node.center().x-this.center.x)*(node.center().x-this.center.x))+((node.center().y-this.center.y)*(node.center().y-this.center.y)));
			double angle = node.relAngle() + this.angle;
			if (angle > 2 * Math.PI)
				angle = (angle - 2 * Math.PI);
				x = (int) (center.x + Math.cos(angle) * node.relRadius());
				y = (int) (center.y + Math.sin(angle) * node.relRadius());
//			} else if (angle <= Math.PI) {
//				x = (int) (center.x - Math.cos(angle) * node.relRadius());
//				y = (int) (center.y + Math.sin(angle) * node.relRadius());
//			} else if (angle <= 1.5 * Math.PI) {
//				x = (int) (center.x - Math.cos(angle) * node.relRadius());
//				y = (int) (center.y - Math.sin(angle) * node.relRadius());
//			} else if (angle <= 2 * Math.PI) {
//				x = (int) (center.x + Math.cos(angle) * node.relRadius());
//				y = (int) (center.y - Math.sin(angle) * node.relRadius());
//			}
				node.setRelAngle(angle);
			node.setCenter(new Point(x,y));
		}
		return nodes;
	}

	public ArrayList<Node> getNextFrame() {
		return changeNodes();
	}
	
	public ArrayList<Node> getNextUserFrame() {
		return changeUserNodes();
	}

}
