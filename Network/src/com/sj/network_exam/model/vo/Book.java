package com.sj.network_exam.model.vo;

import java.io.Serializable;

// 직렬화를 선언한다.
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3725038239658757184L;

	// 필드 선언
	private String title;
	private int price;

	// 기본 생성자
	public Book() {
		// TODO Auto-generated constructor stub
	}

	// 매개변수 생성자
	public Book(String title, int price) {
		this.title = title;
		this.price = price;
	}

	// getter/setter
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	// toString
	@Override
	public String toString() {
		return title + "(" + price + ")";
	}

}
