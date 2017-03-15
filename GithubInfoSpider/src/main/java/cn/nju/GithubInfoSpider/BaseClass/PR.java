package cn.nju.GithubInfoSpider.BaseClass;

import java.io.Serializable;
import java.util.List;

public class PR implements Serializable{
	private List<String> identifierList;
	private String content;
	private List<String> relatedFiles;
	public List<String> getIdentifierList() {
		return identifierList;
	}

	public void setIdentifierList(List<String> identifierList) {
		this.identifierList = identifierList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getRelatedFiles() {
		return relatedFiles;
	}

	public void setRelatedFiles(List<String> relatedFiles) {
		this.relatedFiles = relatedFiles;
	}

	@Override
	public String toString(){
		return "["+identifierList+","+content+","+relatedFiles+"]";
	}
}
