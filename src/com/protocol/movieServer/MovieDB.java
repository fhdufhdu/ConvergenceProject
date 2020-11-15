package com.protocol.movieServer;

import java.sql.*;

public class MovieDB {
	private Connection conn;
	private Statement state;
	private ResultSet rs;

	// 생성자가 DB연결
	public MovieDB(String url, String id, String pw) throws SQLException, ClassNotFoundException {
		conn = null;
		state = null;
		rs = null;
		try {
			// 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 연결 시도
			conn = DriverManager.getConnection(url, id, pw);
			state = conn.createStatement();
		} catch (ClassNotFoundException e) {
			System.out.println("DB 드라이버 로딩 실패" + e.toString());
		} catch (SQLException e) {
			System.out.println("DB 접속 에러: " + e.toString());
		} catch (Exception e) {
			System.out.println("Unknown Error");
			e.printStackTrace();
		}
	}

	public void blockConnection() throws SQLException { // DB종료
		try {
			if (conn != null && !conn.isClosed())
				conn.close();

			if (state != null && !state.isClosed())
				state.close();

			if (rs != null && !rs.isClosed())
				rs.close();

		} catch (SQLException e) {
			System.out.println("에러: " + e);
		}
	}

	public boolean InsertDB(String query) throws SQLException {
		int result = 0;
		String sql = query;
		try {
			result = state.executeUpdate(sql);
			if(result > 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			System.out.println("에러: " + e);
			return false;
		}
	}

	public ResultSet getSelectResult(String query) throws SQLException { // sql문 결과
		String sql = query;
		try {
			rs = state.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		}

		return rs;
	}

	public int UpdateDB(String query) throws SQLException {
		int uCount = 0;
		String sql = query;
		try {
			uCount = state.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		}

		return uCount;
	}

	public Connection getCon() {
		return conn;
	}

	public ResultSet getRs() {
		return rs;
	}

}