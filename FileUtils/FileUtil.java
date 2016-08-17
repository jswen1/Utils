package cn.edu.scu.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.jfree.util.Log;

//创建新文件和目录
public class FileUtil {
	// 验证字符串是否为正确路径名的正则表达式
	private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
	// 通过 sPath.matches(matches) 方法的返回值判断是否正确
	// sPath 为路径字符串
	private static boolean flag = false;
	private static File file;
	 /**  
     *  删除指定目录或文件(无论存在与否)  
     *  @param  deletePath String 目录或文件路径  如  c:/fqf 或 c:/fd.txt 
     *  @return  boolean  
     */  
	public static boolean deleteFolder(String deletePath) {
		flag = false;
		if (deletePath.matches(matches)) {
			file = new File(deletePath);
			if (!file.exists()) {// 判断目录或文件是否存在
				return flag; // 不存在返回 false
			} else {

				if (file.isFile()) {// 判断是否为文件
					return deleteFile(deletePath);// 为文件时调用删除文件方法
				} else {
					return deleteDirectory(deletePath);// 为目录时调用删除目录方法
				}
			}
		} else {
			System.out.println("要传入正确路径！");
			return false;
		}
	}
	/**  
     *  删除指定文件  
     *  @param  deletePath String 文件路径  如   c:/fd.txt 
     *  @return  boolean  
     */  
	public static boolean deleteFile(String filePath) {
		flag = false;
		file = new File(filePath);
		if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
			file.delete();// 文件删除
			flag = true;
		}
		return flag;
	}
	/**  
     *  删除目录（文件夹）以及目录下的文件 
     *  @param  deletePath String 目录路径  如  c:/fqf  
     *  @return  boolean  
     */  
	public static boolean deleteDirectory(String dirPath) {// 
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!dirPath.endsWith(File.separator)) {
			dirPath = dirPath + File.separator;
		}
		File dirFile = new File(dirPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		File[] files = dirFile.listFiles();// 获得传入路径下的所有文件
		for (int i = 0; i < files.length; i++) {// 循环遍历删除文件夹下的所有文件(包括子目录)
			if (files[i].isFile()) {// 删除子文件
				flag = deleteFile(files[i].getAbsolutePath());
				//System.out.println(files[i].getAbsolutePath() + " 删除成功");
				if (!flag)
					break;// 如果删除失败，则跳出
			} else {// 运用递归，删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;// 如果删除失败，则跳出
			}
		}
		if (!flag)
			return false;
		if (dirFile.delete()) {// 删除当前目录
			//System.out.println(dirFile.getAbsolutePath() + " 目录删除成功");
			return true;
		}else{
			//System.out.println(dirFile.getAbsolutePath() + " 目录删除失败");
			return false;
		}	
	}

	/**  
     *  删除目录下的文件 
     *  @param  deletePath String 目录路径  如  c:/fqf  
     *  @return  boolean  
     */  
	public static boolean deleteDirectory1(String dirPath) {// 
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!dirPath.endsWith(File.separator)) {
			dirPath = dirPath + File.separator;
		}
		File dirFile = new File(dirPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		File[] files = dirFile.listFiles();// 获得传入路径下的所有文件
		for (int i = 0; i < files.length; i++) {// 循环遍历删除文件夹下的所有文件(包括子目录)
			if (files[i].isFile()) {// 删除子文件
				flag = deleteFile(files[i].getAbsolutePath());
				//System.out.println(files[i].getAbsolutePath() + " 删除成功");
				if (!flag)
					break;// 如果删除失败，则跳出
			} else {// 运用递归，删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;// 如果删除失败，则跳出
			}
		}
		if (!flag)
			return false;
		return true;
	}
		
	/**
	 * 删除冗余文件，递归删除目录（及以下文件）或文件
	 */
	public static void deleteFiles(File file){
		if(file.exists()){
			if(file.isFile()){
				file.delete();
			}
			else if(file.isDirectory()){
				File files[]=file.listFiles();
				for(int i=0;i<files.length;i++)
					deleteFiles(files[i]);
				file.delete();
			}
		}else
			System.out.println(file+"文件不存在！");
	}
	/**  
     *  创建单个文件
     *  @param  deletePath String 文件路径  如  c:/fqf.txt  
     *  @return  boolean  
     */  
	public static boolean createFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {// 判断文件是否存在
			System.out.println("目标文件已存在" + filePath);
			return false;
		}
		if (filePath.endsWith(File.separator)) {// 判断文件是否为目录
			System.out.println("目标文件不能为目录！");
			return false;
		}
		if (!file.getParentFile().exists()) {// 判断目标文件所在的目录是否存在
			// 如果目标文件所在的文件夹不存在，则创建父文件夹
			System.out.println("目标文件所在目录不存在，准备创建它！");
			if (!file.getParentFile().mkdirs()) {// 判断创建目录是否成功
				System.out.println("创建目标文件所在的目录失败！");
				return false;
			}
		}
		try {
			if (file.createNewFile()) {// 创建目标文件
				System.out.println("创建文件成功:" + filePath);
				return true;
			} else {
				System.out.println("创建文件失败！");
				return false;
			}
		} catch (IOException e) {// 捕获异常
			e.printStackTrace();
			System.out.println("创建文件失败！" + e.getMessage());
			return false;
		}
	}
	 /**  
     *  新建文件  
     *  @param  filePathAndName  String  文件路径及名称  如c:/fqf.txt  
     *  @param  fileContent  String  文件内容  
     *  @return  void  
     */  
   public static void createFile(String  filePathAndName,  String  fileContent)  {  
 
       try  {  
           String  filePath  =  filePathAndName;  
           filePath  =  filePath.toString();  
           File  myFilePath  =  new  File(filePath);  
           if  (!myFilePath.exists())  {  
               myFilePath.createNewFile();  
           }  
           FileWriter  resultFile  =  new  FileWriter(myFilePath);  
           PrintWriter  myFile  =  new  PrintWriter(resultFile);  
           String  strContent  =  fileContent;  
           myFile.println(strContent);  
           resultFile.close();  
           System.out.println("新建文件成功"+filePathAndName);  
       }  
       catch  (Exception  e)  {  
           System.out.println("新建文件操作出错");  
           e.printStackTrace();  
 
       }  
 
   }  
  
	/**  
     *  创建目录（文件夹）
     *  @param  deletePath String 目录路径  如  c:/fqf  
     *  @return  boolean  
     */  
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {// 判断目录是否存在
			System.out.println("创建目录失败，目标目录已存在！");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
			destDirName = destDirName + File.separator;
		}
		if (dir.mkdirs()) {// 创建目标目录
			System.out.println("创建目录成功！" + destDirName);
			return true;
		} else {
			System.out.println("创建目录失败！");
			return false;
		}
	}
	/**  
	   *  复制单个文件  
	   *  @param  oldPath  String  原文件路径  如：c:/fqf.txt  
	   *  @param  newPath  String  复制后路径  如：f:/fqf.txt  
	   *  @return  boolean  
	   */  
	public static void copyFile(String  oldPath,  String  newPath)  {  
		try  {  
	          int  bytesum  =  0;  
	          int  byteread  =  0;  
	          File  oldfile  =  new  File(oldPath);  
	          if  (oldfile.exists())  {  //文件存在时  
	              InputStream  inStream  =  new  FileInputStream(oldPath);  //读入原文件  
	              FileOutputStream  fs  =  new  FileOutputStream(newPath);  
	              byte[]  buffer  =  new  byte[1444];  
	              int  length;  
	              while  (  (byteread  =  inStream.read(buffer))  !=  -1)  {  
	                  bytesum  +=  byteread;  //字节数  文件大小  
	                  //System.out.println("此次拷贝文件大小："+bytesum+"字节");  
	                  fs.write(buffer,  0,  byteread);  
	              }  
	              inStream.close(); 
	              fs.flush();
	              fs.close();
	          }  
	      }  
	      catch  (Exception  e)  {  
	          System.out.println("复制单个文件操作出错");  
	          e.printStackTrace();  
	      }  
	   }
	/**  
	   *  复制单个文件  
	   *  @param  oldPath  String  原文件路径  如：c:/fqf.txt  
	   *  @param  newPath  String  复制后路径  如：f:/fqf.txt  
	   *  @return  void  
	   */  
	private static void copyFile2(String source, String dest) {
		try {
		   File in = new File(source);
		   File out = new File(dest);
		   FileInputStream inFile = new FileInputStream(in);
		   FileOutputStream outFile = new FileOutputStream(out);
		   byte[] buffer = new byte[1024];
		   int i = 0;
		   while ((i = inFile.read(buffer)) != -1) {
			   outFile.write(buffer, 0, i);
		   }//end while
		   inFile.close();
		   outFile.flush();
		   outFile.close();
		   }//end try
		catch (Exception e) {
			System.out.println("复制单个文件操作出错");  
	          e.printStackTrace();
		}//end catch
	}//end copyFile
	/**  
	   *  复制单个文件 到指定的目录 
	   *  @param  oldFile  File  原文件  如：c:/fqf.txt  
	   *  @param  newFile  File  复制后文件  如：f:/fqf.txt  
	   *  @return  void  
	   */  
	public static void copyFile(File oldFile, String newFilePath) {
		try {
			File newFile = new File(newFilePath);
			if(newFile.exists()){
				newFile.delete();
				System.out.println("目标文件已存在!");
				System.out.println("目标文件已被删除!");
			}
			if (!newFile.getParentFile().exists()) { 
				newFile.getParentFile().mkdirs();
				
		           newFile.createNewFile();
		           Log.info("先创建父目录再创建新文件");
			}else{
				Log.info("直接创建新文件");
				newFile.createNewFile();
			}
				

			FileInputStream inFile = new FileInputStream(oldFile);
			FileOutputStream outFile = new FileOutputStream(newFile);
			byte[] buffer = new byte[1024];
			int i = 0;
			while ((i = inFile.read(buffer)) != -1) {
				outFile.write(buffer, 0, i);
			}//end while
			outFile.flush();
			inFile.close();
			outFile.close();
		}catch (Exception e) {
			System.out.println("复制文件时出错");  
	        e.printStackTrace();
		}}
	 /**  
	   *  复制整个文件夹内容  
	   *  @param  oldPath  String  原文件路径  如：c:/fqf  
	   *  @param  newPath  String  复制后路径  如：f:/fqf/ff  
	   *  @return  void  
	 */  
	public static void copyFolder(String  oldPath,  String  newPath)  {  
	      try  {  
	          (new  File(newPath)).mkdirs();  //如果文件夹不存在  则建立新文件夹  
	          File  a=new  File(oldPath);
	          if(!a.exists()){
	        	  Log.info("复制源文件不存在！");
	        	  return;
	          }
	          String[]  file=a.list();  
	          File  temp=null;  
	          for  (int  i  =  0;  i  <  file.length;  i++)  {  
	              if(oldPath.endsWith(File.separator)){  
	                  temp=new  File(oldPath+file[i]);  
	              }  
	              else{  
	                  temp=new  File(oldPath+File.separator+file[i]);  
	              }  

	              if(temp.isFile()){  
	                  FileInputStream  input  =  new  FileInputStream(temp);  
	                  FileOutputStream  output  =  new  FileOutputStream(newPath  +  "/"  +  
	                          (temp.getName()).toString());  
	                  byte[]  b  =  new  byte[1024  *  5];  
	                  int  len;  
	                  while  (  (len  =  input.read(b))  !=  -1)  {  
	                      output.write(b,  0,  len);  
	                  }  
	                  output.flush();  
	                  output.close();  
	                  input.close();  
	              }  
	              if(temp.isDirectory()){//如果是子文件夹  
	                  copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);  
	              }  
	          }
	          //System.out.println("复制整个文件夹内容成功，文件总数："+file.length);  
	      }  
	      catch  (Exception  e)  {  
	          System.out.println("复制整个文件夹内容操作出错");  
	          e.printStackTrace();  
	      }  
	   	} 
	/**  
     *  移动文件到指定目录  
     *  @param  oldPath  String  如：c:/fqf.txt  
     *  @param  newPath  String  如：d:/fqf.txt  
     */  
	public static void moveFile(String  oldPath,  String  newPath)  {  
       copyFile(oldPath,  newPath);  
       deleteFolder(oldPath);   
	}  
 
   /**  
     *  移动文件夹到指定目录  
     *  @param  oldPath  String  如：c:/fqf.txt  
     *  @param  newPath  String  如：d:/fqf.txt  
     */  
	public static void moveFolder(String  oldPath,  String  newPath)  {  
       copyFolder(oldPath,  newPath);  
       deleteFolder(oldPath);  
    }
	/**  
     *  创建临时文件
     *  @param  prex  String 文件名称前缀  如  temp2478577457.txt  
     *  @param  suffix  String 文件类型  如  .txt /.docx
     *  @return  string  
     */  
	public static String createTempFile(String prefix, String suffix,
			String dirName) {
		File tempFile = null;
		if (dirName == null) {// 目录如果为空
			try {
				tempFile = File.createTempFile(prefix, suffix);// 在默认文件夹下创建临时文件
				return tempFile.getCanonicalPath();// 返回临时文件的路径
			} catch (IOException e) {// 捕获异常
				e.printStackTrace();
				System.out.println("创建临时文件失败：" + e.getMessage());
				return null;
			}
		} else {
			// 指定目录存在
			File dir = new File(dirName);// 创建目录
			if (!dir.exists()) {
				// 如果目录不存在则创建目录
				if (FileUtil.createDir(dirName)) {
					System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
					return null;
				}
			}
			try {
				tempFile = File.createTempFile(prefix, suffix, dir);// 在指定目录下创建临时文件
				return tempFile.getCanonicalPath();// 返回临时文件的路径
			} catch (IOException e) {// 捕获异常
				e.printStackTrace();
				System.out.println("创建临时文件失败!" + e.getMessage());
				return null;
			}
		}
	}
	public static void main(String[] args) {
//		String dirName = "E:/createFile/";// 创建目录
//		FileUtil.createDir(dirName);// 调用方法创建目录
//		String fileName = dirName + "/file1.txt";// 创建文件
//		FileUtil.createFile(fileName);// 调用方法创建文件
//		String prefix = "temp";// 创建临时文件
//		String surfix = ".txt";// 后缀
//		for (int i = 0; i < 10; i++) {// 循环创建多个文件
//			System.out.println("创建临时文件: "// 调用方法创建临时文件
//					+ FileUtil.createTempFile(prefix, surfix,
//							dirName));
//		}
		//FileUtil.deleteDirectory("D:\\apache-tomcat-7.0.55\\webapps\\Foundation\\temp");
	//System.out.print("D:\\apache-tomcat-7.0.55\\webapps\\Foundation\\temp");
		//FileUtil.createFile("D:\\test1.doc","的啥都好说菲德就");
		File file=new File("d:\\测试文档.docx");
		String filename=(file.getName()).substring(0, file.getName().indexOf("."));
		System.out.println(filename);
		//FileUtil.copyFile(file, "f:\\1.doc");
		//FileUtil.copyFolder("d:\\unlocker207", "f:\\u");
	}
}