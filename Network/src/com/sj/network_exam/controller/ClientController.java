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
		// Book�� ���׸��� ����Ʈ ����
		ArrayList<Book> bookList = new ArrayList<Book>();
		// Book ��ü ����Ʈ ����
		bookList.add(new Book("�ڹٸ� ��ƶ�", 30000));
		bookList.add(new Book("����Ŭ ����", 35000));
		bookList.add(new Book("��ǥ�� 2.0", 27500));
		bookList.add(new Book("�ڹ� Servlet/JSP", 28000));
		bookList.add(new Book("ajax ����", 15000));
		// ����� bookList ���� ���Ȯ��
		System.out.println("=====fileSave=====");
		for (Book b : bookList) {
			System.out.println(b);
		}

		// ��ü�� books.dat ���Ͽ� ���� ����ϱ� ���� �⺻ ��Ʈ���� ���� ��Ʈ�� ��ü ����
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("books.dat"));) {
			// try~with~resource������ �ڵ� close ó��
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
						System.out.println("exit�� ����. ����");
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
