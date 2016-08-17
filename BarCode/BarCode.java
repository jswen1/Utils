package cn.edu.scu.util.barcode;

/* 
 * To change this template, choose Tools | Templates 
 * and open the template in the editor. 
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

public class BarCode {

	public static void main(String[] paramArrayOfString) {
		
	}
	
	public static String CreateBarCode(HttpServletRequest request,String str){
		//得到系统路径
		String dirPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "barcode/";
		//使用正则表达式将路径中的\ 全部转为 / 以便统一
		dirPath = dirPath.replaceAll("\\\\", "/");
		File dir = new File(dirPath);
		if (!dir.exists())
			dir.mkdirs();
		//随机生成文件名
		String barcodeFileName = UUID.randomUUID().toString();
		String filePath=dirPath+barcodeFileName+".png";
		
		try {
			JBarcode localJBarcode = new JBarcode(Code39Encoder.getInstance(),
					WideRatioCodedPainter.getInstance(),
							BaseLineTextPainter.getInstance());
			BufferedImage localBufferedImage = localJBarcode.createBarcode(str);
			saveToPNG(localBufferedImage, filePath);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return filePath;
	}
	
	public static String CreateBarCode1(HttpServletRequest request,String str){
		//得到系统路径
		String dirPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "barcode/";
		//使用正则表达式将路径中的\ 全部转为 / 以便统一
		dirPath = dirPath.replaceAll("\\\\", "/");
		File dir = new File(dirPath);
		if (!dir.exists())
			dir.mkdirs();
		//随机生成文件名
		String barcodeFileName = UUID.randomUUID().toString();
		String filePath=dirPath+barcodeFileName+".png";
		try  
	    {  
	      JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());  
	      BufferedImage localBufferedImage = localJBarcode.createBarcode(str);  
	      saveToGIF(localBufferedImage, "EAN13.gif");  
	      localJBarcode.setEncoder(Code39Encoder.getInstance());  
	      localJBarcode.setPainter(WideRatioCodedPainter.getInstance());  
	      localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());  
	      localJBarcode.setShowCheckDigit(false);  
	      //xx  
	      str = "JBARCODE-39";  
	      localBufferedImage = localJBarcode.createBarcode(str);  
	      saveToPNG(localBufferedImage, "Code39.png");  
	  
	    }  
	    catch (Exception localException)  
	    {  
	      localException.printStackTrace();  
	    } 
		try {
			JBarcode localJBarcode = new JBarcode(Code39Encoder.getInstance(),
					WideRatioCodedPainter.getInstance(),
							BaseLineTextPainter.getInstance());
			BufferedImage localBufferedImage = localJBarcode.createBarcode(str);
			saveToPNG(localBufferedImage, filePath);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return filePath;
	}
	static void saveToJPEG(BufferedImage paramBufferedImage, String paramString) {
		saveToFile(paramBufferedImage, paramString, "jpeg");
	}

	static void saveToPNG(BufferedImage paramBufferedImage, String paramString) {
		saveToFile(paramBufferedImage, paramString, "png");
	}

	static void saveToGIF(BufferedImage paramBufferedImage, String paramString) {
		saveToFile(paramBufferedImage, paramString, "gif");
	}

	static void saveToFile(BufferedImage paramBufferedImage,
			String paramString1, String paramString2) {
		try {
			FileOutputStream localFileOutputStream = new FileOutputStream(
					paramString1);
			ImageUtil.encodeAndWrite(paramBufferedImage, paramString2,
					localFileOutputStream, 96, 96);
			localFileOutputStream.close();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

}
