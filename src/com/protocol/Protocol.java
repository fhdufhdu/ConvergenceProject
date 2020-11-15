package com.protocol;

public class Protocol {
	// 프로토콜 타입에 관한 변수
	public static final int PT_UNDEFINED = -1; // 프로토콜이 지정되어 있지 않은 경우
	public static final int PT_EXIT = 0; // 프로그램 종료
	public static final int CS_REQ_CONNECT = 1; // 서버 접근 요청
	public static final int SC_RES_CONNECT = 2; // 서버 접근 응답
	public static final int SC_REQ_LOGIN = 3; // 로그인 정보 요청
	public static final int CS_REQ_LOGIN = 4; // 로그인 요청
	public static final int SC_RES_LOGIN = 5; // 로그인 요청 응답
	public static final int SC_REQ_SIGNUP = 6; // 회원가입 정보 요청
	public static final int CS_REQ_SIGNUP = 7; // 회원가입 요청
	public static final int SC_RES_SIGNUP = 8; // 회원가입 응답

	public static final int LEN_LOGIN_ID = 20; // ID 길이
	public static final int LEN_LOGIN_PASSWORD = 50; // PWD 길이
	public static final int LEN_ACCOUNT = 25; // 계좌 길이
	public static final int LEN_ROLE = 1;
	public static final int LEN_NAME = 50; // 이름 길이
	public static final int LEN_PHONE_NUMBER = 20; // 연락처 길이
	public static final int LEN_BIRTH = 10; // 생년월일 길이
	public static final int LEN_GENDER = 5; // 성별 길이
	public static final int LEN_PROTOCOL_TYPE = 1; // 프로토콜 타입 길이
	public static final int LEN_MAX = 1000; // 최대 데이터 길이
	public static final int LEN_RESULT = 2; // 수행 결과 길이

	public static final int FAILED_LOGIN = 1;
	public static final int FAILED_LOGIN_5 = 2;
	public static final int SUCCESS_LOGIN = 3;

	protected int protocolType;
	private byte[] packet; // 프로토콜과 데이터의 저장공간이 되는 바이트 배열

	public Protocol() { // 생성자
		this(PT_UNDEFINED);
	}

	public Protocol(int protocolType) { // 생성자
		this.protocolType = protocolType;
		getPacket(protocolType);
	}

	// 프로토콜 타입에 따라 바이트 배열 packet의 length가 다름
	public byte[] getPacket(int protocolType) {
		if (packet == null) {
			switch (protocolType) {
			case SC_RES_CONNECT:
			case SC_REQ_LOGIN:
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
		packet[0] = (byte) protocolType; // packet 바이트 배열의 첫 번째 바이트에 프로토콜 타입 설정
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

	// Default 생성자로 생성한 후 Protocol 클래스의 packet 데이터를 바꾸기 위한 메서드
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

	// byte[] packet에 String ID를 byte[]로 만들어 프로토콜 타입 바로 뒤에 추가
	public void setId(String id) {
		System.arraycopy(id.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE, id.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + id.trim().getBytes().length] = '\0';
	}

	// 패스워드는 byte[]에서 로그인 아이디 바로 뒤에 있음
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

	// 로그인후 성공/실패의 결과 값을 프로토콜로부터 추출하여 문자열로 리턴
	public String getResult() {
		// String의 다음 생성자를 사용 : String(byte[] bytes, int offset, int length)
		return new String(packet, LEN_PROTOCOL_TYPE, LEN_RESULT).trim();
	}

	// String ok를 byte[]로 만들어서 packet의 프로토콜 타입 바로 뒤에 추가
	public void setResult(String result) {
		// arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
		System.arraycopy(result.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE, result.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + result.trim().getBytes().length] = '\0';
	}
}