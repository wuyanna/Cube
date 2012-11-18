package com.hackthon.cube;

import java.util.ArrayList;

public class Data {
	private int m = (int) Math.round(Math.random()*20);
	private int n = (int) Math.round(Math.random()*18);
	private int[] lengths = new int[n];
	private float[][] p = new float[n][m];
	private float[] weight = new float[n];
	private String[] names = {"��С�� ","Craig����ƫ��","����s","Desmondcao","��͹������ ","bluelight","�Ǻ�","��³��HD","liСla","theosoft","��С������","T I A M O","t&d lemon","hfxg4m2","gg-pp","wuyanna1989","om19","�ڰ˷�ӡ","Bison","mmin18","zjqzzjh"};
	private String[] shopnames = {"İ���˻������","��������������","ʳ֮��","����԰","�Ϻ��˼Ҿ�����","���㴨�˹�","����������(������֮�ε�)","��ͩС��","Home Garden","����������(��ɽ��԰��)","��������","��̩(������֮�ε�)","��(��ɽ��԰��)","��1039","�ƹ�(ά����)","��������(������֮�ε�)","��һ��(������֮�ε�)","����(������֮�ε�)","�����ջ�(������֮�ε�)","AILI(����·��)","����С��(������֮�ε�)","���ų���(������)","��˾����(��ɽ��԰��)"};
	static public int[] useravatar = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10,R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a13,R.drawable.a13,R.drawable.a13,R.drawable.a13,R.drawable.a13,R.drawable.a13,R.drawable.a13,R.drawable.a13,R.drawable.a13,R.drawable.a13};
	public Data(){
		for (int i=0;i<n;i++){
			lengths[i] = (int)Math.round(Math.random()*n);
			for (int j=0;j<m;j++)
			{
				double ran = Math.random();
				if (ran>0.4)
					p[i][j] = (float) (Math.random()*0.7+0.3);
				else  p[i][j] = 0;
			}
			weight[i]=(float) (Math.random()*0.7+0.3);
		}
	}

	public ArrayList<NodeInfo> getFriendByShop(int shopId){
		ArrayList<NodeInfo> list = new ArrayList<NodeInfo>();
		for (int j=0;j<m;j++)
		{
			if (p[shopId-10000][j]!=0)
				list.add(new NodeInfo(names[j],0,j, p[shopId-10000][j]));
		}
		return list;
	}

	public ArrayList<NodeInfo> getShopByUser(int userId){
		ArrayList<NodeInfo> list = new ArrayList<NodeInfo>();
		for (int i=0;i<n;i++)
		{
			if (p[i][userId]!=0)
				list.add(new NodeInfo(shopnames[i],1,i+10000, p[i][userId]));
		}
		return list;
	}
	
	public ArrayList<NodeInfo> getFriends(int userId){
		ArrayList<NodeInfo> list = new ArrayList<NodeInfo>();
		for (int i=0;i<m;i++)
		{
			if (i!=userId)
				list.add(new NodeInfo(names[i],0,i, 0));
		}
		return list;
	}
	
	public ArrayList<NodeInfo> getShops(int userId){
		ArrayList<NodeInfo> list = new ArrayList<NodeInfo>();
		if (userId>=10000)
		{
			return list;
		}
		for (int i=0;i<n;i++)
		{
				list.add(new NodeInfo(shopnames[i],1,i+10000, weight[i]));
		}
		return list;
	}
	
	public NodeInfo getNodeInfoById(int id){
		if (id >=10000)
			return new NodeInfo(shopnames[id-10000],1,id, 0);
		else return new NodeInfo(names[id],0,id, 0);
		
	}


}
