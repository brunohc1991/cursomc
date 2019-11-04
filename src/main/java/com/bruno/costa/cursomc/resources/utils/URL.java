package com.bruno.costa.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	public static List<Long> decodeLongList(String s){
		String ver[] = s.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String string : ver) {
			list.add(Long.valueOf(string));
		}
		return list;
	}
	
	public static List<Long> decodeLongListLambda(String s){
		return Arrays.asList(s.split(",")).stream().map(x -> Long.valueOf(x)).collect(Collectors.toList());
	}
	
	public static String decodeParam(String argumento) {
		try {
			return URLDecoder.decode(argumento, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
