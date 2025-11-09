package org.example;

public class BoardItem {
    private int id;
    private String subject;
    private String writer;
    private String created_date;
    private String updated_date;
    private int read;

    public BoardItem(int id, String subject, String writer, String created_date, String updated_date, int read) {
        this.id = id;
        this.subject = subject;
        this.writer = writer;
        this.created_date = created_date;
        this.updated_date = updated_date;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}