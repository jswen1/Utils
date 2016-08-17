package cn.edu.scu.util.security;

import java.io.*;
import java.security.MessageDigest;

/**
 * 该类计算文件的MD5 checksum
 * 
 * 在附件上传（工作记录附件、协议文本附件、项目立项表附件）上传的时候，涉
 * 及到附件的预览操作，这些文件的格式有多种，在涉及到office文档附件预览的
 * 时候，由于需要首先通过工具类xdoc进行文档格式的转换，而转换过程又比较
 * 长。设想这样一种情景，用户先选择了office文档1，然后选择了2，然后选择了
 * 文档1，第一次、第三次选择的是相同的文件，这个时候，后台不应该重复为相
 * 同的文档重复生成预览所需的pdf文件，为了避免后台重复生成相同的文档，我
 * 们对用户上传的文档进行md5校验，如果输入文件的md5值相同，则表明用户先
 * 后选择了相同的文件，此时不予重新生成文档预览，至于如何获取先前生成的文
 * 档，前期在各action对应类中进行具体的实现，后期可以根据需要封装成一个工
 * 具类。
 * 
 * @author zhangjie
 * @date 2014-09-12
 *
 */
public class MD5Checksum {

   public static byte[] createChecksum(String filename) throws Exception {
       InputStream fis =  new FileInputStream(filename);

       byte[] buffer = new byte[1024];
       MessageDigest complete = MessageDigest.getInstance("MD5");
       int numRead;

       do {
           numRead = fis.read(buffer);
           if (numRead > 0) {
               complete.update(buffer, 0, numRead);
           }
       } while (numRead != -1);

       fis.close();
       return complete.digest();
   }

   public static byte[] createChecksum(File file) throws Exception {
	   InputStream fis = new FileInputStream(file);

       byte[] buffer = new byte[1024];
       MessageDigest complete = MessageDigest.getInstance("MD5");
       int numRead;

       do {
           numRead = fis.read(buffer);
           if (numRead > 0) {
               complete.update(buffer, 0, numRead);
           }
       } while (numRead != -1);

       fis.close();
       return complete.digest();
   }

   // see this How-to for a faster way to convert
   // a byte array to a HEX string
   public static String getMD5Checksum(String filename) throws Exception {
       byte[] b = createChecksum(filename);
       String result = "";

       for (int i=0; i < b.length; i++) {
           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
       }
       return result;
   }

   public static String getMD5Checksum(File file) throws Exception {
       byte[] b = createChecksum(file);
       String result = "";

       for (int i=0; i < b.length; i++) {
           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
       }
       return result;
   }

   /**
    * MD5 checksum计算测试
    * @param args
    */
   /*
   public static void main(String args[]) {
	   for(int i=0; i<args.length; i++)
		   System.out.println(args[i]);
	   if(args.length<1) {
		   System.out.println("error syntax!\nright syntax: java MD5Checksum filepath");
		   return;
	   }

	   File f = new File(args[0]);
	   if(!f.exists() || !f.isFile()) {
		   System.out.println("invalid filepath!\nright syntax: "+args[0]+" is not a valid file");
		   return;
	   }

       try {
           System.out.println(getMD5Checksum(args[0]));
       }
       catch (Exception e) {
           e.printStackTrace();
       }
   }
   */
}
