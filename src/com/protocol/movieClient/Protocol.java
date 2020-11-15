package com.protocol.movieClient;

public class Protocol {
	// �������� Ÿ�Կ� ���� ����
	public static final int PT_UNDEFINED = -1; // ���������� �����Ǿ� ���� ���� ���
	public static final int PT_EXIT = 0; // ���α׷� ����
	public static final int CS_REQ_CONNECT = 1; // ���� ���� ��û
	public static final int SC_RES_CONNECT = 2; // ���� ���� ���� �� �α��� ��û
	public static final int CS_REQ_LOGIN = 3; // �α��� ��û
	public static final int SC_RES_LOGIN = 4; // �α��� ��û ����
	public static final int SC_REQ_SIGNUP = 5; // ȸ������ ���� ��û
	public static final int CS_REQ_SIGNUP = 6; // ȸ������ ��û
	public static final int SC_RES_SIGNUP = 7; // ȸ������ ����

	public static final int LEN_LOGIN_ID = 20; // ID ����
	public static final int LEN_LOGIN_PASSWORD = 50; // PWD ����
	public static final int LEN_ACCOUNT = 25; // ���� ����
	public static final int LEN_ROLE = 1;
	public static final int LEN_NAME = 50; // �̸� ����
	public static final int LEN_PHONE_NUMBER = 20; // ����ó ����
	public static final int LEN_BIRTH = 10; // ������� ����
	public static final int LEN_GENDER = 5; // ���� ����
	public static final int LEN_PROTOCOL_TYPE = 1; // �������� Ÿ�� ����
	public static final int LEN_MAX = 1000; // �ִ� ������ ����
	public static final int LEN_RESULT = 2; // ���� ��� ����

	public static final int FAILED_LOGIN = 1;
	public static final int FAILED_LOGIN_5 = 2;
	public static final int SUCCESS_LOGIN = 3;

	protected int protocolType;
	private byte[] packet; // �������ݰ� �������� ��������� �Ǵ� ����Ʈ �迭

	public Protocol() { // ������
		this(PT_UNDEFINED);
	}

	public Protocol(int protocolType) { // ������
		this.protocolType = protocolType;
		getPacket(protocolType);
	}

	// �������� Ÿ�Կ� ���� ����Ʈ �迭 packet�� length�� �ٸ�
	public byte[] getPacket(int protocolType) {
		if (packet == null) {
			switch (protocolType) {
			case SC_RES_CONNECT:
				packet = new byte[LEN_PROTOCOL_TYPE];
				break;
			case CS_REQ_LOGIN:
				packet = new byte[LEN_PROTOCOL_TYPE + LEN_LOGIN_ID + LEN_LOGIN_PASSWORD];
				break;
			case SC_RES_LOGIN:
				packet = new byte[LEN_PROTOCOL_TYPE + LEN_RESULT];
				break;
			case SC_REQ_SIGNUP:
				packet = new byte[LEN_PROTOCOL_TYPE];
				break;
			case CS_REQ_SIGNUP:
				packet = new byte[LEN_PROTOCOL_TYPE + LEN_ROLE + LEN_LOGIN_ID + LEN_LOGIN_PASSWORD + LEN_NAME
						+ LEN_GENDER + LEN_PHONE_NUMBER + LEN_BIRTH + LEN_ACCOUNT];
				break;
			case SC_RES_SIGNUP:
				packet = new byte[LEN_PROTOCOL_TYPE + LEN_RESULT];
				break;
			case PT_UNDEFINED:
				packet = new byte[LEN_MAX];
				break;
			case PT_EXIT:
				packet = new byte[LEN_PROTOCOL_TYPE];
				break;
			} // end switch
		} // end if
		packet[0] = (byte) protocolType; // packet ����Ʈ �迭�� ù ��° ����Ʈ�� �������� Ÿ�� ����
		return packet;
	}

	public void setProtocolType(int protocolType) {
		this.protocolType = protocolType;
	}

	public int getProtocolType() {
		return protocolType;
	}

	public byte[] getPacket() {
		return packet;
	}

	// Default �����ڷ� ������ �� Protocol Ŭ������ packet �����͸� �ٲٱ� ���� �޼���
	public void setPacket(int pt, byte[] buf) {
		packet = null;
		packet = getPacket(pt);
		protocolType = pt;
		System.arraycopy(buf, 0, packet, 0, packet.length);
	}

	public String getId() {
		// String(byte[] bytes, int offset, int length)
		return new String(packet, LEN_PROTOCOL_TYPE, LEN_LOGIN_ID).trim();
	}

	// byte[] packet�� String ID�� byte[]�� ����� �������� Ÿ�� �ٷ� �ڿ� �߰�
	public void setId(String id) {
		System.arraycopy(id.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE, id.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + id.trim().getBytes().length] = '\0';
	}

	// �н������ byte[]���� �α��� ���̵� �ٷ� �ڿ� ����
	public String getPassword() {
		return new String(packet, LEN_PROTOCOL_TYPE + LEN_LOGIN_ID, LEN_LOGIN_PASSWORD).trim();
	}

	public void setPassword(String password) {
		System.arraycopy(password.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_LOGIN_ID,
				password.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_LOGIN_ID + password.trim().getBytes().length] = '\0';
	}

	public String getSignUpData() {
		return new String(packet, LEN_PROTOCOL_TYPE, LEN_PROTOCOL_TYPE + LEN_LOGIN_ID + LEN_LOGIN_PASSWORD + LEN_ACCOUNT
				+ LEN_NAME + LEN_PHONE_NUMBER + LEN_BIRTH + LEN_GENDER).trim();
	}

	public void setSighUpData(String data) {
		System.arraycopy(data.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE, data.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + data.trim().getBytes().length] = '\0';
	}

	// �α����� ����/������ ��� ���� �������ݷκ��� �����Ͽ� ���ڿ��� ����
	public String getResult() {
		// String�� ���� �����ڸ� ��� : String(byte[] bytes, int offset, int length)
		return new String(packet, LEN_PROTOCOL_TYPE, LEN_RESULT).trim();
	}

	// String ok�� byte[]�� ���� packet�� �������� Ÿ�� �ٷ� �ڿ� �߰�
	public void setResult(String result) {
		// arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
		System.arraycopy(result.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE, result.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + result.trim().getBytes().length] = '\0';
	}
}