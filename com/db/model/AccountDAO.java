package com.db.model;

import java.sql.*;

public class AccountDAO extends DAO
{
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public AccountDAO()
    {
        super();
        conn = getConn();
        ps = getPs();
        rs = getRs();
    }

    /*//계좌 추가
    public void addAccount(AccountDTO ac) throws Exception
    {
        String insert_sql = "insert into accounts(account, money) values(?, ?)";

        if(checkAccount(ac) != 0) 
        {
            ps.close();
            throw new DAOException("account duplicate found");
        }

        ps = conn.prepareStatement(insert_sql);

        ps.setString(1, ac.getAccount());
        ps.setInt(2, ac.getMoney());
        
        int r = ps.executeUpdate();
        System.out.println("변경된 row : " + r);

        ps.close();
    }

    //계좌가 중복되는지 확인
    private int checkAccount(AccountDTO ac) throws Exception
    {
        String check_sql = "select * from accounts where account = ?";
        ps = conn.prepareStatement(check_sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ps.setString(1, ac.getAccount());

        rs = ps.executeQuery();
        rs.last();
        int result_row = rs.getRow();

        rs.close();
        ps.close();

        return result_row;
    }

    //계좌 잔액 수정 - 문제 발생 시 예외처리
    public void plusMoney(String account, int money) throws Exception
    {
        String insert_sql = "update accounts set money = ? where account = ?";

        int modified_money = getMoney(account) + money;

        ps = conn.prepareStatement(insert_sql);

        ps.setInt(1, modified_money);
        ps.setString(2, account);
        
        int r = ps.executeUpdate();
        System.out.println("변경된 row : " + r);

        ps.close();
    }

    //계좌 잔액 수정 - 문제 발생 시 예외처리0
    public void minusMoney(String account, int money) throws Exception
    {
        String insert_sql = "update accounts set money = ? where account = ?";

        if(getMoney(account) < money)
        {
            throw new DAOException("잔액 부족");
        }
        int modified_money = getMoney(account) - money;

        ps = conn.prepareStatement(insert_sql);

        ps.setInt(1, modified_money);
        ps.setString(2, account);
        
        int r = ps.executeUpdate();
        System.out.println("변경된 row : " + r);

        ps.close();
        /*String insert_sql = "call payment(?, ?)";

        //if(getMoney(account) < money)
        //{
        //    throw new DAOException("잔액 부족");
        //}
        //int modified_money = getMoney(account) - money;
        
        ps = conn.prepareStatement(insert_sql);

        ps.setString(1, account);
        ps.setInt(2, money);
        
        int r = ps.executeUpdate();
        System.out.println("변경된 row : " + r);

        ps.close();
    }

    public int getMoney(String account) throws Exception
    {
        String check_sql = "select * from accounts where account = ?";
        ps = conn.prepareStatement(check_sql);

        ps.setString(1, account);

        rs = ps.executeQuery();
        rs.next();
        int money = rs.getInt("money");
        rs.close();
        ps.close();
        return money;
    }*/

}
