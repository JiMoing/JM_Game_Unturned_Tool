package com.clb.common.model;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil {

	public static void close(Closeable ... cs){
		if(null==cs){
			return;
		}
		for(Closeable c:cs){
			if(null!=c){
				try {
					c.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
