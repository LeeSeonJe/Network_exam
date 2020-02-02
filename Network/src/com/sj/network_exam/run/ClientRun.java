package com.sj.network_exam.run;

import com.sj.network_exam.controller.ClientController;

public class ClientRun {
	public static void main(String[] args) {		
		ClientController cc = new ClientController();
		cc.fileUpload();
	}
}
