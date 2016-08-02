package com.cokapp.dockress.socket.ios;

import java.io.IOException;
import java.io.InputStream;

public class InputReadThead implements Runnable {

	private InputStream in;
	private String cache;

	public InputReadThead(InputStream in) {
		this.in = in;
		this.cache = "";
	}

	@Override
	public void run() {
		try {
			int count = 0;
			while (count == 0) {
				count = this.in.available();
			}
			byte[] b = new byte[count];
			this.in.read(b);

			this.cache += b.toString();
			System.out.println("+++++++++++++++++++++++" + this.cache);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getCache() {
		String rst = this.cache;
		this.cache = "";
		return rst;
	}

}
