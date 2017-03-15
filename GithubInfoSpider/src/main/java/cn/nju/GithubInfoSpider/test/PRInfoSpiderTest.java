package cn.nju.GithubInfoSpider.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.nju.GithubInfoSpider.BaseClass.PR;
import cn.nju.GithubInfoSpider.ToolClass.PRInfoSpider;

public class PRInfoSpiderTest {
	private Map<String,PR> prMap;
	private String baseUrl;
	private int start;
	private int end;
	private PRInfoSpider prInfoSpider;
	
	public PRInfoSpiderTest(Map<String,PR> prMap,String baseUrl,
			int start,int end){
		this.prMap = prMap;
		this.baseUrl = baseUrl;
		this.start = start;
		this.end = end;
		prInfoSpider = new PRInfoSpider();
	}
	
	public static void main(String[] args){
		Map<String,PR> PRMap = new HashMap<String,PR>();
		String baseUrl = "https://github.com/weld/core/pull/";
		int start = 1;
		int end = 16;//1617
		PRInfoSpiderTest demo = new PRInfoSpiderTest(PRMap,baseUrl,start,end);
		demo.test();
	}
	
	public void test(){
		prInfoSpider.doTask(prMap, baseUrl, start, end);
		print(prMap);
		System.out.println("");
	}

	private void print(Map<String, PR> prMap) {
		Iterator<String> ite = prMap.keySet().iterator();
		while(ite.hasNext()){
			System.out.println("----------------------------------");
			String prNumber = ite.next();
			PR pr = prMap.get(prNumber);
			List<String> relatedFiles = pr.getRelatedFiles();
			List<String> identifierList = pr.getIdentifierList();
			System.out.print(prNumber+" ");
			for(String str:identifierList){
				System.out.print(str+" ");
			}
			for(String str:relatedFiles){
				System.out.print(str+" ");
			}
		}
	}
}
