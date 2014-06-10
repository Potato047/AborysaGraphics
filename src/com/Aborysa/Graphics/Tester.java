package com.Aborysa.Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tester {
	JFrame frame;
	Canvas can;
	Dimension size;
	boolean running = true;
	DrawingBuffer testBuff = new DrawingBuffer(1024,4,32);
	DrawingBuffer testBuff2 = new DrawingBuffer(1024,4,32);
	DrawingBuffer testBuff3 = new DrawingBuffer(1024,4,32);
	
	DrawingBuffer tempBuffer;
	BufferedImage tempImage;
	BufferStrategy bufferStrat;
	BlendMode bMode = new BlendMode(BlendMode.ADD);
	float tempTime = 0;
	public Tester(int width, int height){
		frame = new JFrame("Test");
		can = new Canvas();
		size = new Dimension(width,height);
		tempBuffer = new DrawingBuffer(width*height,4,width);
		tempImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
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
		Drawer.setBlendMode(bMode);
		tempBuffer.fill(new byte[]{0,0,0,0});
		testBuff.fill(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0,(byte)0});
		testBuff2.fill(new byte[]{(byte)0xFF,(byte)0,(byte)0xFF,(byte)0});
		testBuff3.fill(new byte[]{(byte)0xFF,(byte)0,(byte)0,(byte)0xFF});
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
		Drawer.setDepth((byte)0xF);
		Drawer.setTarget(tempImage);
		Drawer.setBlendMode(new BlendMode(0));
		Drawer.drawBuffer(0, 0, tempBuffer);
		Drawer.setBlendMode(new BlendMode(BlendMode.ADD));
		//Drawer.drawBuffer(64, 64, testBuff);
		//Drawer.drawBuffer(64+16, 64, testBuff2);
		//Drawer.drawBuffer(64+16, 64+16, testBuff3);
		Drawer.drawBuffer(64, 64+16, testBuff);
		Drawer.setTarget(can);
		Drawer.drawImage(0, 0, tempImage);		
	}
	public static void main(String args[]){
		new Tester(640,480);
	}
}
