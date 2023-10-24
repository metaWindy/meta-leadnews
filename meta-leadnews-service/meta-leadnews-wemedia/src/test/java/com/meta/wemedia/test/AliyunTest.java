package com.meta.wemedia.test;

import com.meta.common.aliyun.AliAutoRoute;
import com.meta.wemedia.WemediaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class AliyunTest {


    @Autowired
    private AliAutoRoute aliAutoRoute;

    /**
     * 测试文本内容审核
     */
    @Test
    public void testScanText() throws Exception {
        Map<String, String> map = aliAutoRoute.textAutoRoute("我有冰毒");
    }

    /**
     * 测试图片审核
     */
   @Test
    public void testScanImage() throws Exception {
       aliAutoRoute.imgAutoRoute("https://img.alicdn.com/tfs/TB1U4r9AeH2gK0jSZJnXXaT1FXa-2880-480.png");
    }
}


