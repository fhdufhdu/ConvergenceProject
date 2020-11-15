package com.db.model;

public class ReservationDTO extends DTO{
    private String id;
    private String member_id;
    private String time_table_id;
    private int screen_row;
    private int screen_col;
    private int price;          //자동 결정 되게 하기
    private String cancel;

    public ReservationDTO(String id, String member_id, String time_table_id, int screen_row, int screen_col, int price,
            String cancel) {
        this.id = id;
        this.member_id = member_id;
        this.time_table_id = time_table_id;
        this.screen_row = screen_row;
        this.screen_col = screen_col;
        this.price = price;
        this.cancel = cancel;
    }

    public ReservationDTO(String member_id, String time_table_id, int screen_row, int screen_col,
            String cancel) {
        this.member_id = member_id;
        this.time_table_id = time_table_id;
        this.screen_row = screen_row;
        this.screen_col = screen_col;
        this.cancel = cancel;
    }

    public ReservationDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return member_id;
    }

    public void setMemberId(String member_id) {
        this.member_id = member_id;
    }

    public String getTimeTableId() {
        return time_table_id;
    }

    public void setTimeTableId(String time_table_id) {
        this.time_table_id = time_table_id;
    }

    public int getScreenRow() {
        return screen_row;
    }

    public void setScreenRow(int screen_row) {
        this.screen_row = screen_row;
    }

    public int getScreenCol() {
        return screen_col;
    }

    public void setScreenCol(int screen_col) {
        this.screen_col = screen_col;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    
}
