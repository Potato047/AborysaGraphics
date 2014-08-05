package com.Aborysa.Graphics.Texture;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static BufferedImage loadImage(String filePath){
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}
	public static BufferedImage getImageFromURL(URL imageAddress){
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(imageAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}
	public static BufferedImage[] loadImages(String dirPath){
		BufferedImage[] images = null;
		ArrayList<BufferedImage> tempImgs = new ArrayList<BufferedImage>(25);
		File dir = new File(dirPath);
		String[] files = null;
		int imgCount = 0;
		if (dir.isDirectory()){
			files = dir.list();
			for(int i=0; i< files.length;i++){
				String fileName = files[i].toUpperCase();
				if(fileName.endsWith(".PNG") | fileName.endsWith(".BMP") | fileName.endsWith(".JPG")){
					try {
						tempImgs.add(ImageIO.read(new File(dirPath + "/" + fileName)));
						imgCount++;
					} catch (IOException e) {
						System.err.println("Could not load image: " + fileName + System.lineSeparator() + "in directory: " + dirPath);
						//e.printStackTrace();
					}
				}
			}
			images = new BufferedImage[imgCount];
			tempImgs.toArray(images);
		}
		return images;
	}
}
