package com.cokapp.dockress.socket.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.cokapp.dockress.docker.DockerManager;
import com.cokapp.dockress.socket.ios.OnReceiveImpl;
import com.cokapp.dockress.socket.ios.Receive;
import com.cokapp.dockress.socket.ios.Send;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.core.command.ExecStartResultCallback;

@ServerEndpoint("/exec")
public class ExecHandler {

	@OnOpen
	public void onOpen(Session session) throws IOException {

		try {
            ExecutorService es = Executors.newFixedThreadPool(4);  
            Send send1 = new Send();  
            Receive receive1 = new Receive(new OnReceiveImpl());  
            //连接管道  
            send1.getOut().connect(receive1.getIn());
            es.execute(send1);
            es.execute(receive1);
	
            Send send2 = new Send();  
            Receive receive2 = new Receive(new OnReceiveImpl());  
            //连接管道  
            send2.getOut().connect(receive2.getIn());
            es.execute(send2);
            es.execute(receive2);
            
			PipedInputStream in = receive2.getIn();
			PipedOutputStream out = send1.getOut();

			session.getUserProperties().put("src", send2.getOut());
			session.getUserProperties().put("dest", receive2.getIn());

			DockerClient dockerClient = DockerManager.getDefault();
			String containerId = "1c27553310d2";

			ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId).withAttachStdout(true)
					.withAttachStdin(true).withTty(true).withCmd("bash").exec();

			dockerClient.execStartCmd(execCreateCmdResponse.getId()).withDetach(false).withStdIn(in)
					.exec(new ExecStartResultCallback(out, out));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@OnMessage
	public void onMessage(Session session, String message) {

		try {
			PipedOutputStream src = (PipedOutputStream) session.getUserProperties().get("src");
			PipedInputStream dest = (PipedInputStream) session.getUserProperties().get("dest");

			System.out.println("send: " + message);

			src.write(message.getBytes());

			//session.getAsyncRemote().sendText();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String readString(InputStream in) {
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(in);
		return reader.next();
	}

}