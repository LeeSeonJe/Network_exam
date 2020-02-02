package com.sj.network_exam.run;

import com.sj.network_exam.controller.ServerController;

public class ServerRun {
	public static void main(String[] args) {
		ServerController sc = new ServerController();
		sc.startServer();
	}
}
