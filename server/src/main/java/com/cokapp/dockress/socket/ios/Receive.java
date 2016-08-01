package com.cokapp.dockress.socket.ios;

import java.io.IOException;
import java.io.PipedInputStream;

public class Receive implements Runnable {
	private PipedInputStream in = null;

	public Receive(OnReceive onReceive) {
		this.in = new PipedInputStream();
		this.onReceive = onReceive;
	}

	private OnReceive onReceive;

	@Override
	public void run() {
		byte[] b = new byte[1024];
		try {
			int len = this.in.read(b);
			onReceive.receive(new String(b, 0, len));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PipedInputStream getIn() {
		return in;
	}
}