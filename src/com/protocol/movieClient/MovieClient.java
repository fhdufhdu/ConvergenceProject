package com.protocol.movieClient;

import java.net.*;
import java.io.*;

public class MovieClient {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		if (args.length < 2)
			System.out.println("���� : " + "java LoginClient �ּ� ��Ʈ��ȣ");

		Socket socket = new Socket("192.168.232.88", 5000); // ���� ������ ���ÿ� ip, port �Ҵ� �� ������ ���� ��û

		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();

		Protocol protocol = new Protocol();
		byte[] buf = protocol.getPacket();

		while (true) {
			is.read(buf);
			int packetType = buf[0];
			protocol.setPacket(packetType, buf);
			if (packetType == Protocol.PT_EXIT) {
				System.out.println("Ŭ���̾�Ʈ ����");
				break;
			}

			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			switch (packetType) {
			case Protocol.SC_RES_CONNECT:
				System.out.println("������ �α��� ���� ��û");
				System.out.print("���̵� : ");
				String id = userIn.readLine();
				System.out.print("��ȣ : ");
				String pwd = userIn.readLine();
				// �α��� ���� ���� �� ��Ŷ ����
				protocol = new Protocol(Protocol.CS_REQ_LOGIN);
				protocol.setId(id);
				protocol.setPassword(pwd);
				System.out.println("�α��� ���� ����");
				os.write(protocol.getPacket());
				break;

			case Protocol.SC_RES_LOGIN:
				System.out.println("������ �α��� ��� ����.");
				String result = protocol.getResult();
				if (result.equals("1")) {
					System.out.println("�α��� ����");
				} else if (result.equals("2")) {
					System.out.println("��ȣ Ʋ��");
				} else if (result.equals("3")) {
					System.out.println("���̵� �������� ����");
				}
				protocol = new Protocol(Protocol.PT_EXIT);
				System.out.println("���� ��Ŷ ����");
				os.write(protocol.getPacket());
				break;

			case Protocol.SC_REQ_SIGNUP:
				System.out.println("������ ȸ������ ���� ��û");
				System.out.print("����(�����: 1, ������: 0)");
				String role = userIn.readLine();
				System.out.print("���̵�: ");
				String sign_id = userIn.readLine();
				System.out.print("��ȣ: ");
				String sign_password = userIn.readLine();
				System.out.print("�̸�: ");
				String name = userIn.readLine();
				System.out.print("����(��/��): ");
				String gender = userIn.readLine();
				System.out.print("����ó: ");
				String phone_number = userIn.readLine();
				System.out.print("�������(xxxx-xx-xx): ");
				String birth = userIn.readLine();
				System.out.print("���¹�ȣ: ");
				String account = userIn.readLine();

				protocol = new Protocol(Protocol.CS_REQ_SIGNUP);
				protocol.setSighUpData(role + "/" + sign_id + "/" + sign_password + "/" + name + "/" + gender + "/"
						+ phone_number + "/" + birth + "/" + account);
				System.out.println("ȸ������ ���� ����");
				os.write(protocol.getPacket());
				break;

			case Protocol.SC_RES_SIGNUP:
				System.out.println("������ ȸ������ ��� ����.");
				String signUp_result = protocol.getResult();
				if (signUp_result.equals("0")) {
					System.out.println("ȸ������ ����");
				} else if (signUp_result.equals("1")) {
					System.out.println("ȸ������ ����");
				}
				protocol = new Protocol(Protocol.PT_EXIT);
				System.out.println("���� ��Ŷ ����");
				os.write(protocol.getPacket());
				break;
			}
		}
		os.close();
		is.close();
		socket.close();
	}
}