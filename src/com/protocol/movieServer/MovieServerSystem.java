package com.protocol.movieServer;

import java.sql.*;
import java.net.*;
import java.io.*;

public class MovieServerSystem {
	private static final int SERVER_PORT = 5000;

	MovieServerSystem() throws ClassNotFoundException, SQLException, IOException {

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		new MovieServerSystem().start();
	}

	// ���ι��� ���� ���� ��κ��� �ý��� �����帧
	public void start() {
		ServerSocket sSocket;
		Socket socket;

		try {
			sSocket = new ServerSocket();
			String localHostAddress = InetAddress.getLocalHost().getHostAddress();
			sSocket.bind(new InetSocketAddress(localHostAddress, SERVER_PORT));
			System.out.println("[server] binding! \n[server] address:" + localHostAddress + ", port:" + SERVER_PORT);
			System.out.println("Ŭ���̾�Ʈ ���� �����...");
			while (true) {
				socket = sSocket.accept();
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "���� ���� �Ͽ����ϴ�.");
				MovieServer serverThread = new MovieServer(socket);
				serverThread.start();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}