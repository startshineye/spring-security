package com.yxm.security.core.validate;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
/**图片验证码
 * @author yexinming
 * @date 2020/2/24
 **/
public class ImageCode{
    private BufferedImage image;
    /**
     * code是一个随机数,图片是根据随机数生成的,
     * 存放到session里面,后面用户提交登录请求时候要去验证的
     */
    private String code;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    public ImageCode(BufferedImage image,String code,int expireIn){
      this.image=image;
      this.code=code;
        /**
         * 过期时间传递的参数应该是一个秒数:根据这个秒数去计算过期时间
         */
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }
    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
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
