package day25.boardservice9mvc.model;
import java.io.*;
import java.util.ArrayList;
/*
    boardservice9mvc
        - boardservice8mvc 패키지를 복사해서 boardservice9mvc 패키지명 변경후 진행
        - 조건 : 파일 입출력을 활용하여 프로그램이 종료되고 다시 실행 했을때 영구 저장 되도록 하시오.

        [생각해보기]
        1. txt 메모장는 *문자열*만 저장되는 윈도우 프로그램의 확장자 , txt(객체지향 X)
        2. 게시물들( ArrayList<BoardDto> ) 저장를 하기 위해서는 변환이 필요하다. , java(객체지향 O)
        문제점 발견 : 서로 다른 언어/프로그램 들 간의 데이터를 주고 받을려면 형식/타입이 같으면 좋을텐데.
        - 관례적으로 사용되는 타입 : CSV , JSON , XML 파일 타입 주로 사용한다.
        --> 서로 다른 언어/프로그램 들 간의 공통된 형식/타입을 사용 하므로 데이터 통신하자.!!!

        [ CSV ]
        1. , 쉼표로 구분된 문자열로 구성된 파일 형식
        2. \n 이용한 줄바꿈 처리를 한다.
        3. .csv 확장자 가진다.

        [ 게시물 객체를 CSV 형식의 문자열로 변환 ]
        1. boardDto( content="안녕하세요" , writer="유재석" , pwd=1234 )   :  자바 객체를 임의로 시각화
        2. 하나의 문자열로 필드명을 제외한 필드값들을 ,쉼표 구분하여 문자열로 변환 : "안녕하세요,유재석,1234"

        [ 여러개 게시물 객체가 존재 했을때 ]
        point1 : 객체내 필드간의 구분을 ,쉼표로 한다
        point2 : 객체간의 구분을 \n으로 한다.
            예] 만일 게시물 2개일때 "안녕하세요,유재석,1234\n그래안녕,강호동,4567"

 */
public class BoardDao {
    // 싱글톤
    private static BoardDao boardDao = new BoardDao();
    private BoardDao(){

        // 만약에 파일를 로드 하는데.. 파일이 존재하지 않으면
            // [1] 파일 경로에 따른 파일 객체화
        File file = new File("./src/day25/boardservice9mvc/data.txt");
            // [2] 객체화 한 파일이 존재 하는지 확인
        if( file.exists() ){ // - 지정한 경로의 파일이 있다.
            // - 싱글톤(static) 이 생성될때( 프로그램이 실행될때 )
            fileLoad(); // 파일 로드
        }else{ // - 지정한 경로의 파일이 없다.
            try {
                file.createNewFile(); // .createNewFile() : 파일 생성 함수
            }catch ( IOException e ){
                e.printStackTrace();
            }
        } // else end
    } // 생성자 end

    public static BoardDao getInstance(){
        return boardDao;
    }
    // 여러개 게시물 저장하는 리스트
    ArrayList<BoardDto> boardDB = new ArrayList<>();

    // 1. 게시물 등록 접근 함수
    public boolean boardWrite( BoardDto boardDto){
        boardDB.add( boardDto );
        fileSave(); // 만약에 리스트에 게시물 객체를 추가 했다면 , 파일에도 추가된 게시물을 쓰기
        return true;
    }
    // 2. 게시물 출력 접근 함수
    public ArrayList<BoardDto> boardPrint( ){  return boardDB;   }

