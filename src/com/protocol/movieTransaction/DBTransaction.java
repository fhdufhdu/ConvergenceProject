package com.protocol.movieTransaction;

import com.protocol.movieServer.MovieDB;
import com.protocol.*;
//트렌젝션은 건들이면 안됌 필요하면 모두가 같이 변경
public abstract class DBTransaction {
	private String id;
	private boolean admit;
	private MovieDB movieDB;
	private Protocol protocol;

	public DBTransaction(String id, boolean admit, MovieDB db, Protocol protocol) {
		this.id = id;
		this.admit = admit;
		movieDB = db;
		this.protocol = protocol;
	}

	public abstract Protocol execute();

	public void setID(String id) {
		this.id = id;
	}

	public void setAdmit(boolean admit) {
		this.admit = admit;
	}

	public String getID() {
		return id;
	}

	public boolean getAdmit() {
		return admit;
	}

	public MovieDB getDB() {
		return movieDB;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol input) {
		protocol = input;
	}
}