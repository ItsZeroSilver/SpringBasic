package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        //basePackages: 탐색할 패키지의 시작 위치를 지정
        //basePackages = "hello.core.member",
        //basePackageClasses: 지정한 클래스의 패키지를 탐색 시작 위로 지정한다.
        //basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        //AppConfig와 겹치지 않게 하기 위해서
        //기존 예제 코드를 최대한 남기고 유지를 하기 위해서

        //만약 지정하지 않으면 @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치
        //package hello.core; 가 시작

        //권장하는 방법
        //설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것
)

public class AutoAppConfig {
    //중복 등록과 충돌
    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
