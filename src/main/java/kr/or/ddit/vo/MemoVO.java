package kr.or.ddit.vo;

import java.io.Serializable;

public class MemoVO implements Serializable {
	private Integer code;
	private String writer;
	private String content;
	private String date;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "MemoVO [code=" + code + ", writer=" + writer + ", content=" + content + ", date=" + date + "]";
	}
	
}
