package com.yxm.security.core.validate;
import java.time.LocalDateTime;
/**短信验证码
 * @author yexinming
 * @date 2020/2/24
 **/
public class ValidateCode {
    private String code;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    public ValidateCode(String code, int expireIn){
       this.code=code;
        /**
         * 过期时间传递的参数应该是一个秒数:根据这个秒数去计算过期时间
         */
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }
    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
