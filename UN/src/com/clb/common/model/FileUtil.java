package com.clb.common.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static void copyFiles(String dirPath, String newDirPath, String suffix) {
		List<File> list = getAllFile(dirPath, suffix);
		for (File file : list) {
			copyFile(file, file.getAbsolutePath().replace(dirPath, newDirPath));
		}
	}

	public static void copyFile(File file, String path) {
		FileOutputStream outputStream = null;
		try {
			int byteread = 0;
			if (file.exists()) {
				InputStream inStream = new FileInputStream(file);
				(new File(path)).getParentFile().mkdirs();
				outputStream = new FileOutputStream(path);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(outputStream);
		}
	}

	public static void writerTxt(String filePath, String txt) {
		BufferedWriter writer = null;
		try {
			File file = new File(filePath);
			file.getParentFile().mkdirs();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
			writer.append(txt);
			writer.newLine();
			writer.append(txt);
			writer.flush(); // 全部写入缓存中的内容
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(writer);
		}
	}

	public static void writerTxt(String filePath, List<String> list) {
		BufferedWriter writer = null;
		try {
			File file = new File(filePath);
			file.getParentFile().mkdirs();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
			for (String s : list) {
				writer.append(s);
				writer.newLine();
			}
			writer.flush(); // 全部写入缓存中的内容
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(writer);
		}
	}

	public static void readTxtFile(String filePath) {
		BufferedReader reader = null;
		try {
			File file = new File(filePath);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String lineTxt = null;
			while ((lineTxt = reader.readLine()) != null) {
				System.out.println(lineTxt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(reader);
		}
	}
	
	public static List<File> getAllFile(String filePath, String suffix) {
		return getAllFile(new File(filePath),suffix);
	}

	public static List<File> getAllFile(File dir, String suffix) {
		List<File> list = new ArrayList<File>();
		getAllFile(dir, new FileNameFilter(suffix), list);
		return list;
	}

	public static List<File> getAllFile(String filePath, FileFilter filter) {
		List<File> list = new ArrayList<File>();
		getAllFile(new File(filePath), filter, list);
		return list;
	}

	public static void getAllFile(File file, FileFilter filter, List<File> list) {
		if (file.isDirectory()) {
			File[] fs = file.listFiles(filter);
			for (File f : fs) {
				getAllFile(f, filter, list);
			}
		} else {
			list.add(file);
		}
	}
}
