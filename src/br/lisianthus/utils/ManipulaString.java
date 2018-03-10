package br.lisianthus.utils;

public class ManipulaString {
	public static String capitularizar(String str){
		if(str==null) return null;
		return str.substring(0,1).toUpperCase()+str.substring(1);
	}
}
