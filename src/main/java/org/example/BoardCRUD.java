package org.example;

import java.sql.*;
import java.util.Scanner;

public class BoardCRUD implements IBoardCRUD {

    private Connection conn;
    private Scanner s;

    public BoardCRUD(Scanner s) {
        this.s = s;
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:board.db");
            // 테이블이 없다면 생성
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS board (\n"
                    + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "    subject TEXT NOT NULL,\n"
                    + "    writer TEXT NOT NULL,\n"
                    + "    created_date TEXT DEFAULT (DATETIME('now', 'localtime')),\n"
                    + "    updated_date TEXT DEFAULT (DATETIME('now', 'localtime')),\n"
                    + "    read INTEGER DEFAULT 0\n"
                    + ");";
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            System.err.println("DB 연결 또는 테이블 생성 실패: " + e.getMessage());
        }
    }

    @Override
    public void addItem() {
        s.nextLine();
        System.out.print("=> subject : ");
        String subject = s.nextLine().trim();
        System.out.print("=> writer : ");
        String writer = s.nextLine().trim();

        if(subject.isEmpty() || writer.isEmpty()) {
            System.out.println("제목과 작성자는 필수 입력 항목입니다.");
            return;
        }

        String sql = "INSERT INTO board (subject, writer, created_date, updated_date, read) "
                + "VALUES (?, ?, DATETIME('now', 'localtime'), DATETIME('now', 'localtime'), 0)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setString(2, writer);
            pstmt.executeUpdate();
            System.out.println("새 게시글이 추가되었습니다.");
        } catch (SQLException e) {
            System.err.println("데이터 추가 오류: " + e.getMessage());
        }
    }
}