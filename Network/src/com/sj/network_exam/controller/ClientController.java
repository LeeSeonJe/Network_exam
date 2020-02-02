package com.sj.network_exam.controller;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.sj.network_exam.model.vo.Book;

public class ClientController {
	public void fileSave() {
		// Book을 제네릭한 리스트 생성
		ArrayList<Book> bookList = new ArrayList<Book>();
		// Book 객체 리스트 저장
		bookList.add(new Book("자바를 잡아라", 30000));
		bookList.add(new Book("오라클 정복", 35000));
		bookList.add(new Book("웹표준 2.0", 27500));
		bookList.add(new Book("자바 Servlet/JSP", 28000));
		bookList.add(new Book("ajax 사용법", 15000));
		// 저장된 bookList 정보 출력확인
		System.out.println("=====fileSave=====");
		for (Book b : bookList) {
			System.out.println(b);
		}

		// 객체를 books.dat 파일에 저장 출력하기 위한 기본 스트림과 보조 스트림 객체 생성
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("books.dat"));) {
			// try~with~resource문으로 자동 close 처리
			for (Book b : bookList) {
				oos.writeObject(b);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void fileOpen() {
		ArrayList<Book> bookList = new ArrayList<Book>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("books.dat"));) {
			while (true) {
				bookList.add((Book) ois.readObject());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EOFException e) {
			System.out.println("=====fileOpen=====");
			for (Book b : bookList) {
				System.out.println(b);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void fileUpload() {
		int port = 3000;
		try {
			String server = InetAddress.getLocalHost().getHostAddress();
			Socket socket = new Socket(server, port);
			if (socket != null) {
				InputStream input = socket.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("books.dat"));
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(input));
				try {
					while (true) {
						Book b = (Book) ois.readObject();
						oos.writeObject(b);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EOFException e) {
					oos.writeObject(null);
					oos.flush();

					String massage = br.readLine();
					if (massage.equals("exit")) {
						System.out.println("exit를 받음. 종료");
						ois.close();
						oos.close();
						socket.close();
					}
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
