package com.example.gxufl2.global;
/**
 * ������������Ҫ�õ��ı���
 * @author cookie1 ����cookie1
 * @author cookie2 ����cookie2
 */
public class Global {
	private static String cookie1="";
	private static String cookie2="";
	public void setcookie1(String cookie){
		cookie1=cookie;
	}
	public void setcookie2(String cookie){
		cookie2=cookie;
	}
	public String getcookie1(){
		return cookie1;
	}
	public String getcookie2(){
		return cookie2;
	}
}
