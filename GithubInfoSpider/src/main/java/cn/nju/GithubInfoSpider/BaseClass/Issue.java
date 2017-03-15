package cn.nju.GithubInfoSpider.BaseClass;

import java.io.Serializable;

public class Issue implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String identifier;
	private String content;
	private String type;
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString(){
		return "["+identifier+","+type+","+content+"]";
	}
}
