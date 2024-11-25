package day30.boardservice11mvc.model;

import java.sql.*;
import java.util.ArrayList;

public class BoardDao {


    //JDBC 인터페이스 ,
    private Connection conn; //***


    // 싱글톤
    private static BoardDao boardDao = new BoardDao();
    private BoardDao(){
        //1.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//***
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb1125","root","1234");//***
            System.out.println("[ Board DB Connection OK]");
        } catch (ClassNotFoundException e) {
            e.getMessage();
            System.out.println("[ Board DB Connection fail]");
        } catch (SQLException e) {
            e.getMessage();
            System.out.println("[ Board DB Connection fail]");
        }
        //2.
    }
    public static BoardDao getInstance(){
        return boardDao;
    }


    // 1. 게시물 등록 접근 함수
    public boolean boardWrite( BoardDto boardDto){
        try {
        //1. sql 작성, SQL 그대로 작성 하되 데이터가 들어가는 자리는 ? 로 작성
        String sql = "insert into board(content,writer,pwd)values(?,?,?)";
        //2. 작성한 SQL를 DB연동객체의 기재한다. prepare:준비한다 , Statement : 기재한다 , ==> SQL기재할준비한다
            // - 연동된 객체로부터 SQL기재해서 준비된 객체를 PreparedStatement 인터페이스에 대입한다.
        PreparedStatement ps = conn.prepareStatement(sql);
        // 3. 기재된 SQL의 매개변수 (?) 에 값을 대입한다.
            // ps.setString( ?순서번호 , 대입할데이터 )
        ps.setString(1, boardDto.getContent()); //1: SQL내 첫번째 ? 뜻한다 : 첫번째 ? 에 입력받은 게시물내용을 대입한다.
        ps.setString(2, boardDto.getWriter()); //2: SQL내 두번째 ? 뜻한다 : 두번째 ? 에 입력받은 게시물 작성자를 대입한다.
        ps.setInt(3, boardDto.getPwd()); //3: SQL내 세번째 ? 뜻한다 : 세번째 ? 에 입력받은 게시물 비밀번호를 대입한다.
        // 4. 기재된 SQL 를 실행한다. execute: 실행하다 , Update:최신화하다, => sql 실행후 최신화한다.
        ps.executeUpdate();
        // 5. 성공했을때 true 반환
            return true;
        }catch (SQLException e){
            e.getMessage();
            System.out.println("[ 게시물 등록시 예외발생 ]");
        }
        //실패 또는 오류 발생시 false반환
        return false;
    }

    //2. 게시물 출력 접근 함수
    public ArrayList<BoardDto> boardPrint( ){
        ArrayList<BoardDto> list = new ArrayList<>(); // -- 조회된 레코드들을 객체화 해서 저장할 리스트 객체
        try {
        // 1.SQL 작성
        String sql = "select * from board";
        // 2.SQL 기재
        PreparedStatement ps = conn.prepareStatement(sql);
        // 3.SQL 조작 , executeQuery() : sql 실행 결과 조회된 SQL 결과를 조작하는 resultSet 객체 반환
        // 4.SQL 실행
        ResultSet rs = ps.executeQuery();
        // 5.SQL 결과
            while (rs.next()){ // while ( 조건 ) {} : 반복문 , rs.next() : 조회 결과에서 다음 레코드로 이동 함수
                // 만약에 결과 레코드가 3개 존재하면 rs.next() 3번 실행 된다.
                // [해석] 조회 결과첫번째 레코드 부터 마지막 레코드 까지 하나씩 레코드 이동
                // 6. 레코드를 읽어서 각 필드별 레코드 호출
                int num = rs.getInt("num");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                int pwd = rs.getInt("pwd");
                // 7. 각 레코드의 호출된 필드값들을 객체화 --> DTO 생성
                BoardDto boardDto = new BoardDto(content,writer,pwd); // 게시물 번호는과제 생각해보기
                //8 .1개 레코드를 DTO 객체로 반환된 DTO를 리스트에 저장
                list.add(boardDto);
            }// w end // - 반복문 1번에 레코드 1개를 dto로 변환
        }catch (SQLException e){
            e.getMessage();System.out.println("[ 게시물 출력시 예외발생 ]");
        }
        // 9. 구성한 리스트 객체를 반환
        return list;
    }//method end

}//c end
