package com.yxm.security.core.validate;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
/**图片验证码
 * @author yexinming
 * @date 2020/2/24
 **/
public class ImageCode extends SmsCode{
    private BufferedImage image;

    public ImageCode(BufferedImage image,String code,int expireIn){
        super(code,expireIn);
        this.image=image;
    }
    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
