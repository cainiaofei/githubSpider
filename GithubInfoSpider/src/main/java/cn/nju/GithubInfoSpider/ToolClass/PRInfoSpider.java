package cn.nju.GithubInfoSpider.ToolClass;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.nju.GithubInfoSpider.BaseClass.PR;

public class PRInfoSpider {

	/**
	 * the method which exposed to users.
	 * after this method, the all issue will be stored in map, the url is regular.
	 * @param issueMap         the container which used to store issue elements 
	 * @param baseUrl          the base url
	 * @param start            the start page number
	 * @param end              the end page number
	 * @throws                 maybe throws some exception which related with internet connection.
	 * */
	public void doTask(Map<String,PR> PRMap,String baseUrl,
			int start,int end){
		for(int i = start; i <= end;i++){
			int number = i;
			System.out.println(i);
			excuteTask(PRMap,baseUrl+i+"/files",number);
		}
	}

	private void excuteTask(Map<String, PR> pRMap, String url,int number) {
		Connection conn = Jsoup.connect(url);
		conn.timeout(300000);
		try {
			Document doc = conn.get();
			PR pr = process(doc);
			if(pr!=null){
				pRMap.put(number+"", pr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private PR process(Document doc) {
		String title = doc.title();
		//get issue identifier list
		List<String> issueIdentifierList = getIdentifierList(title);
		if(issueIdentifierList.isEmpty()){
			return null;
		}
		
		//get the related file
		List<String> relatedFileList = getRelatedFilesList(doc);
		PR pr = new PR();
		pr.setIdentifierList(issueIdentifierList);
		pr.setRelatedFiles(relatedFileList);
		return pr;
	}

	/**
	 * get related source file names through document
	 * @param           doc
	 * @return          related source file names
	 * */
	private List<String> getRelatedFilesList(Document doc) {
		List<String> fileList = new LinkedList<String>();
		String classValue = "file-header";
		Elements elements = doc.getElementsByClass(classValue);
		for(int i = 0; i < elements.size();i++){
			Element ele = elements.get(i);
			String fileName = ele.attr("data-path");
		    fileList.add(fileName);
		}
		return fileList;
	}

	/**
	 * copy my former code IssueLinkRetrieve
	 * used to retrieve the IdentifierList 
	 * @param        title   the title of this html
	 * @return       identifier list
	 * */
	private List<String> getIdentifierList(String title) {
		List<String> issueIdentifierList = new LinkedList<String>();
		String[] strs = title.split(",|\\[|]|\\(|\\)| |;|\\.|-|/");//split by several separator
		for(int i = 0; i < strs.length;i++){
			strs[i] = strs[i].toUpperCase();
			if(strs[i].startsWith("WELD")){
				if(strs[i].equals("WELD") && i+1!=strs.length&&isNumber(strs[i+1])){
					issueIdentifierList.add(strs[i]+"-"+strs[i+1]);
				}
				else{
					String remain = strs[i].substring(4);
					if(isNumber(remain)){
						issueIdentifierList.add("WELD-"+remain);
					}
				}
			}
		}
		return issueIdentifierList;
	}

	/**
	 * copy my former code IssueLinkRetrieve
	 * @param            curValue
	 * @return           whether the input string is number
	 * */
	private boolean isNumber(String curValue) {
		if(curValue==null||curValue.length()==0){
			return false;
		}
		char[] chs = curValue.toCharArray();
		for(int i = 0; i < chs.length;i++){
			if(chs[i]<'0'||chs[i]>'9'){
				return false; 
			}
		}
		return true;
	}
}

