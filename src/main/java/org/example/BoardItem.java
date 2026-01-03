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

    public int getId() { return id; }
    public String getSubject() { return subject; }
    public String getWriter() { return writer; }
    public int getRead() { return read; }


    public static String getDisplayHeader() {
        return String.format("%-4s %-25s %-10s %-12s %-5s", "ID", "Subject", "Writer", "Date", "Read");
    }

    public String toDisplayString() {
        String displayDate = (this.created_date != null && this.created_date.length() >= 10)
                ? this.created_date.substring(0, 10)
                : (this.created_date == null ? "" : this.created_date);

        String displaySubject = getDisplaySubject();

        return String.format("%-4d %-25s %-10s %-12s %-5d",
                this.id,
                displaySubject,
                this.writer,
                displayDate,
                this.read
        );
    }

    public static String getRankingHeader() {
        return String.format("%-4s %-25s %-10s %-5s", "ID", "Subject", "Writer", "Read");
    }

    public String toRankingString() {
        String displaySubject = getDisplaySubject();

        return String.format("%-4d %-25s %-10s %-5d",
                this.id,
                displaySubject,
                this.writer,
                this.read
        );
    }

    private String getDisplaySubject() {
        return (this.subject != null && this.subject.length() > 20)
                ? this.subject.substring(0, 20) + "..."
                : (this.subject == null ? "" : this.subject);
    }

    public String toFileString() {
        return String.join("|",
                String.valueOf(this.id),
                this.subject,
                this.writer,
                this.created_date,
                this.updated_date,
                String.valueOf(this.read)
        );
    }
}