package com.yxm.security.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * WireMock服务端:连接wiremock启动server端
 * @author yexinming
 * @date 2020/2/22
 **/
public class MockServer {
    /**
     * 因为是一个启动类,所以需要具有main方法
     * @param args
     */
    public static void main(String[] args) throws IOException{
        WireMock.configureFor(8063); //1.告诉wiremock服务端在哪
        WireMock.removeAllMappings();//移除之前所有的配置,把最新参数添加进去

        mock("/order/1", "01");
        mock("/order/2", "02");
      }

    private static void mock(String url, String file) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/" + file + ".txt");
        String content = StringUtils.join(FileUtils.readLines(resource.getFile(), "UTF-8").toArray(), "\n");
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(url)).willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
    }
}
