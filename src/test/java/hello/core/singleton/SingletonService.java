package hello.core.singleton;

public class SingletonService {

    //1.static 영역에 객체를 딱 1 개만 생성한다.
    private static final SingletonService instance=new SingletonService();

    //2.public 으로 객체 인스턴스 필요하면 이 static 메소드를 통해 조회할 수 있도록 허용
    public static SingletonService getInstance(){
        return instance;
    }

    //3.생성자를 private으로 선언해 외부에서 new 연산자로 생성하지 못하도록 막음
    private SingletonService(){}

    public void logic(){
        System.out.println("싱글톤 개게 로직 호출 ! ");
    }
}
