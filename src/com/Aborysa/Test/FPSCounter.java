package com.Aborysa.Test;

public class FPSCounter {
	private long time = 0;
	private double dTime = 0;
	private double FPS = 0;
	
	public FPSCounter() {
	}
	public double getFPS(){
		return FPS;
	}
	public void update(){
		dTime = (System.nanoTime() - time);
		time = System.nanoTime();
		FPS = 1000000000/dTime;
	}
	
}
