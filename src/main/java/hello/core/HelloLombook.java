package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//lombok 기능 getter, setter자동으로 만들어줌.
@Getter
@Setter
@ToString
public class HelloLombook {
    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombook helloLombook = new HelloLombook();
        helloLombook.setName("helloLombok");
        System.out.println("helloLombook = " + helloLombook);
        String name = helloLombook.getName();
        System.out.println("name = " + name);
    }
}
