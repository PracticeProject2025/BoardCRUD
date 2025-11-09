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
}