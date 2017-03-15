package cn.nju.GithubInfoSpider.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.nju.GithubInfoSpider.BaseClass.Issue;
import cn.nju.GithubInfoSpider.ToolClass.IssueInfoSpider;

public class IssueInfoSpiderTest {
	private Map<String,Issue> issueMap;
	private final String baseUrl;
	private int start;
	private int end;
	private IssueInfoSpider issueInfoSpider;
	
	public IssueInfoSpiderTest(Map<String,Issue> issueMap,String baseUrl,
			int start,int end){
		this.issueMap = issueMap;
		this.baseUrl = baseUrl;
		this.start = start;
		this.end = end;
		issueInfoSpider = new IssueInfoSpider();
	}
	
	public static void main(String[] args){
		Map<String,Issue> issueMap = new HashMap<String,Issue>();
		String baseUrl = "https://issues.jboss.org/browse/WELD-";
		int start = 1;
		int end = 10;//2350
		IssueInfoSpiderTest demo = new IssueInfoSpiderTest(issueMap,baseUrl,start,end);
		demo.test();
	}
	
	public void test(){
		issueInfoSpider.doTask(issueMap, baseUrl, start, end);
		print(issueMap);
		System.out.println("");
	}

	private void print(Map<String, Issue> issueMap) {
		Iterator<String> ite = issueMap.keySet().iterator();
		while(ite.hasNext()){
			String issueIdentifier = ite.next();
			Issue issue = issueMap.get(issueIdentifier);
			String type = issue.getType();
			String content = issue.getContent();
			System.out.println(issueIdentifier+"   "+type+"    "+content);
		}
	}
}
