package com.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.model.*;
import com.service.*;
import com.util.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
//导入导出

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

@Controller
public class YxinxiAction {
	@Autowired
	private YxinxiService yxinxiService;
	@Autowired
	private YxtypeService yxtypeService;
	@Autowired
	private YonghuService yonghuService;

	/***上传导入开始***/
	private InputStream excelFile;

	public InputStream getExcelFile() {
		return excelFile;
	}
	/***上传导入结束***/

	@RequestMapping("/getYxinxis")
	public void getYxinxis(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String yxinxiName = (String) request.getParameter("yxinxiName");
		String yxinxiId = (String) request.getParameter("yxinxiId");
		String yxtypeId = (String) request.getParameter("yxtypeId");
		String yxinxiType = (String) request.getParameter("yxinxiType");
		String yxinxiType1 = (String) request.getParameter("yxinxiType1");
		String yonghuId = (String) request.getParameter("yonghuId");
		String yhbumenId = (String) request.getParameter("yhbumenId");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Yxinxi yxinxi = new Yxinxi();
		try {
			if (StringUtil.isNotEmpty(yxinxiName)) {
				yxinxi.setYxinxiName(yxinxiName);
			}
			if (StringUtil.isNotEmpty(yxinxiId)) {
				yxinxi.setYxinxiId(Integer.parseInt(yxinxiId));
			}
			if (StringUtil.isNotEmpty(yxtypeId)) {
				yxinxi.setYxtypeId(Integer.parseInt(yxtypeId));
			}
			if (StringUtil.isNotEmpty(yxinxiType)) {
				yxinxi.setYxinxiType(Integer.parseInt(yxinxiType));
			}
			if (StringUtil.isNotEmpty(yxinxiType1)) {
				yxinxi.setYxinxiType1(Integer.parseInt(yxinxiType1));
			}
			if (StringUtil.isNotEmpty(yonghuId)) {
				yxinxi.setYonghuId(Integer.parseInt(yonghuId));
			}
			if (StringUtil.isNotEmpty(yhbumenId)) {
				yxinxi.setYhbumenId(Integer.parseInt(yhbumenId));
			}
			JSONArray jsonArray = JSONArray.fromObject(yxinxiService.queryYxinxis(
					yxinxi, pageBean.getStart(), pageBean.getRows(), sdate, edate));
			JSONObject result = new JSONObject();
			int total = yxinxiService.queryYxinxis(yxinxi, 0, 0, sdate, edate).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addYxinxi")
	public void addYxinxi(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String yxinxiName = (String) request.getParameter("yxinxiName");
		String yxinxiMark = (String) request.getParameter("yxinxiMark");
		String yxinxiMark1 = request.getParameter("yxinxiMark1");
		String yxinxiMark2 = request.getParameter("yxinxiMark2");
		String yxinxiDate = request.getParameter("yxinxiDate");
		String yxinxiType = request.getParameter("yxinxiType");
		String yxinxiType1 = request.getParameter("yxinxiType1");
		String yxtypeId = request.getParameter("yxtypeId");
		String yonghuId = request.getParameter("yonghuId");
		String yxinxiId = request.getParameter("yxinxiId");
		Yxinxi yxinxi = new Yxinxi();

		if (StringUtil.isNotEmpty(yxinxiId)) {
			yxinxi = yxinxiService.getYxinxi(Integer.parseInt(yxinxiId));
		}
		if (StringUtil.isNotEmpty(yxinxiName)) {
			yxinxi.setYxinxiName(yxinxiName);
		}
		if (StringUtil.isNotEmpty(yxinxiMark)) {
			yxinxi.setYxinxiMark(yxinxiMark);
		}
		if (StringUtil.isNotEmpty(yxinxiMark1)) {
			yxinxi.setYxinxiMark1(yxinxiMark1);
		}
		if (StringUtil.isNotEmpty(yxinxiMark2)) {
			yxinxi.setYxinxiMark2(yxinxiMark2);
		}
		if (StringUtil.isNotEmpty(yxinxiDate)) {
			yxinxi.setYxinxiDate(DateUtil.formatString(yxinxiDate,
					"yyyy-MM-dd HH:mm:ss"));
		}
		if (StringUtil.isNotEmpty(yxinxiType)) {
			yxinxi.setYxinxiType(Integer.parseInt(yxinxiType));
		}
		if (StringUtil.isNotEmpty(yxinxiType1)) {
			yxinxi.setYxinxiType1(Integer.parseInt(yxinxiType1));
		}
		if (StringUtil.isNotEmpty(yxtypeId)) {
			yxinxi.setYxtypeId(Integer.parseInt(yxtypeId));
			Yxtype yxtype = new Yxtype();
			yxtype = yxtypeService.getYxtype(Integer.parseInt(yxtypeId));
			yxinxi.setYxtypeName(yxtype.getYxtypeName());
		}
		if (StringUtil.isNotEmpty(yonghuId)) {
			yxinxi.setYonghuId(Integer.parseInt(yonghuId));
			Yonghu yonghu = new Yonghu();
			yonghu = yonghuService.getYonghu(Integer.parseInt(yonghuId));
			yxinxi.setYonghuName(yonghu.getYonghuName());
			yxinxi.setYhbumenId(yonghu.getYhbumenId());
			yxinxi.setYhbumenName(yonghu.getYhbumenName());
		}
		try {
			if (StringUtil.isNotEmpty(yxinxiId)) {
				yxinxiService.modifyYxinxi(yxinxi);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			} else {
				Date date = new Date();
				yxinxi.setYxinxiDate(date);
				yxinxi.setYxinxiType(0);
				yxinxiService.save(yxinxi);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteYxinxi")
	public void deleteYxinxi(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		String delIds = (String) request.getParameter("delIds");
		try {
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				yxinxiService.deleteYxinxi(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/yxinxiComboList")
	public void yxinxiComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String yxtypeId = (String) request.getParameter("yxtypeId");
		String yxinxiType = request.getParameter("yxinxiType");
		String yxinxiType1 = request.getParameter("yxinxiType1");
		String yonghuId = request.getParameter("yonghuId");
		String yhbumenId = request.getParameter("yhbumenId");
		Yxinxi yxinxi = new Yxinxi();
		if (StringUtil.isNotEmpty(yxtypeId)) {
			yxinxi.setYxtypeId(Integer.parseInt(yxtypeId));
		}
		if (StringUtil.isNotEmpty(yxinxiType)) {
			yxinxi.setYxinxiType(Integer.parseInt(yxinxiType));
		}
		if (StringUtil.isNotEmpty(yxinxiType1)) {
			yxinxi.setYxinxiType1(Integer.parseInt(yxinxiType1));
		}
		if (StringUtil.isNotEmpty(yonghuId)) {
			yxinxi.setYonghuId(Integer.parseInt(yonghuId));
		}
		if (StringUtil.isNotEmpty(yhbumenId)) {
			yxinxi.setYhbumenId(Integer.parseInt(yhbumenId));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("yxinxiName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(yxinxiService.queryYxinxis(yxinxi, 0, 0, null, null)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/yxinxiTongji")
	public void yxinxiTongji(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sdate=request.getParameter("sdate");
		String edate=request.getParameter("edate");
		List<Integer> yxtypeIds = new ArrayList<Integer>();
		List<String> yxtypeNames = new ArrayList<String>();
		List<Integer> yxinxiZongshus = new ArrayList<Integer>();
		List<Yxtype> yxtypes = new ArrayList<Yxtype>();
		List<Yxinxi> yxinxis = new ArrayList<Yxinxi>();
		Yxinxi yxinxi = new Yxinxi();
		Integer zongshu = 0;
		try {
			yxtypes = yxtypeService.queryYxtypes(null, 0,0);
			for(int i=0;i<yxtypes.size();i++){
				yxtypeIds.add(yxtypes.get(i).getYxtypeId());
				yxtypeNames.add(yxtypes.get(i).getYxtypeName());
			}
			for(int i=0;i<yxtypeIds.size();i++){
				Integer yxinxiZongshu = 0;
				yxinxi.setYxtypeId(yxtypeIds.get(i));
				yxinxis = yxinxiService.queryYxinxis(yxinxi, 0, 0, sdate, edate);
				for(int j=0;j<yxinxis.size();j++){
					yxinxiZongshu = yxinxiZongshu + yxinxis.size();
				}
				zongshu = zongshu + yxinxiZongshu;
				yxinxiZongshus.add(yxinxiZongshu);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("yxtypeNames", yxtypeNames);
			session.setAttribute("yxinxiZongshus", yxinxiZongshus);
			session.setAttribute("zongshu", zongshu);
			response.sendRedirect("admin/yxinxitongji.jsp");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/shangchuanYxinxi")
	public void shangchuanYxinxi(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
			throws Exception {
		try {
			String yxinxiId = (String) request.getParameter("yxinxiId");
			String directory = "/file";
			String targetDirectory = request.getSession().getServletContext().getRealPath(directory);
	        String fileName = uploadFile.getOriginalFilename();  
			File dir = new File(targetDirectory,fileName);        
	        if(!dir.exists()){
	            dir.mkdirs();
	        }
	        //MultipartFile自带的解析方法
	        uploadFile.transferTo(dir);

			String shangchuandizhi = "/file" + "/" + fileName;
			String shangchuanname = fileName;
			Yxinxi yxinxi = yxinxiService.getYxinxi(Integer.parseInt(yxinxiId));
			yxinxi.setYxinxiImg(shangchuandizhi);
			yxinxi.setYxinxiImgName(shangchuanname);
			yxinxiService.modifyYxinxi(yxinxi);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/xiazaiYxinxi")
	public void xiazaiYxinxi(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String filename = (String) request.getParameter("filename");
        //模拟文件，myfile.txt为需要下载的文件  
        String path = request.getSession().getServletContext().getRealPath("file")+"\\"+filename;  
        //获取输入流  
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        //转码，免得文件名中文乱码  
        filename = URLEncoder.encode(filename,"UTF-8");  
        //设置文件下载头  
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型    
        response.setContentType("multipart/form-data");   
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
        int len = 0;  
        while((len = bis.read()) != -1){  
            out.write(len);  
            out.flush();  
        }  
        out.close();
	}

	@RequestMapping("/daoruYxinxi")
	public void daoruYxinxi(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
			throws Exception {
		try {
			String directory = "/file";
			String targetDirectory = request.getSession().getServletContext().getRealPath(directory);
	        String fileName = uploadFile.getOriginalFilename();  
			File dir = new File(targetDirectory,fileName);        
	        if(!dir.exists()){
	            dir.mkdirs();
	        }
	        //MultipartFile自带的解析方法
	        uploadFile.transferTo(dir);
			excelFile = new FileInputStream(dir);
			Workbook wb = new HSSFWorkbook(excelFile);
			Sheet sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum() + 1;
			for (int i = 1; i < rowNum; i++) {
				Yxinxi yxinxi = new Yxinxi();
				Row row = sheet.getRow(i);
				int cellNum = row.getLastCellNum();
				for (int j = 0; j < cellNum; j++) {
					Cell cell = row.getCell(j);
					String cellValue = null;
					switch (cell.getCellType()) { // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
					case 0:
						cellValue = String.valueOf((int) cell
								.getNumericCellValue());
						break;
					case 1:
						cellValue = cell.getStringCellValue();
						break;
					case 2:
						cellValue = cell.getStringCellValue();
						break;
					}

					switch (j) {// 通过列数来判断对应插如的字段
					case 1:
						yxinxi.setYxinxiName(cellValue);
						break;
					case 2:
						yxinxi.setYxinxiMark(cellValue);
						break;
					}
				}
				yxinxiService.save(yxinxi);
			}
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daochuYxinxi")
	public void daochuYxinxi(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String delIds = (String) request.getParameter("delIds");
		JSONObject result = new JSONObject();
		String str[] = delIds.split(",");

		// 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("yxinxis记录");
		// 添加表头行
		HSSFRow hssfRow = sheet.createRow(0);
		// 设置单元格格式居中
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 添加表头内容
		HSSFCell headCell = hssfRow.createCell(0);
		headCell.setCellValue("编号");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(1);
		headCell.setCellValue("用户名");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(2);
		headCell.setCellValue("密码");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(3);
		headCell.setCellValue("姓名");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(4);
		headCell.setCellValue("性别");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(5);
		headCell.setCellValue("年龄");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(6);
		headCell.setCellValue("电话");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(7);
		headCell.setCellValue("备注1");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(8);
		headCell.setCellValue("备注2");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(9);
		headCell.setCellValue("备注3");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(10);
		headCell.setCellValue("备注4");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(13);
		headCell.setCellValue("标志1");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(14);
		headCell.setCellValue("备注2");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(15);
		headCell.setCellValue("职位");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(16);
		headCell.setCellValue("科室");
		headCell.setCellStyle(cellStyle);

		// 添加数据内容
		for (int i = 0; i < str.length; i++) {
			hssfRow = sheet.createRow((int) i + 1);
			Yxinxi yxinxi = yxinxiService.getYxinxi(Integer.parseInt(str[i]));

			// 创建单元格，并设置值
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(yxinxi.getYxinxiId());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(1);
			cell.setCellValue(yxinxi.getYxinxiName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(7);
			cell.setCellValue(yxinxi.getYxinxiMark());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(16);
			cell.setCellValue(yxinxi.getYxtypeName());
			cell.setCellStyle(cellStyle);
		}

		// 保存Excel文件
		try {
			Date date = new Date();
			String strdate = DateUtil.formatDate(date, "yyyyMMddhhmmss");
			OutputStream outputStream = new FileOutputStream("D:/yxinxi"
					+ strdate + ".xls");
			workbook.write(outputStream);
			outputStream.close();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
