package com.aiproject.ai;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageLoader {
	public static byte[] load(String pathOrUrl) throws Exception {
		if(pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
			try(InputStream is = new URL(pathOrUrl).openStream()) {
				return is.readAllBytes();
			}
		}else {
			File imgFile = new File(pathOrUrl);
			try(InputStream is = new FileInputStream(imgFile)){
				return is.readAllBytes();
			}
		}
		
	}
}
