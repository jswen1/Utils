package cn.edu.scu.util.excelReader;

import g4studio.base.common.Dto;
import g4studio.util.Utils;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * Excel工具类
 * 
 * @author jiangshengwen
 * @since 2016-04-04
 */
public class ExcelUtils {
	private static Log log = LogFactory.getLog(ExcelUtils.class);
	/**
	  * 导出EXCEL
	  * @param qDtoList 数据集合
	  * @param filePath excel表格存放路径   
	  * @param columnNames excel表格第一行标题名称数组
	  * @param dataNames excel表格数据在map中映射名称,该数组的值必须在dto的键值对中存在,并且该数组的长度必须与columnNames相同
	  */
	public static boolean exportToExcel(List<Dto> qDtoList,String filePath,String[] columnNames,String[] dataNames)
			throws Exception{
		 if(columnNames.length != dataNames.length){
			 log.info("excel表格列数与数据集列名称个数不一致！");
			 return false;
		 }
		 int columnCount = columnNames.length;
		 // 建立工作薄并写表头
		 try {
			 WritableWorkbook wwb = Workbook.createWorkbook(new File(filePath));
			 WritableSheet ws = wwb.createSheet("Sheet1", 0);// 建立工作簿	
			 // 写表头
			 jxl.write.Label label;
			 for(int i=0;i<columnCount;i++){
				 label = new jxl.write.Label(i,0,columnNames[i].toString());
				 ws.addCell(label);   // 放入工作簿
			 }		   
		   // 写入信息
		   for (int i = 0; i < qDtoList.size(); i++) {
			   for(int j=0;j < columnCount;j++){
				   if(Utils.isNotEmpty(qDtoList.get(i).get(dataNames[j]))){
					   label = new jxl.write.Label(j, i + 1, qDtoList.get(i).get(
							   dataNames[j]).toString().trim());// 新建一个单元格,j为列数,i+1为行数
					   ws.addCell(label);    // 放入工作簿
					  // log.info("第"+(i+2)+"行第"+(j+1)+"列为："+qDtoList.get(i).get(
							 //  dataNames[j]).toString().trim());
				   }
			   }
		   }		   
		   wwb.write();    // 写入Excel工作表		  
		   wwb.close();    // 关闭Excel工作薄对象
		  } catch (Exception e) {
			  e.printStackTrace();
			  return false;
		  }
		 return true;
	 }
}
