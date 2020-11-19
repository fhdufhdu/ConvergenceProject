package com.db.model;

import java.sql.*;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomDTO {
    ReservationDTO rDto;
    MemberDTO memDto;
    TimeTableDTO ttDto;
    MovieDTO movDto;
    ScreenDTO sDto;
    TheaterDTO tDto;

    public CustomDTO(ReservationDTO rDto) throws DAOException, SQLException {
        this.rDto = rDto;

        MemberDAO memDao = new MemberDAO();
        memDto = memDao.getMemberInfo(rDto.getMemberId());

        TimeTableDAO ttDao = new TimeTableDAO();
        ttDto = ttDao.getTimeTable(rDto.getTimeTableId());

        MovieDAO movDao = new MovieDAO();
        movDto = movDao.getMovie(ttDto.getMovieId());

        ScreenDAO sDao = new ScreenDAO();
        sDto = sDao.getScreenElem(ttDto.getScreenId());

        TheaterDAO tDao = new TheaterDAO();
        tDto = tDao.getTheaterElem(sDto.getTheaterId());
    }

    public StringProperty getMember() {
        return new SimpleStringProperty(memDto.getName());
    }

    public StringProperty getMovie() {
        return new SimpleStringProperty(movDto.getTitle());
    }

    public StringProperty getTheater() {
        return new SimpleStringProperty(tDto.getName());
    }

    public StringProperty getScreen() {
        return new SimpleStringProperty(sDto.getName());
    }

    public StringProperty getStartTime() {
        return new SimpleStringProperty(ttDto.getStartTime().toString());
    }

    public StringProperty getEndTime() {
        return new SimpleStringProperty(ttDto.getEndTime().toString());
    }

    public StringProperty getSeat() {
        return new SimpleStringProperty(
                Character.toString((char) (rDto.getScreenRow() + 64)) + Integer.toString(rDto.getScreenCol() + 1));
    }

    public StringProperty getPrice() {
        return new SimpleStringProperty(Integer.toString(rDto.getPrice()));
    }

    public ReservationDTO getRsv() {
        return rDto;
    }
}
