package com.yxm.security.core.social.qq.connect;

import com.yxm.security.core.social.qq.api.QQ;
import com.yxm.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author yexinming
 * @date 2020/2/29
 **/
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     * 测试qq的api是否可用,我们默认为true
     * @param qq
     * @return
     */
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    /**
     * 让Connection和Api之间做一个适配
     * @param qq
     * @param connectionValues
     */
    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        //1.获取Api信息
        QQUserInfo userInfo = qq.getUserInfo();
        //2.将用户信息值设置到connection中
        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_1());
        connectionValues.setProfileUrl(null);//qq是没有个人主页的
        connectionValues.setProviderUserId(userInfo.getOpenId());//授权服务方的用户id:这里指代qq的openId
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    /**
     * 与:ProfileUrl类似,在某些社交网站才有这个概念,比如说微博,微博才可以
     *发一个message,发一个消息去更新微博;qq是没有时间线个个人主页的问题
     * 所以我们这里什么都不做:do nothing
     * @param qq
     * @param s
     */
    @Override
    public void updateStatus(QQ qq, String s) {
        //do nothing
    }
}
