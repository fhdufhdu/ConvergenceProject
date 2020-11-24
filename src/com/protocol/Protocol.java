package com.protocol;

public class Protocol
{
	// 프로토콜 타입에 관한 변수
	public static final String PT_EXIT = "0"; // 프로그램 종료
	public static final String PT_REQ_LOGIN_INFO = "1"; // 로그인 정보 요청
	public static final String PT_REQ_LOGIN = "2"; // 로그인 요청
	public static final String PT_RES_LOGIN = "3"; // 로그인 요청 응답
	public static final String PT_REQ_VIEW = "4"; // 조회 요청
	public static final String PT_RES_VIEW = "5"; // 조회 요청 으답
	public static final String PT_REQ_RENEWAL = "6"; // 갱신 요청
	public static final String PT_RES_RENEWAL = "7"; // 갱신 요청 응답
	
	// 프로토콜 타입의 코드에 관한 변수
	public static final String CS_REQ_SIGNUP = "6-0"; // 회원가입 요청
	public static final String SC_RES_SIGNUP = "7-0"; // 회원가입 요청 응답
	public static final String CS_REQ_THEATER_VIEW = "4-0"; // 영화관 리스트 요청
	public static final String SC_RES_THEATER_VIEW = "5-0"; // 영화관 리스트 요청 응답
	public static final String CS_REQ_THEATER_ADD = "6-4"; // 영화관 등록 요청
	public static final String SC_RES_THEATER_ADD = "7-8"; // 영화관 등록 요청 응답
	public static final String CS_REQ_THEATER_DELETE = "6-5"; // 영화관 삭제 요청
	public static final String SC_RES_THEATER_DELETE = "7-A"; // 영화관 삭제 요청 응답
	public static final String CS_REQ_THEATER_CHANGE = "6-6"; // 영화관 수정 요청
	public static final String SC_RES_THEATER_CHANGE = "7-B"; // 영화관 수정 요청 응답
	public static final String CS_REQ_SCREEN_VIEW = "4-11"; // 상영관 리스트 요청
	public static final String SC_RES_SCREEN_VIEW = "5-22"; // 상영관 리스트 요청 응답
	public static final String CS_REQ_SCREEN_ADD = "6-A"; // 상영관 등록 요청
	public static final String SC_RES_SCREEN_ADD = "7-14"; // 상영관 등록 요청 응답
	public static final String CS_REQ_SCREEN_CHANGE = "6-B"; // 상영관 수정 요청
	public static final String SC_RES_SCREEN_CHANGE = "7-16"; // 상영관 수정 요청 응답
	public static final String CS_REQ_SCREEN_DELETE = "6-C"; // 상영관 삭제 요청
	public static final String SC_RES_SCREEN_DELETE = "7-18"; // 상영관 삭제 요청
	public static final String CS_REQ_MOVIE_VIEW = "4-D"; // 영화 정보 요청
	public static final String SC_RES_MOVIE_VIEW = "5-1A"; // 영화 정보 요청 읃답
	public static final String CS_REQ_MOVIE_ADD = "6-D"; // 영화 등록 요청
	public static final String SC_RES_MOVIE_ADD = "7-1A"; // 영화 등록 요청 응답
	public static final String CS_REQ_MOVIE_CHANGE = "6-E"; // 영화 수정 요청
	public static final String SC_RES_MOVIE_CHANGE = "7-1C"; // 영화 수정 요청 응답
	public static final String CS_REQ_MOVIE_DELETE = "6-F"; // 영화 삭제 요청
	public static final String SC_RES_MOVIE_DELETE = "7-1E"; // 영화 삭제 요청 응답
}