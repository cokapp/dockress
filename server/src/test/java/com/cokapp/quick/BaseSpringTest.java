package com.cokapp.quick;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//使用junit4进行测试
@RunWith(SpringJUnit4ClassRunner.class)
// 暂时直接使用开发环境的配置文件
@ContextConfiguration({ "/**/*.xml" })
public class BaseSpringTest {
}