package com.db.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

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
    public void addReservation(ReservationDTO rsv, String passwd) throws DAOException, SQLException
    {
        String insert_sql = "call ADD_RSV(?, ?, ?, ?, ?)";
        
        if (checkReservation(rsv) > 0)
        {
            ps.close();
            throw new DAOException("screen info duplicate found");
        }
        
        ps = conn.prepareStatement(insert_sql);
        
        ps.setString(1, rsv.getMemberId());
        ps.setInt(2, Integer.valueOf(rsv.getTimeTableId()));
        ps.setInt(3, rsv.getScreenRow());
        ps.setInt(4, rsv.getScreenCol());
        ps.setString(5, passwd);
        
        int r = ps.executeUpdate();
        System.out.println("변경된 row : " + r);
        
        rs.close();
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
        String insert_sql = "select * from reservations where ttable_id = ? and cancel = 0";
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
            String cancel = rs.getString("cancel");
            temp_list.add(new ReservationDTO(id, member_id, time_table_id, screen_row, screen_col, price, cancel));
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
            String cancel = rs.getString("cancel");
            temp_list.add(new ReservationDTO(id, member_id, time_table_id, screen_row, screen_col, price, cancel));
        }
        
        rs.close();
        ps.close();
        
        return temp_list;
    }
    
    public ArrayList<ReservationDTO> getRsvList(String mem_id, String movie_id, String theater_id, String start_time, String end_time) throws DAOException, SQLException
    {
        ArrayList<ReservationDTO> temp_list = new ArrayList<ReservationDTO>();
        String insert_sql = "select * from reservations where member_id like ? and ttable_id like (select id from timetables where movie_id like ? and start_time between ? and ? and screen_id like (select id from screens where theater_id like ?))";
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
            temp_list.add(new ReservationDTO(id, member_id, time_table_id, screen_row, screen_col, price, type));
        }
        
        rs.close();
        ps.close();
        
        return temp_list;
    }
    
    // 예약 취소
    public void cancelRsv(String id) throws DAOException, SQLException
    {
        String insert_sql = "call CANCEL_RSV(?)";
        
        ps = conn.prepareStatement(insert_sql);
        
        ps.setInt(1, Integer.valueOf(id));
        
        int r = ps.executeUpdate();
        System.out.println("변경된 row : " + r);
        
        ps.close();
    }
    
}
