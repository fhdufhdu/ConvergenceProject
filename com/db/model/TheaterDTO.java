package com.db.model;

public class TheaterDTO extends DTO{
    private String id;
    private String name;
    private String address;
    private int total_screen;
    private int total_seats;

    public TheaterDTO(String name, String address, int total_screen, int total_seats) {
        this.name = name;
        this.address = address;
        this.total_screen = total_screen;
        this.total_seats = total_seats;
    }

    public TheaterDTO()
    {
        
    }

    public TheaterDTO(String id, String name, String address, int total_screen, int total_seats) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.total_screen = total_screen;
        this.total_seats = total_seats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalScreen() {
        return total_screen;
    }

    public void setTotalScreen(int total_screen) {
        this.total_screen = total_screen;
    }

    public int getTotalSeats() {
        return total_seats;
    }

    public void setTotalSeats(int total_seats) {
        this.total_seats = total_seats;
    }

    
}
