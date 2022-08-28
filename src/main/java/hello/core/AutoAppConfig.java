package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        //AppConfig와 겹치지 않게 하기 위해서
        //기존 예제 코드를 최대한 남기고 유지를 하기 위해서
)

public class AutoAppConfig {
}
