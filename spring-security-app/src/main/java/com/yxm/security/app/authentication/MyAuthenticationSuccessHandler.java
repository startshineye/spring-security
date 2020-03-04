package com.yxm.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxm.security.core.enums.LoginType;
import com.yxm.security.core.properties.SecurityProperties;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.social.security.provider.OAuth2AuthenticationService;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yexinming
 * @date 2020/2/23
 **/
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        /**
         * 将Authentication转换成json返回给前端
         * 参数:authentication 使用不同登录方式,其值是不一样的,这是一个接口在实际运转中,他会传不同的实现对象过来
         */
        logger.info("登录成功");

        //1.解析clientId
        String header = request.getHeader("Authorization");
        if (header == null && !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无clientId信息");
        }
        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;
        String clientId = tokens[0];
        String clientSecret = tokens[1];


        //2.获取ClientDetails并作校验
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if(null == clientDetails){//说明我们根据配置的yxm拿不到第三方ClientDetails信息。我们应该抛出异常
          throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:"+clientId);
        }else if(!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)){//如果clientDetails存在,我们就应该校验clientSeret
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:"+clientSecret);
        }

        //3.获取TokenRequest:第一个参数map主要是为了创建Authentication；但是上面Authentication已经创建。我们可以将其设置为空
        //因为grantType为:授权类型 之前是:password、authentication_code、implit、Client Credential、我们这里为自定义模式
        /**
         * 类:ResourceOwnerPasswordTokenGranter
         *
         * protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
         *         Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
         *         String username = (String)parameters.get("username");
         *         String password = (String)parameters.get("password");
         *         parameters.remove("password");
         *         Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
         * }
         */
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,clientId, clientDetails.getScope(), "custom");

        //4.创建OAuth2Request
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        //5.创建OAuth2Authentication
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        //6.响应返回
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(accessToken));
    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }
}
