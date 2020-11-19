package com.db.model;

import java.sql.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MovieDTO extends DTO
{
    private String id;
    private String title;
    private Date release_date; // date
    private String is_current;
    private String plot;
    private String poster_path;
    private String still_cut_path;
    private String trailer_path;
    private String director;
    private String actor;
    private int min;
    
    public MovieDTO(String id, String title, String release_date, String is_current, String plot, String poster_path, String still_cut_path, String trailer_path, String director, String actor, int min)
    {
        // release_date의 포맷은 "YYYY-MM-DD"
        this.id = id;
        this.title = title;
        this.release_date = Date.valueOf(release_date);
        this.is_current = is_current;
        this.plot = plot;
        this.poster_path = poster_path;
        this.still_cut_path = still_cut_path;
        this.trailer_path = trailer_path;
        this.director = director;
        this.actor = actor;
        this.min = min;
    }
    
    public MovieDTO(String title, String release_date, String is_current, String plot, String poster_path, String still_cut_path, String trailer_path, String director, String actor, int min)
    {
        // release_date의 포맷은 "YYYY-MM-DD"
        this.title = title;
        this.release_date = Date.valueOf(release_date);
        this.is_current = is_current;
        this.plot = plot;
        this.poster_path = poster_path;
        this.still_cut_path = still_cut_path;
        this.trailer_path = trailer_path;
        this.director = director;
        this.actor = actor;
        this.min = min;
    }
    
    public MovieDTO()
    {
        
    }
    
    public StringProperty getScreeningProperty()
    {
        if (is_current.equals("0"))
        {
            return new SimpleStringProperty("상영종료");
        } else if (is_current.equals("1"))
        {
            return new SimpleStringProperty("상영중");
        } else
        {
            return new SimpleStringProperty("상영예정");
        }
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public Date getReleaseDate()
    {
        return release_date;
    }
    
    public void setReleaseDate(String release_date)
    {
        this.release_date = Date.valueOf(release_date);
    }
    
    public String getIsCurrent()
    {
        return is_current;
    }
    
    public void setIsCurrent(String is_current)
    {
        this.is_current = is_current;
    }
    
    public String getPlot()
    {
        return plot;
    }
    
    public void setPlot(String plot)
    {
        this.plot = plot;
    }
    
    public String getPosterPath()
    {
        return poster_path;
    }
    
    public void setPosterPath(String poster_path)
    {
        this.poster_path = poster_path;
    }
    
    public String getStillCutPath()
    {
        return still_cut_path;
    }
    
    public void setStillCutPath(String still_cut_path)
    {
        this.still_cut_path = still_cut_path;
    }
    
    public String getTrailerPath()
    {
        return trailer_path;
    }
    
    public void setTrailerPath(String trailer_path)
    {
        this.trailer_path = trailer_path;
    }
    
    public String getDirector()
    {
        return director;
    }
    
    public void setDirector(String director)
    {
        this.director = director;
    }
    
    public String getActor()
    {
        return actor;
    }
    
    public void setActor(String actor)
    {
        this.actor = actor;
    }
    
    public int getMin()
    {
        return min;
    }
    
    public void setMin(int min)
    {
        this.min = min;
    }
}
