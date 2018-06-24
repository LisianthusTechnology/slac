package br.lisianthus.utils;

public class ManipulaString {
	public static String capitularizar(String str){
		if(str==null) return null;
		return str.substring(0,1).toUpperCase()+str.substring(1);
	}
	
	public static long converteParaLong(String str){
		long idlong = str != null && !str.equals("") ? Long.parseLong(str) : null;
		return idlong;
	}
	
	public static Integer converteParaInt(String str){
		Integer idint = str != null && !str.equals("") ? Integer.parseInt(str) : null;
		return idint;
	}
}
