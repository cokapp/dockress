package com.cokapp.dockress.socket.ios;

import java.io.PipedOutputStream;

public class Send implements Runnable {
	private PipedOutputStream out = null;

	public Send() {
		this.out = new PipedOutputStream();
	}

	@Override
	public void run() {
		
	}

	public PipedOutputStream getOut() {
		return out;
	}
}