    // =================== 영구저장 을 위한 게시물들을 파일에 저장하는 기능 ======================== //
    public void fileSave(){ // ( 게시물 등록을 성공했을때 지정한 함수 사용/호출 )
        // 여러개 게시물들을[ArrayList<BoardDto> boardDB] 하나의 문자열[String / CSV]로 표현하는 방법
        String outStr = "";             // 1. 임의의 문자열 변수 선언
        // 2. 반복문을 이용한 모든 게시물들을 순회/반복
        for( int index = 0 ; index <= boardDB.size()-1 ; index++ ){ // - 리스트객체내 0번 인덱스부터 마지막인덱스까지 반복
            BoardDto boardDto = boardDB.get( index ); // 3. index번째의 게시물
            // 4. [객체내 필드 구분 ] index번째의 게시물객체내 필드값을 CSV 형식으로 변환 , 필드값을 출력후 사이사이에 ,쉼표 이용한 CSV 형식 만들기
            outStr += boardDto.getContent()+","+boardDto.getWriter()+","+boardDto.getPwd();
            // += 복합대입연산자 : 오른쪽값을 왼쪽변수 값 와 더한후에 결과를 왼쪽 변수에 대입한다.
            outStr += "\n";   // 5. [객체 구분]
        }System.out.println( outStr ); // 확인용
        // - try{}catch(){} : try{} 에서 예상치 못한 예외가 발생 했을때 지정된 catch 코드로 흐름을 이동하는 문법
        try {
            // [1] 파일 출력 객체 생성한다.
            FileOutputStream outputStream = new FileOutputStream("./src/day25/boardservice9mvc/data.txt");
            // [2] 파일 출력 객체를 이용한 바이트 쓰기/내보내기
            outputStream.write( outStr.getBytes() ); // 위에서 객체들을 모두 CSV 형식으로 변환된 문자열를 바이트로 변환
            // [3] 안내 메시지
            System.out.println("[파일 저장 성공]");
        }catch ( FileNotFoundException e ){ e.printStackTrace();}
        catch ( IOException e ){ e.printStackTrace(); }
    }
    // =================== 영구저장 된 파일의 게시물들을 가져오는 기능 ============================= //
    public void fileLoad( ) { // ( 프로그램이 실행 되었을때 1번 만 실행 ) // DAO 객체(싱글톤) 생성될때 메모장(TXT) 불러오기 한다.
        try {
            // [1] 파일 입력 객체 생성
            FileInputStream inputStream = new FileInputStream("./src/day25/boardservice9mvc/data.txt");
            // [2] 파일 용량만큼 바이트 배열 선언
            File file = new File("./src/day25/boardservice9mvc/data.txt");
            byte[] bytes = new byte[ (int) file.length()];
            // [3] 파일 입력 객체를 이용한 파일 읽어서 바이트 배열 저장
            inputStream.read(bytes);
            // [4] 읽어온 바이트 배열을 문자열(String)으로 변환
            String inStr = new String( bytes );
            // 활용과제 : 파일로 부터 읽어온 문자열의 게시물 정보들을 다시 ArrayList<BoardDto> boardDB 에 저장하시오.
            // 목표 : 파일로 가져온 문자열내 저장된 여러개 게시물들을 객체화 하고 게시물객체를 리스트에 담자.
                // "안녕하세요,유재석,1234\n그래안녕,강호동,4567"
            // [1] 객체 구분문자(\n:임의)  , 문자열 분해 , "문자열".split("기준문자") : 문자열내 기준문자 기준으로 분해 후 배열로 반환
                // inStr = "안녕하세요,유재석,1234\n그래안녕,강호동,4567\n"
                // inStr.split("\n")  -> [ "안녕하세요,유재석,1234" , "그래안녕,강호동,4567" ]
                String[] objStr = inStr.split("\n");
            // [2] 객체내 필드 구분 문자(,:쉼표)
            for( int i = 0 ; i <= objStr.length - 1 ; i++ ){ // 마지막 줄 제외를 하기 위해 -1
                String obj = objStr[i]; // [3] 1개의 객체 필드 값들 가져오기
                //  objStr = [ "안녕하세요,유재석,1234" , "그래안녕,강호동,4567" ]
                //  objStr[0] = "안녕하세요,유재석,1234"
                //  objStr[0].split(",")   -->   [  "안녕하세요" , "유재석" , "1234" ]
                //  field = [  "안녕하세요" , "유재석" , "1234" ]
                //  field[0] = "안녕하세요"
                //  [4] 문자열로 된 필드 값들을 객체로 변환하기
                String[] field = obj.split(",");
                String content = field[0];
                String writer = field[1];
                int pwd = Integer.parseInt( field[2] ) ; // String 타입을 int 으로 변환하는 방법 , Integer.parseInt("문자열")
                BoardDto boardDto = new BoardDto( content , writer , pwd );
                // [5] 리스트에 담기
                boardDB.add( boardDto );
            }
        }catch ( FileNotFoundException e ){ e.printStackTrace();}
        catch ( IOException e ){ e.printStackTrace(); }
    }
}









