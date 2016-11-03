package com.example.gxufl2.jsouphtml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlJsoup {

	StringBuilder htmlAy=new StringBuilder();//��������������
	
	/**
	 * ����HTML ���ص�¼���״̬��
	 * @param html
	 * @return String ��Ϣ
	 */
	public String htmlAnalysis(String html){
		Document doc = Jsoup.parse(html);
		Elements linkStrs=doc.getElementsByTag("span");
		for(Element linkStr:linkStrs){  
        	/**/
        	//
            //text()�õ��ı�ֵ		attr(String key)  ���Ԫ�ص�����	 getElementsByTag:ͨ����ǩ���Ԫ��
            String title=linkStr.getElementsByTag("span").text();
            htmlAy.append(title);
            System.out.println("����:"+title);  
            
        } 
		return htmlAy.toString();
		
	}
}
