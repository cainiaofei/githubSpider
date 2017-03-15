package cn.nju.GithubInfoSpider.ToolClass;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cn.nju.GithubInfoSpider.BaseClass.Issue;

public class IssueInfoSpider {
	
	/**
	 * the method which exposed to users.
	 * after this method, the all issue will be stored in map, the url is regular.
	 * @param issueMap         the container which used to store issue elements 
	 * @param baseUrl          the base url
	 * @param start            the start page number
	 * @param end              the end page number
	 * @throws                 maybe throws some exception which related with internet connection.
	 * */
	public void doTask(Map<String,Issue> issueMap,String baseUrl,
			int start,int end){
		for(int i = start; i <= end;i++){
			System.out.println(i);
			excuteTask(issueMap,baseUrl+i);
		}
	}

	private void excuteTask(Map<String, Issue> issueMap, String url) {
		//the conn seemed not need to close explicit, why????? wait a day I will see the source code
		Connection conn = Jsoup.connect(url);
		conn.timeout(300000);
		try {
			Document doc = conn.get();
			Issue issue = process(doc);
			if(issue!=null){
				issueMap.put(issue.getIdentifier(), issue);
			}
		} catch (IOException e) {
			for(int i = 0; i < 20; i++){
				System.out.println("exception come up!!!!!!!!!!!!!!!!!!!!!!!!!");
			}
			System.exit(-1);
			e.printStackTrace();
		}
	}

	private Issue process(Document doc) {
		String title = doc.title();
		String identifier = getIdentifier(title);
		if(identifier==null){
			return null;
		}
		String summary = getSummary(title);
		/*
		 * through analysis the html code of web page.
		 * the most beautiful method is ..Id, for Id is unique.
		 */
		Element target = doc.getElementById("type-val");
		String type = target.text();
		target = doc.getElementById("description-val");
		String description = (target!=null)?target.text():"";
		Issue issue = new Issue();
		issue.setIdentifier(identifier);
		issue.setContent(summary+" "+description);
		issue.setType(type);
		return issue;
	}

	/**
	 * @param            title like [WELD-50] If a Web Bean.... 
	 * @return           summary, like If a Web Bean...
	 * */
	private String getSummary(String title) {
		int start = title.indexOf(']');
		if(start==-1){
			return null;
		}
		int end = title.lastIndexOf('-');
		return title.substring(start+1,end);
	}
	

	/**
	 * NOTE:I assume there are all common scene, so maybe exist bug.
	 * @param title              the title of web page, 
	 *    e.g. [WELD-50] If a Web Bean....
	 * @return identifier        the identifier like WELD-50          
	 * */
	private String getIdentifier(String title) {
		int start = title.indexOf('[');
		if(start==-1){
			return null;
		}
		int end = title.indexOf(']');
		if(end<=start){
			return null;
		}
		return title.substring(start+1, end).trim();
	}
}
