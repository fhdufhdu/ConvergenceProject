package com.protocol;

public class Protocol {
	// 프로토콜 타입에 관한 변수
	public static final String PT_EXIT = "0"; // 프로그램 종료
	public static final String CS_REQ_CONNECT = "1"; // 서버 접근 요청
	public static final String SC_RES_CONNECT = "2"; // 서버 접근 응답
	public static final String SC_REQ_LOGIN = "3"; // 로그인 정보 요청
	public static final String CS_REQ_LOGIN = "4"; // 로그인 요청
	public static final String SC_RES_LOGIN = "5"; // 로그인 요청 응답
	public static final String SC_REQ_SIGNUP = "6"; // 회원가입 정보 요청
	public static final String CS_REQ_SIGNUP = "7"; // 회원가입 요청
	public static final String SC_RES_SIGNUP = "8"; // 회원가입 응답
}