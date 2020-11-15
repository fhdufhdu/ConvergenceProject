package com.db.model;

public class ReviewDTO {
    private String id;
    private String member_id;
    private String movie_id;
    private int star;
    private String text;

    public ReviewDTO(String id, String member_id, String movie_id, int star, String text) {
        this.id = id;
        this.member_id = member_id;
        this.movie_id = movie_id;
        this.star = star;
        this.text = text;
    }

    public ReviewDTO() {

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

    public String getMovieId() {
        return movie_id;
    }

    public void setMovieId(String movie_id) {
        this.movie_id = movie_id;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
