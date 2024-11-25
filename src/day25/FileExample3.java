package day25;

import java.io.*;
import java.util.Scanner;

public class FileExample3 {
    public static void main(String[] args) {

        // 생각해보기 :
        try {
            // 1. (파일입력)키보드로부터 입력받은 문자열을 'day25'폴더내 test2.txt 에 저장하시오.
            // [1] 입력 객체
            Scanner scan = new Scanner(System.in);
            // [2] 키보드로 입력 받기
            System.out.print("파일에 저장할 내용 : ");
            String outStr = scan.next();
            // [3] 파일출력 객체 생성 , 파일의 경로 지정
            FileOutputStream outputStream = new FileOutputStream("./src/day25/test2.txt");
            // [4] 파일출력 객체를 이용한 문자열 출력하기.   , .write( "문자열".getBytes() );
            outputStream.write(outStr.getBytes());        System.out.println("[ 저장 성공 ]");

            // 2. (파일출력)'day25'폴더내 test2.txt 에 저장된 (문자열)바이트 를 읽어 콘솔에 출력하시오.
            // [1] 파일입력 객체 생성 , 위에서 생성된 메모장을 읽어오기
            FileInputStream inputStream = new FileInputStream("./src/day25/test2.txt");

            // [2] 읽어온 바이트를 저장할 바이트 배열 선언
            // byte[] bytes = new byte[1000]; // 1000개의 바이트( 영문1글자당 1바이트 , 한글1글자당 2바이트 ) 를 저장할수 있는 배열 선언
            // 현재 읽어올 파일의 용량을 몰라서 임의로 1000개로 한다.

            // [2-2] 읽어올 파일을 용량/바이트개수 를 확인 후 파일의 용량만큼 배열 선언
            File file = new File("./src/day25/test2.txt");
            System.out.println( file.isFile() );    // true
            System.out.println( file.getName() );   // test2.txt
            System.out.println( file.length() );    // 반환타입 long
            byte[] bytes = new byte[ (int)file.length() ];
                // (캐스팅) : 강제 타입 변환 , long타입을 int 타입으로 변환
                // 배열내 [ ] 안에 들어가는 배열크기는 타입이 정수(int)만 가능하다.

            // [3] 준비된 바이트 배열에 읽어온 바이트를 저장한다.
            inputStream.read( bytes );
            // [4] 바이트를 문자열로 다시 변환 하기
            String inStr = new String( bytes );
            System.out.println("[ 불러오기 성공 ]");    System.out.println( inStr );

        // ! : 만약에 파일 객체가 지정한 경로에 파일이 존재하지 않으면 예외 발생
        }catch ( FileNotFoundException e ){ e.printStackTrace(); }
        // ! : 만약에 파일 객체가 입/출력 중일때 외부적인 요소로 문제가 발생하면 예외 발생
        catch ( IOException e ){ e.printStackTrace();}
    } // main e
} // class e
