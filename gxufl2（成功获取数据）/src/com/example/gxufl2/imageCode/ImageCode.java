package com.example.gxufl2.imageCode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.example.gxufl2.ScheduleActivity;
import com.example.gxufl2.MD5.md5;
/**
 * ��ȡ��վ����֤��
 * @author ImagesCode
 *
 */
public class ImageCode {
	HttpClient client = new DefaultHttpClient();
	private static List<Cookie> cook ;
	private static String cookie1="";
	private static String cookie2="";
	private static String COOKIE="";
	 public Bitmap getcode() throws Exception {
			HttpGet get = new HttpGet(
					"http://jw.gxufl.com/sys/ValidateCode.aspx"); // 
			get.setHeader("Cookie", COOKIE); 
			HttpResponse httpResponse = client.execute(get);
			cook = ((AbstractHttpClient) client).getCookieStore().getCookies();
			int i=cook.size();
			//cookie1=cook.get(0).getValue();
			//cookie2=cook.get(1).getValue();
			//cookie = ((AbstractHttpClient) client).getCookieStore().getCookies().get(1).getValue();
			//Log.e("������cookie1",cookie1);
			//Log.e("������cookie2",cookie2);
			//Log.e("������cookie_Size",i+"��");
			byte[] bytes = EntityUtils.toByteArray(httpResponse.getEntity());
			String str=new String(bytes);
			Log.e("��", str);
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			return bitmap;
		}
	 
	 
	 /**
	  * ͨ��Post��֤��¼
	  * @param url
	  * @param map
	  * @return
	  */
	 public String getWebWithPost(String loginURL,Map<String,String> map) {
		 HttpPost httpRequest = new HttpPost(loginURL);
		 String name=map.get("txt_asmcdefsddsd");//��ȡ�û���
		 String pass=map.get("txt_pewerwedsdfsdff");//��ȡ����
		 String code=map.get("txt_sdertfgsadscxcadsads");//��ȡ��֤��
		 
		 md5 m=new md5();
		 String codeMD5=m.getMD5(m.getMD5(code.toUpperCase()).substring(0, 30)+"13830").substring(0, 30);
		 String usepassMD5=m.getMD5(name.concat(m.getMD5(pass).substring(0, 30)+"13830")).substring(0,30);
		 Log.e("MD5+��֤��", codeMD5);
		 Log.e("MD5+�û�������", usepassMD5);
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
		 params.add(new BasicNameValuePair("txt_asmcdefsddsd",name));//�����û���
		 params.add(new BasicNameValuePair("txt_pewerwedsdfsdff",pass));//��������
		 params.add(new BasicNameValuePair("txt_sdertfgsadscxcadsads",code));//������֤��
		 params.add(new BasicNameValuePair("fgfggfdgtyuuyyuuckjg",codeMD5));//������֤��
		 params.add(new BasicNameValuePair("dsdsdsdsdxcxdfgfg",usepassMD5));//����

		 params.add(new BasicNameValuePair("Sel_Type","STU"));//����
		 //params.add(new BasicNameValuePair("typeName","STU000"));//����
		 params.add(new BasicNameValuePair("pcInfo","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729; Tablet PC 2.0)x860 SN:NULL"));//����
		 
		/* for(String key : map.keySet()) {
			params.add(new BasicNameValuePair(key, map.get(key))); //��Ӳ���
		 }*/
		 /*Log.e("�û���",name);
		 Log.e("����",pass);
		 Log.e("��֤��",code);
		 Log.e("������cookie11",cookie1);
		 Log.e("������cookie22",cookie2);
		 Log.e("������","name=value; __jsluid="+cookie2+"; ASP.NET_SessionId="+cookie1);*/
		httpRequest.setHeader("Cookie", 
				"name=value; Hm_lvt_2d57a0f88eed9744a82604dcfa102e49=1437955437,1437955450; __jsluid="+cookie2+"; ASP.NET_SessionId="+cookie1);//����Cookie
		//����һϵ�е�����ͷ������403
		httpRequest.setHeader("Accept", "text/html, application/xhtml+xml, */*");
		httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpRequest.setHeader("DNT", "1");
		httpRequest.setHeader("Host", "jw.gxufl.com");
		httpRequest.setHeader("Proxy-Connection", "Keep-Alive");
		httpRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
		httpRequest.setHeader("Referer", "http://jw.gxufl.com/_data/home_login.aspx");
		try {
			//�����������
			httpRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			//����post����
			HttpResponse httpResponse = client.execute(httpRequest); 	
			int state=httpResponse.getStatusLine().getStatusCode(); 
			//��¼-״̬-�ж�
			if (state== 200) { 	//״̬��
				Log.e("��¼�ɹ�-----", "Yes������");
				
				return  readFromStream(httpResponse.getEntity().getContent());
				
			}else if(state==403){
				Log.e("��¼ʧ��----", "��Դ�����á����������ͻ������󣬵�'�ܾ�'������");
			}
			else {
				Log.e("��¼ʧ��----", "ERROR");
				return "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	 /**
	  * ��ȡ������ҳ��Դ����
	  * @param inputStream
	  * @return
	  * @throws Exception
	  */
	 public static String readFromStream(InputStream inputStream)
				throws Exception {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
		String data = "";
		while ((data = br.readLine()) != null) {
			sb.append(data);
		}		
		return sb.toString(); 
	}	 	
	 
	 public  Bitmap getcookie() throws Exception
	 {
		 HttpGet get = new HttpGet(
					"http://jw.gxufl.com/_data/home_login.aspx"); // 
			get.setHeader("Cookie", ""); 
			HttpResponse httpResponse = client.execute(get);
			cook = ((AbstractHttpClient) client).getCookieStore().getCookies();
			int i=cook.size();
			cookie1=cook.get(0).getValue();
			cookie2=cook.get(1).getValue();
			client = new DefaultHttpClient();	
			COOKIE="name=value; __jsluid="+cookie2+"; ASP.NET_SessionId="+cookie1;
			 Log.e("COOKIE12111111111111", COOKIE);
			 Bitmap bitmap=getcode();
			 return bitmap;
	 }
	
	 
	
	 public String getSco(){
		 Log.e("�ҵĳɼ����3","����ҳɼ��Ƕ�����");
			HttpGet get = new HttpGet(
					"http://jw.gxufl.com/xscj/Stu_MyScore.aspx");
			get.setHeader("Cookie","name=value; Hm_lvt_2d57a0f88eed9744a82604dcfa102e49=1437955437,1437955450; __jsluid="+cookie2+"; ASP.NET_SessionId="+cookie1);
			try {
				HttpResponse httpResponse = client.execute(get);
				String scon=readFromStream(httpResponse.getEntity().getContent());
				return scon;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
