package com.cokapp.dockress.socket.ios;

public class OnReceiveImpl implements OnReceive {

	@Override
	public void receive(String str) {
		System.out.println("receive: " + str);
	}

}
