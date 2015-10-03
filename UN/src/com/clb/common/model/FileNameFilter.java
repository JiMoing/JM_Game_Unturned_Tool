package com.clb.common.model;

import java.io.File;
import java.io.FileFilter;

public class FileNameFilter implements FileFilter {

	String extension = "";

	public FileNameFilter(String suffix) {
		extension = suffix;
	}

	@Override
	public boolean accept(File dir) {
		return dir.isDirectory() || dir.getName().endsWith(extension);
	}
}
