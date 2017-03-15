package cn.nju.GithubInfoSpider.ToolClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.nju.GithubInfoSpider.BaseClass.Issue;
import cn.nju.GithubInfoSpider.BaseClass.PR;

public class WriteExcel {
	/*
	 * write related information into excel.
	 * issueIdentifier------pr-------related file
	 * @param                 prMap 
	 * @param                 issueMap
	 * @param                 targetFile
	 * @throws                IOException
	 * */
	public void write(Map<String,PR> prMap,Map<String,Issue> issueMap,
			File targetFile) throws IOException{
		//first write the information into .csv file then translate excel through excel api
		BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile));
		//write the header ---issueIdentifier---type---prNumber---related Files
		bw.write("issueIdentifier;");
		bw.write("type;");
		bw.write("prNumber;");
		bw.write("related Files");
		bw.newLine();
		Iterator<String> ite = prMap.keySet().iterator();
		while(ite.hasNext()){
			String prNumber = ite.next();
			PR curPR = prMap.get(prNumber);
			List<String> identifierList = curPR.getIdentifierList();
			for(String identifier:identifierList){
				if(issueMap.containsKey(identifier)){
					Issue curIssue = issueMap.get(identifier);
					bw.write(identifier+";");
					bw.write(curIssue.getType()+";");
					bw.write(prNumber+";");
					List<String> relatedFileList = curPR.getRelatedFiles();
					for(int i = 0; i < relatedFileList.size()-1;i++){
						bw.write(relatedFileList.get(i)+" ");
					}
					bw.write(relatedFileList.get(relatedFileList.size()-1));
				}
				else{
					continue;
				}
			}
			bw.newLine();
		}
		bw.close();
	}

	public void store(Map<Issue, List<PR>> issueMapPR, File targetFile) 
			throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile));
		//write file header
		StringBuilder sb = new StringBuilder();
		sb.append("issueIdentifier;");
		sb.append("issueType;");
		sb.append("issueContent;");
		sb.append("relatedPRNumber;");
		sb.append("relatedFiles");
		bw.write(sb.toString());
		bw.newLine();
		
		Iterator<Issue> ite = issueMapPR.keySet().iterator();
		while(ite.hasNext()){
			StringBuilder content = new StringBuilder();
			Issue issue = ite.next();
			content.append(issue.getIdentifier()+";");
			content.append(issue.getType()+";");
			content.append(issue.getContent()+";");
			StringBuilder relatedPRNumber = new StringBuilder();
			StringBuilder relatedFiles = new StringBuilder();
			List<PR> prList = issueMapPR.get(issue);
			for(PR pr:prList){
				addInfo(relatedPRNumber,pr.getIdentifierList());
				addInfo(relatedFiles,pr.getRelatedFiles());
			}
			content.append(relatedPRNumber+";");
			content.append(relatedFiles);
			bw.write(content.toString());
			bw.newLine();
		}
		bw.close();
	}

	private void addInfo(StringBuilder sb, List<String> list) {
		for(String str:list){
			sb.append(str+" ");
		}
	}
}






