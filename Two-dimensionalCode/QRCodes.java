package cn.edu.scu.util.qrcode;

import g4studio.base.common.Dto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

/**
 * Servlet implementation class QRCodeServlet
 */
public class QRCodes {
	private static Log log = LogFactory.getLog(QRCodes.class);
	public static String textToJPG(String text, File destDir) {

		if(text==null || text.isEmpty())
			return null;
		
		if(destDir==null || !destDir.exists() ||  !destDir.isDirectory() )
			return null;
		
		try {
			ByteArrayOutputStream out=QRCode.from(text).to(ImageType.JPG).stream();
			String qrcodeFileName = UUID.randomUUID().toString();
			File file = new File(destDir.getAbsolutePath()+"/"+qrcodeFileName+".jpg");
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(out.toByteArray());
			fout.flush();
			fout.close();
			
			return file.getAbsolutePath();
		}
		catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	public static String textToPNG(String text, File destDir) {

		if(text==null || text.isEmpty())
			return null;
		
		if(destDir==null || !destDir.exists() ||  !destDir.isDirectory() )
			return null;

		try {
			ByteArrayOutputStream out=QRCode.from(text).to(ImageType.PNG).stream();
			String qrcodeFileName = UUID.randomUUID().toString();
			File file = new File(destDir.getAbsolutePath()+"/"+qrcodeFileName+".png");
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(out.toByteArray());
			fout.flush();
			fout.close();
		log.info(file.getAbsolutePath());
			return file.getAbsolutePath();
		}
		catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
	public static String CreateQRCode(Dto dto){
		String dirPath=dto.getAsString("dirPath");
		String userid=dto.getAsString("userid");
		String username=dto.getAsString("username");
		String info=userid+","+username;
		File dir = new File(dirPath);
		System.out.println("dir is "+dir);
		if (!dir.exists())
			dir.mkdirs();
		String qrcodeFilename = textToPNG(info, dir);
		qrcodeFilename = qrcodeFilename.replaceAll("\\\\", "/");;
		String qrcodeURL = qrcodeFilename == null ? null : "/Foundation"
				+ qrcodeFilename.substring(qrcodeFilename
						.indexOf("/qrcode/"), qrcodeFilename.length());
		return qrcodeURL;
	}

}
