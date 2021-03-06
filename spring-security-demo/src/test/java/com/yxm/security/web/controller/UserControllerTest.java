package com.yxm.security.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 使用SpringRunner类运行测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    /**
     * 伪造mvc环境  伪造的环境不会真正去启动tomcat
     */
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    /**
     * 每次执行测试用例前执行这个方法
     */
    @Before
    public void setup(){
        /**
         * 构造mvc环境
         */
        //mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception{
        //发送一个url为/user的GET请求
        /* String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")//请求user
                .param("username","Jack")//传递请求参数
                 .param("age","18")
                 .param("size", "15")
                 .param("page", "3")
                 .param("sort", "age,desc")
                 .contentType(MediaType.APPLICATION_JSON))//发送请求类型:application-json
                 .andExpect(MockMvcResultMatchers.status().isOk())//结果执行的期望-返回状态码isOk
                 .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))//期望返回结果为3
                 .andReturn().getResponse().getContentAsString();

         System.out.println("whenQuerySuccess:"+result);*/
         //结果输出：whenQuerySuccess:[{"username":null},{"username":null},{"username":null}]
    }

    @Test
    public void whenDetailInfoSuccess() throws Exception{
        //发送一个url为/user/1 的GET请求
        /* String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                 .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.username")
                         .value("Jack"))
                 .andReturn().getResponse().getContentAsString();

         System.out.println("whenDetailInfoSuccess:"+result);*/
         //输出结果:whenDetailInfoSuccess:{"username":"Jack","password":null}
    }

    @Test
    public void whenDetailInfoFail() throws Exception{
     /* mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
              .contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(MockMvcResultMatchers.status().is4xxClientError());*/
    }

    @Test
    public void whenCreateSuccess() throws Exception{
        /*long time = new Date().getTime();
        String content = "{\"username\":\"Jack\",\"password\":null,\"birthday\":"+time+"}";
        String resut = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))//返回的json对象属性id值为1
                 .andReturn().getResponse().getContentAsString();

        System.out.println("Test:"+resut);*/
        //Test:{"id":"1","username":"Jack","password":null,"birthday":1582190517178}
    }

    @Test
    public void whenUpdateSuccess() throws Exception{
        //与创建区别:创建时候是post请求,我们修改时候用put请求,创建时候是没有id的但是修改时候需要根据id去修改,所以有id
      /*  Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());//定义未来时间作为生日过去时间的校验
        System.out.println(date);
        String content = "{\"id\":\"1\",\"username\":\"Jack\",\"password\":null,\"birthday\":"+date.getTime()+"}";
        String resut = mockMvc.perform(MockMvcRequestBuilders.put("/user/1")//针对id为1的用户进行修改
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))//返回的json对象属性id值为1
                .andReturn().getResponse().getContentAsString();

        System.out.println("Test:"+resut);*/
        //Test:{"id":"1","username":"Jack","password":null,"birthday":1582190517178}
    }

    @Test
    public void whenDeleteSuccess() throws Exception{
    /*    mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")//针对id为1的用户进行修改
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());*/
    }

    @Test
    public void whenUploadSuccess() throws Exception{
     /* String result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")//请求参数url
               //file-方法参数名  test.txt--file里面传递的文件名  multipart/form-data--参数类型   hello upload--参数内容
               .file(new MockMultipartFile("file","test.txt","multipart/form-data","hello upload".getBytes("UTF-8"))))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andReturn().getResponse().getContentAsString();

      System.out.println("whenUploadSuccess:"+result);*/
    }
}
