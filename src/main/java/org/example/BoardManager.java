package org.example;

import java.util.Scanner;

public class BoardManager {
    Scanner s = new Scanner(System.in);
    BoardCRUD boardCRUD;

    BoardManager() {
        boardCRUD = new BoardCRUD(s);
    }

    public int selectMenu() {
        System.out.print(
                "*** 게시판 관리 프로그램 ***\n"
                        + "************************\n"
                        + "1. 게시글 전체 목록\n"
                        + "2. 새 게시글 추가\n"
                        + "3. 게시글 수정\n"
                        + "4. 게시글 삭제\n"
                        + "5. 제목으로 검색\n"
                        + "6. 작성자명으로 검색\n"
                        + "7. 조회수 랭킹 (Top 5)\n"
                        + "0. 종료\n"
                        + "************************\n"
                        + "=> 원하는 메뉴는? ");

        int menu = -1;
        try {
            menu = s.nextInt();
        } catch (Exception e) {
            s.nextLine(); // 잘못된 입력 비우기
            return -1; // 잘못된 메뉴 선택
        }
        return menu;
    }

    public void start() {
        while (true) {
            int menu = selectMenu();
            if (menu == 0) {
                boardCRUD.closeConnection();
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            switch (menu) {
                case 1:
                    boardCRUD.listAll();
                    break;
                case 2:
                    boardCRUD.addItem();
                    break;
                case 3:
                    boardCRUD.updateItem();
                    break;
                case 4:
                    boardCRUD.deleteItem();
                    break;
                case 5:
                    boardCRUD.searchTitle();
                    break;
                case 6:
                    boardCRUD.searchAuthor();
                    break;
                case 7:
                    boardCRUD.viewRanking();
                    break;
                default:
                    System.out.println("잘못된 메뉴입니다. 0-7 사이의 숫자를 입력하세요.");
            }
            System.out.println(); // 메뉴 간 구분을 위한 공백
        }
        s.close();
    }
}