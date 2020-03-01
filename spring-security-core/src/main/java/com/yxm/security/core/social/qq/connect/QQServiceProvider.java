package com.yxm.security.core.social.qq.connect;
import com.yxm.security.core.social.qq.QQOAuth2Template;
import com.yxm.security.core.social.qq.api.QQ;
import com.yxm.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
/**
 * @author yexinming
 * @date 2020/2/29
 **/
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    private String appId;
    /**
     *应用将用户导向认证服务器时候,导向的url;
     * https://wiki.connect.qq.com/的qq互联:网站应用-->获取Access_Token->Step1：获取Authorization Code的url:PC网站：https://graph.qq.com/oauth2.0/authorize
     */
    private static final String URL_AUTHORIZA="https://graph.qq.com/oauth2.0/authorize";
    private static final String URL_ACCESS_TOKEN="https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId,String appSecret) {
        /**
         * 1.我们从开发流程图上看到:ServiceProvider构建需要OAuth2Operations
         * 2.我们在qq互联上注册我们的应用时候,clientId，clientSecret其实就是qq提供给第三方应用的appId,appSecret,相当于app
         * 的用户名/密码。
         * 3.authorizeUrl,accessTokenUr对应于我们SpringSocial基本原理里面的第一步和第四步。
         * authorizeUrl---应用将用户导向认证服务器时候,导向的url是什么。
         * accessTokenUr---第四部我们申请token时候携带的地址
         */
        super(new QQOAuth2Template(appId,appSecret,URL_AUTHORIZA,URL_ACCESS_TOKEN));
        this.appId=appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        /**
         * 1.我们需要获取Api的时候：由于要求是accessToken是多值得,所以QQImpl是不能通过注解:@Component来注解的
         * 因为final修饰的成员变量是不可变得,一个对象有一个成员变量,是全局的,我们这里用new
         * 2.接受的2个参数:
         * accessToken抽象类会传给我们,我们不用管。
         * appId,需要我们自己处理了：所以我们需要自己传一个,由于appId指代qq提供的id，他是唯一的。
         */
        return new QQImpl(accessToken,appId);
    }
}
