package com.dhcc.urms;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
public class UrmsApplicationTests {

    @BeforeEach
    public void init() {
        System.out.println("<========== 开始测试 ==========>");
    }

    @AfterEach
    public void after() {
        System.out.println("<========== 测试结束 ==========>");
    }
}
