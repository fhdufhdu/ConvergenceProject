package com.db.model;

import java.sql.*;

public class ChargeDAO extends DAO
{
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public ChargeDAO()
    {
        super();
        conn = getConn();
        ps = getPs();
        rs = getRs();
    }

    //멤버 정보 수정
    public void changeCharge(ChargeDTO charge) throws Exception
    {
        String insert_sql = "update charges set price = ? where type = ?";

        ps = conn.prepareStatement(insert_sql);

        ps.setInt(1, charge.getPrice());
        ps.setString(2, charge.getType());
        
        int r = ps.executeUpdate();
        System.out.println("변경된 row : " + r);

        ps.close();
    }
}
