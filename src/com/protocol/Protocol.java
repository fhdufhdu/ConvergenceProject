package com.protocol;

public class Protocol {
	// 프로토콜 타입에 관한 변수
	public static final String PT_EXIT = "0"; // 프로그램 종료
	public static final String SC_REQ_LOGIN = "1"; // 서버와 연결 응답 및 로그인 정보 요청
	public static final String CS_REQ_LOGIN = "2"; // 로그인 요청
	public static final String SC_RES_LOGIN = "3"; // 로그인 요청 응답
	public static final String CS_REQ_FILE = "4"; // 파일 전송 요청
	public static final String SC_RES_FILE = "5"; // 피일 전송 요청에 대한 응답
	public static final String CS_REQ_VIEW = "6"; // 조회 요청
	public static final String SC_RES_VIEW = "7"; // 조회 요청에 대한 응답
	public static final String CS_REQ_RENEWAL = "8"; // 갱신 요청
	public static final String SC_RES_RENEWAL = "9"; // 갱신 요청에 대한 응답

	// 프로토콜 타입의 코드에 관한 변수
	public static final String CS_REQ_SIGNUP = "8-0"; // 회원가입 요청
	public static final String SC_RES_SIGNUP = "9-0"; // 회원가입 응답
}