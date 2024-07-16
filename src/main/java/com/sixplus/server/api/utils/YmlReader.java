package com.sixplus.server.api.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

public class YmlReader {
	public static Map<String, Object> readYml(String filePath) throws FileNotFoundException {
		Yaml y = new Yaml();
		Reader yamlFile = new FileReader(filePath);
		Map<String, Object> yamlMaps = y.load(yamlFile);
		return yamlMaps;
	}
}
