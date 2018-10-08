package songhq.com.cache.utils;


import java.io.BufferedInputStream;
import java.io.FileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileOperationUtil {

	    static Logger log = LoggerFactory.getLogger(FileOperationUtil.class);
	
	//返回文件的编码格式
		public static String getFileCharset(String fileName){
			byte[] first3Bytes = new byte[3];
			String charset = "GBK";
			try{
				boolean checked = false;
				BufferedInputStream bis = new BufferedInputStream(Object.class.getResourceAsStream(fileName));
				bis.mark(0);
				int read = bis.read(first3Bytes, 0, 3);
				if(read == -1){
					return charset;
				}else if(first3Bytes[0] == (byte)0xFF && first3Bytes[1] == (byte)0xFE){
					charset = "UTF-16LE";
					checked = true;
				}else if(first3Bytes[0] == (byte)0xFE && first3Bytes[1] == (byte)0xFF){
					charset = "UTF-16BE";
					checked = true;
				}
				else if(first3Bytes[0] == (byte)0xEF && first3Bytes[1] == (byte)0xBB && first3Bytes[2] == (byte)0xBF){
					charset = "UTF-8";
					checked = true;
				}
				bis.reset();
				if(!checked){
					int loc = 0;
					while((read = bis.read()) != -1){
						loc++;
						if(read >=0xF0) break;
						if(0x80 <= read && read <= 0xBF) break;
						if(0xC0 <= read && read <= 0xDF){
							read =bis.read();
							if(0x80 <= read && read <= 0xBF) continue;
							else break;
						}else if(0xE0 <= read && read <= 0xEF){
							read = bis.read();
							if(0x80 <= read && read <= 0xBF){
								read = bis.read();
								if(0x80 <= read && read <= 0xBF){
									charset = "UTF-8";
									break;
								}else break;
							}else break;
						
						}
					}
				}
				bis.close();
			}catch(Exception e){
				log.error("Lack commonUserInfo file!");
			}
			return charset;
		}
	
	
}
