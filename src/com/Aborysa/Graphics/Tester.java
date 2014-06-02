package com.Aborysa.Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tester {
	JFrame frame;
	Canvas can;
	Dimension size;
	boolean running = true;
	DrawingBuffer testBuff = new DrawingBuffer(1024,4,32);
	BufferStrategy bufferStrat;
	float tempTime = 0;
	public Tester(int width, int height){
		frame = new JFrame("Test");
		can = new Canvas();
		size = new Dimension(width,height);
		init();
		
	}
	private void init(){
		//System.setProperty("sun.awt.noerasebackground", "true");
		can.setPreferredSize(size);
		can.setBackground(Color.BLACK);
		frame.add(can);
		frame.setPreferredSize(size);
		frame.setSize(size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		can.createBufferStrategy(2);
		Drawer.setTarget(can);
		testBuff.fill(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0,(byte)0});
		byte[] contains = testBuff.getPixels();
		for (int i=0; i< testBuff.getPixelCount();i++){
			String testStr = "";
			for(int j=0;j<testBuff.getType();j++){
				int temp = 0;
				temp += ((int)(contains[i*testBuff.getType()+j]) & 0xFF);
				testStr = testStr + "[" + j + "]: " + temp  + ", ";
			}
			System.out.println(testStr);
		}
		while(running){
			render();
			can.getBufferStrategy().getDrawGraphics().dispose();
			can.getBufferStrategy().show();
			
		}
	}
	
	private void render(){
		can.paint(can.getBufferStrategy().getDrawGraphics());
		
		
		testBuff.fill(new byte[]{(byte)0xFF,(byte)((tempTime%256) - Math.random()*170),(byte)(byte)((tempTime%256) - Math.random()*170),(byte)(byte)((tempTime%256) - Math.random()*170)});
		Drawer.drawBuffer((int)(tempTime%256), 0, testBuff);
		/*	testBuff.fill(new byte[]{(byte)0xFF,(byte)((tempTime+32)%256),(byte)0,(byte)0});
		Drawer.drawBuffer((tempTime+32)%256, 0, testBuff);
		testBuff.fill(new byte[]{(byte)0xFF,(byte)((tempTime+64)%256),(byte)0,(byte)0});
		Drawer.drawBuffer((tempTime+64)%256, 0, testBuff);
	*/	
		tempTime+=0.01;
		
	}
	public static void main(String args[]){
		new Tester(640,480);
	}
}
