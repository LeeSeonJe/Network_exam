package com.sj.network_exam.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.sj.network_exam.model.vo.Book;

public class ServerController {
	public void startServer() {
		int port = 3000;
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("클라이언트 요청 대기중 :::");

			Socket client = server.accept();

			InputStream input = client.getInputStream();
			OutputStream output = client.getOutputStream();

			ObjectInputStream ois = new ObjectInputStream(input);
			PrintWriter pw = new PrintWriter(output);
			Scanner sc = new Scanner(System.in);
			Book b = null;
			while (true) {
				b = (Book) ois.readObject();
				if (b != null) {
					System.out.println(b);
				} else {
					String exit = sc.next();
					pw.write(exit);
					break;
				}
			}
			pw.close();
			ois.close();
			server.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
