package cn.nju.GithubInfoSpider.mainClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.nju.GithubInfoSpider.BaseClass.Issue;
import cn.nju.GithubInfoSpider.BaseClass.PR;
import cn.nju.GithubInfoSpider.ToolClass.IssueInfoSpider;
import cn.nju.GithubInfoSpider.ToolClass.PRInfoSpider;
import cn.nju.GithubInfoSpider.ToolClass.WriteExcel;

public class Main {
	//retrieve issueInfo and PRInfo respectively.
	private IssueInfoSpider issueSpider;
	private PRInfoSpider prSpider;
	private WriteExcel writeExcel;
	
	public Main(){
		issueSpider = new IssueInfoSpider();
		prSpider = new PRInfoSpider();
		writeExcel = new WriteExcel();
	}
	
	/*
	 * 
	 * */
	public void process(){
		Map<String,Issue> issueMap = new HashMap<String,Issue>();
		/**
		 * variable reuse
		 * as for issue info  the base url the start and end
		 * */
		String baseUrl = "https://issues.jboss.org/browse/WELD-";
		int start = 1;
		int end = 2350;//2350
		issueSpider.doTask(issueMap, baseUrl, start, end);
		
		/////serialize issueMapPR
		serialize(issueMap,"issueMapPR.out");
		
		
		
		/**
		 * as for PR info the base url the start and end
		 */
		baseUrl = "https://github.com/weld/core/pull/";
		start = 1;
		end = 1617;//1617
		Map<String,PR> prMap = new HashMap<String,PR>();
		prSpider.doTask(prMap, baseUrl, start, end);
		
		Map<Issue,List<PR>> issueMapPR = generateMap(prMap,issueMap);
		
		String destination = "D:/issueMapPR.txt";
		File targetFile = new File(destination);
		try {
			writeExcel.store(issueMapPR,targetFile);
			//writeExcel.write(prMap, issueMap, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void serialize(Map<String,Issue> issueMap,String fileName) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(fileName));
			out.writeObject(issueMap);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map<Issue, List<PR>> generateMap(Map<String, PR> prMap,
			Map<String, Issue> issueMap) {
		Map<Issue,List<PR>> issueMapPR = new HashMap<Issue,List<PR>>();
		Iterator<String> ite = prMap.keySet().iterator();
		while(ite.hasNext()){
			String prNumber = ite.next();
			PR pr = prMap.get(prNumber);
			List<String> issueIdentifierList = pr.getIdentifierList();
			for(String issueIdentifier:issueIdentifierList){
				if(!issueMap.containsKey(issueIdentifier)){
					continue;
				}
				else{
					Issue issue = issueMap.get(issueIdentifier);
					if(issueMapPR.containsKey(issue)){
						issueMapPR.get(issue).add(pr);
					}
					else{
						List<PR> prList = new LinkedList<PR>();
						prList.add(pr);
						issueMapPR.put(issue, prList);
					}
				}
				
			}
		}
		return issueMapPR;
	}
	
	public static void main(String[] args){
		Main m = new Main();
		m.process();
	}
	
}
