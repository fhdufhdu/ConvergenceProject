package com.protocol.movieServer;

import java.sql.*;

public class MovieDB {
	private Connection conn;
	private Statement state;
	private ResultSet rs;

	// �����ڰ� DB����
	public MovieDB(String url, String id, String pw) throws SQLException, ClassNotFoundException {
		conn = null;
		state = null;
		rs = null;
		try {
			// ����̹� �ε�
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ���� �õ�
			conn = DriverManager.getConnection(url, id, pw);
			state = conn.createStatement();
		} catch (ClassNotFoundException e) {
			System.out.println("DB ����̹� �ε� ����" + e.toString());
		} catch (SQLException e) {
			System.out.println("DB ���� ����: " + e.toString());
		} catch (Exception e) {
			System.out.println("Unknown Error");
			e.printStackTrace();
		}
	}

	public void blockConnection() throws SQLException { // DB����
		try {
			if (conn != null && !conn.isClosed())
				conn.close();

			if (state != null && !state.isClosed())
				state.close();

			if (rs != null && !rs.isClosed())
				rs.close();

		} catch (SQLException e) {
			System.out.println("����: " + e);
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
			System.out.println("����: " + e);
			return false;
		}
	}

	public ResultSet getSelectResult(String query) throws SQLException { // sql�� ���
		String sql = query;
		try {
			rs = state.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("����: " + e);
		}

		return rs;
	}

	public int UpdateDB(String query) throws SQLException {
		int uCount = 0;
		String sql = query;
		try {
			uCount = state.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("����: " + e);
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