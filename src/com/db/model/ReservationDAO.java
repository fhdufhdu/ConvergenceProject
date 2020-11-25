package com.db.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class ReservationDAO extends DAO
{
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public ReservationDAO()
    {
        super();
        conn = getConn();
        ps = getPs();
        rs = getRs();
    }
    
    // 예약 추가
    public int addPreRsv(String member_id, String ttable_id, ArrayList<Integer> row_arr, ArrayList<Integer> col_arr, String account, String bank) throws DAOException, SQLException
    {
        try
        {
            String insert_sql = "call PRE_RSV(?, ?, ?, ?, ?, ?, ?)";
            
            CallableStatement cs = conn.prepareCall(insert_sql);
            
            cs.setString(1, member_id);
            cs.setString(2, ttable_id);
            cs.setArray(3, new ARRAY(ArrayDescriptor.createDescriptor("N_ARRAY", conn), conn, row_arr.toArray()));
            cs.setArray(4, new ARRAY(ArrayDescriptor.createDescriptor("N_ARRAY", conn), conn, col_arr.toArray()));
            cs.setString(5, account);
            cs.setString(6, bank);
            cs.registerOutParameter(7, Types.INTEGER);
            cs.executeUpdate();
            int result = cs.getInt(7);
            
            cs.close();
            return result;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DAOException("DUPLICATE_RSV");
        }
        
    }
    
    public void addConfimRsv(String member_id, String ttable_id, ArrayList<Integer> row_arr, ArrayList<Integer> col_arr) throws DAOException, SQLException
    {
        try
        {
            String insert_sql = "call CONFIRM_RSV(?, ?, ?, ?)";
            
            CallableStatement cs = conn.prepareCall(insert_sql);
            
            cs.setString(1, member_id);
            cs.setString(2, ttable_id);
            cs.setArray(3, new ARRAY(ArrayDescriptor.createDescriptor("N_ARRAY", conn), conn, row_arr.toArray()));
            cs.setArray(4, new ARRAY(ArrayDescriptor.createDescriptor("N_ARRAY", conn), conn, col_arr.toArray()));
            
            cs.executeUpdate();
            
            cs.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DAOException("NOT_SELECTED");
        }
        
    }
    
    public void payment(String mem_accout, String bank, String passwd, int money) throws DAOException, SQLException
    {
        String insert_sql = "call payment(?, ?, ?, ?)";
        
        ps = conn.prepareStatement(insert_sql);
        
        ps.setString(1, mem_accout);
        ps.setInt(2, money);
        ps.setString(3, bank);
        ps.setString(4, passwd);
        
        int r = ps.executeUpdate();
        System.out.println("변경된 row : " + r);
        
        ps.close();
    }
    
    public void refund(String rid) throws DAOException, SQLException
    {
        String insert_sql = "call refund(?)";
        
        ps = conn.prepareStatement(insert_sql);
        
        ps.setString(1, rid);
        
        int r = ps.executeUpdate();
        System.out.println("변경된 row : " + r);
        
        ps.close();
    }
    
    private int checkReservation(ReservationDTO rsv) throws DAOException, SQLException
    {
        String check_sql = "select * from reservations where ttable_id = ? and s_row = ? and s_col = ? and cancel = 0 and not(id = ?)";
        ps = conn.prepareStatement(check_sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        ps.setString(1, rsv.getTimeTableId());
        ps.setInt(2, rsv.getScreenRow());
        ps.setInt(3, rsv.getScreenCol());
        ps.setString(4, rsv.getId());
        
        rs = ps.executeQuery();
        rs.last();
        int result_row = rs.getRow();
        
        rs.close();
        ps.close();
        
        return result_row;
        
    }
    
    // 상영시간표 id에 맞는 예약 DTO 리스트 반환
    public ArrayList<ReservationDTO> getRsvListFromTT(String ttid) throws DAOException, SQLException
    {
        ArrayList<ReservationDTO> temp_list = new ArrayList<ReservationDTO>();
        String insert_sql = "select * from reservations where ttable_id = ? and type in ('0', '1')";
        ps = conn.prepareStatement(insert_sql);
        
        ps.setString(1, ttid);
        
        rs = ps.executeQuery();
        while (rs.next())
        {
            String id = rs.getString("id");
            String member_id = rs.getString("member_id");
            String time_table_id = rs.getString("ttable_id");
            int screen_row = rs.getInt("s_row");
            int screen_col = rs.getInt("s_col");
            int price = rs.getInt("price");
            String type = rs.getString("type");
            Timestamp rsv_time = rs.getTimestamp("rsv_time");
            String account = rs.getString("account");
            String bank = rs.getString("bank");
            temp_list.add(new ReservationDTO(id, member_id, time_table_id, screen_row, screen_col, price, type, rsv_time, account, bank));
        }
        
        rs.close();
        ps.close();
        
        return temp_list;
    }
    
    public ArrayList<ReservationDTO> getRsvListFromMem(String mem_id) throws DAOException, SQLException
    {
        ArrayList<ReservationDTO> temp_list = new ArrayList<ReservationDTO>();
        String insert_sql = "select * from reservations where member_id = ? and cancel = 0";
        ps = conn.prepareStatement(insert_sql);
        
        ps.setString(1, mem_id);
        
        rs = ps.executeQuery();
        while (rs.next())
        {
            String id = rs.getString("id");
            String member_id = rs.getString("member_id");
            String time_table_id = rs.getString("ttable_id");
            int screen_row = rs.getInt("s_row");
            int screen_col = rs.getInt("s_col");
            int price = rs.getInt("price");
            String type = rs.getString("type");
            Timestamp rsv_time = rs.getTimestamp("rsv_time");
            String account = rs.getString("account");
            String bank = rs.getString("bank");
            temp_list.add(new ReservationDTO(id, member_id, time_table_id, screen_row, screen_col, price, type, rsv_time, account, bank));
        }
        
        rs.close();
        ps.close();
        
        return temp_list;
    }
    
    public ArrayList<ReservationDTO> getRsvList(String mem_id, String movie_id, String theater_id, String start_time, String end_time) throws DAOException, SQLException
    {
        ArrayList<ReservationDTO> temp_list = new ArrayList<ReservationDTO>();
        String insert_sql = "select * from reservations where member_id like ? and ttable_id in (select id from timetables where movie_id like ? and start_time between ? and ? and screen_id in (select id from screens where theater_id like ?))";
        ps = conn.prepareStatement(insert_sql);
        
        ps.setString(1, mem_id);
        ps.setString(2, movie_id);
        ps.setTimestamp(3, Timestamp.valueOf(start_time));
        ps.setTimestamp(4, Timestamp.valueOf(end_time));
        ps.setString(5, theater_id);
        
        System.out.println(mem_id + " / " + movie_id + " / " + start_time + " / " + end_time + " / " + theater_id);
        
        rs = ps.executeQuery();
        while (rs.next())
        {
            String id = rs.getString("id");
            String member_id = rs.getString("member_id");
            String time_table_id = rs.getString("ttable_id");
            int screen_row = rs.getInt("s_row");
            int screen_col = rs.getInt("s_col");
            int price = rs.getInt("price");
            String type = rs.getString("type");
            Timestamp rsv_time = rs.getTimestamp("rsv_time");
            String account = rs.getString("account");
            String bank = rs.getString("bank");
            temp_list.add(new ReservationDTO(id, member_id, time_table_id, screen_row, screen_col, price, type, rsv_time, account, bank));
        }
        
        rs.close();
        ps.close();
        
        if (temp_list.size() == 0)
        {
            throw new DAOException("EMPTY_LIST");
        }
        
        return temp_list;
    }
    
    public ArrayList<ReservationDTO> getRsvList(String movie_id, String screen_id, String start_time, String end_time) throws DAOException, SQLException
    {
        ArrayList<ReservationDTO> temp_list = new ArrayList<ReservationDTO>();
        String insert_sql = "select * from reservations where member_id like '%' and ttable_id in (select id from timetables where movie_id like ? and start_time between ? and ? and screen_id like ?)";
        ps = conn.prepareStatement(insert_sql);
        
        ps.setString(1, movie_id);
        ps.setTimestamp(2, Timestamp.valueOf(start_time));
        ps.setTimestamp(3, Timestamp.valueOf(end_time));
        ps.setString(4, screen_id);
        
        rs = ps.executeQuery();
        while (rs.next())
        {
            String id = rs.getString("id");
            String member_id = rs.getString("member_id");
            String time_table_id = rs.getString("ttable_id");
            int screen_row = rs.getInt("s_row");
            int screen_col = rs.getInt("s_col");
            int price = rs.getInt("price");
            String type = rs.getString("type");
            Timestamp rsv_time = rs.getTimestamp("rsv_time");
            String account = rs.getString("account");
            String bank = rs.getString("bank");
            temp_list.add(new ReservationDTO(id, member_id, time_table_id, screen_row, screen_col, price, type, rsv_time, account, bank));
        }
        
        rs.close();
        ps.close();
        
        return temp_list;
    }
    
    // 예약 취소
    public int cancelRsv(String id) throws DAOException, SQLException
    {
        String insert_sql = "call CANCEL_RSV(?, ?)";
        
        CallableStatement cs = conn.prepareCall(insert_sql);
        
        cs.setString(1, id);
        cs.registerOutParameter(2, Types.INTEGER);
        cs.executeUpdate();
        int result = cs.getInt(2);
        
        cs.close();
        return result;
    }
    
}
