package songhq.com.cache.utils;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class BinaryToImg {
    static void base64StringToImage(String base64String, String filePath){      
        try {      
            byte[] bytes1 = Base64.decode(base64String);      
                  
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);      
            BufferedImage bi1 =ImageIO.read(bais);
            if(StringUtil.isNullStr(filePath)) {
            	filePath = "D://QQ.bmp";
            }
            File w2 = new File(filePath);//可以是jpg,png,gif格式      
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动      
        } catch (IOException e) {      
            e.printStackTrace();     
        }
    }
    /*public static void main(String[] args) throws IOException {
        String b = "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";
        base64StringToImage(b);
    }*/
}
