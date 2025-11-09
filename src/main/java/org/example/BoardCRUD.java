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

    @Override
    public void listAll() {
        System.out.println("---------------------------------");
        System.out.printf("%-3s %-25s %-10s %-12s %s\n", "No", "Subject", "Writer", "Date", "Read");
        System.out.println("---------------------------------");

        String sql = "SELECT id, subject, writer, created_date, read FROM board ORDER BY id DESC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("%-3d %-25s %-10s %-12s %d\n",
                        rs.getInt("id"),
                        rs.getString("subject"), // "subject"
                        rs.getString("writer"), // "writer"
                        rs.getString("created_date").substring(0, 10),
                        rs.getInt("read")); // "read"
            }
        } catch (SQLException e) {
            System.err.println("데이터 조회 오류: " + e.getMessage());
        }
        System.out.println("---------------------------------");
    }

    @Override
    public void updateItem() {
        System.out.print("=> 수정할 ID: ");
        int id = s.nextInt();
        s.nextLine();

        System.out.print("=> 새 제목 : ");
        String subject = s.nextLine().trim();

        if (subject.isEmpty()) {
            System.out.println("제목은 비워둘 수 없습니다. 수정 취소.");
            return;
        }

        String sql = "UPDATE board SET subject = ?, updated_date = DATETIME('now', 'localtime') WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setInt(2, id);
            int count = pstmt.executeUpdate();
            if (count > 0) {
                System.out.println("게시글이 수정되었습니다.");
            } else {
                System.out.println("해당 ID의 게시글이 없습니다.");
            }
        } catch (SQLException e) {
            System.err.println("데이터 수정 오류: " + e.getMessage());
        }
    }

    @Override
    public void deleteItem() {
        System.out.print("=> 삭제할 ID: ");
        int id = s.nextInt();
        s.nextLine();

        System.out.print("=> 정말로 삭제하시겠습니까? (Y/n) ");
        String answer = s.next();
        if (answer.equalsIgnoreCase("Y")) {
            String sql = "DELETE FROM board WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                int count = pstmt.executeUpdate();
                if (count > 0) {
                    System.out.println("게시글이 삭제되었습니다.");
                } else {
                    System.out.println("해당 ID의 게시글이 없습니다.");
                }
            } catch (SQLException e) {
                System.err.println("데이터 삭제 오류: " + e.getMessage());
            }
        } else {
            System.out.println("삭제가 취소되었습니다.");
        }
    }

}