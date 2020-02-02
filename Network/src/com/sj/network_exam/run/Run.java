package com.sj.network_exam.run;

import com.sj.network_exam.controller.ClientController;

public class Run {
	public static void main(String[] args) {
		ClientController cc = new ClientController();
		cc.fileSave();
		System.out.println();
		cc.fileOpen();
	}
}
