package day29.boardservice10mvc.model;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
/*
DAO data Acceess object

*/

public class BoardDao {


    //private Connection connection;
    //DB연동시 필요한 코드 2줄

    //Class.forName("com.mysql.cj.jdbc.Driver");
    //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb1122","root","1234");

    private Connection connection; // DB연동 인터페이스 // 연동 결과를 가지고 있어서 DB조작이 가능한 인터페이스

    // 싱글톤
    private static BoardDao boardDao = new BoardDao();
    private BoardDao(){
        try {
            //DB연동시 필요한 코드 2줄
            Class.forName("com.mysql.cj.jdbc.Driver");
            //DriverManager.getConnection("MYSQLSERVER_URL/DB명","계명정","비밀번호");
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb1122","root","1234");
                System.out.println("DB 연동 성공");
            } catch (SQLException e) {e.getMessage();}
        } catch (ClassNotFoundException e) {e.getMessage();}
    }
    public static BoardDao getInstance(){



        return boardDao;
    }

    // 여러개 게시물 저장하는 리스트
    ArrayList<BoardDto> boardDB = new ArrayList<>();

    // 1. 게시물 등록 접근 함수
    public boolean boardWrite( BoardDto boardDto){
        try {
            PreparedStatement ps = connection.prepareStatement("insert into board(bcontent, bwriter, bpwd)valuse( '여기는 JAVA','유재석','1234')");
            ps.execute();
        } catch (SQLException e) {
            e.getMessage();
        }
        return true;
    }

    // 2. 게시물 출력 접근 함수
    public ArrayList<BoardDto> boardPrint( ){
        return boardDB;
    }

}
