package com.sixplus.server.api.utils;


import java.io.EOFException;
import java.io.File;
import java.io.IOException;


public class FileUtil {
	public static void createFile(File file) throws IOException {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			if (!file.createNewFile()) {
				throw new EOFException("Temp File Creation Error");
			}
		}
	}
}
