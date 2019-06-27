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
public class UtaolunAction {
	@Autowired
	private UtaolunService utaolunService;
	@Autowired
	private UserService userService;
	@Autowired
	private YonghuService yonghuService;

	/***上传导入开始***/
	private InputStream excelFile;

	public InputStream getExcelFile() {
		return excelFile;
	}
	/***上传导入结束***/

	@RequestMapping("/getUtaoluns")
	public void getUtaoluns(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String utaolunName = (String) request.getParameter("utaolunName");
		String utaolunId = (String) request.getParameter("utaolunId");
		String utaolunType = (String) request.getParameter("utaolunType");
		String utaolunType1 = (String) request.getParameter("utaolunType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		String yhbumenId = (String) request.getParameter("yhbumenId");
		String yonghuId = (String) request.getParameter("yonghuId");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Utaolun utaolun = new Utaolun();
		try {
			if (StringUtil.isNotEmpty(utaolunName)) {
				utaolun.setUtaolunName(utaolunName);
			}
			if (StringUtil.isNotEmpty(utaolunId)) {
				utaolun.setUtaolunId(Integer.parseInt(utaolunId));
			}
			if (StringUtil.isNotEmpty(utaolunType)) {
				utaolun.setUtaolunType(Integer.parseInt(utaolunType));
			}
			if (StringUtil.isNotEmpty(utaolunType1)) {
				utaolun.setUtaolunType1(Integer.parseInt(utaolunType1));
			}
			if (StringUtil.isNotEmpty(userId)) {
				utaolun.setUserId(Integer.parseInt(userId));
			}
			if (StringUtil.isNotEmpty(bumenId)) {
				utaolun.setBumenId(Integer.parseInt(bumenId));
			}
			if (StringUtil.isNotEmpty(yhbumenId)) {
				utaolun.setYhbumenId(Integer.parseInt(yhbumenId));
			}
			if (StringUtil.isNotEmpty(yonghuId)) {
				utaolun.setYonghuId(Integer.parseInt(yonghuId));
			}
			JSONArray jsonArray = JSONArray.fromObject(utaolunService.queryUtaoluns(
					utaolun, pageBean.getStart(), pageBean.getRows(), sdate, edate));
			JSONObject result = new JSONObject();
			int total = utaolunService.queryUtaoluns(utaolun, 0, 0, sdate, edate).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addUtaolun")
	public void addUtaolun(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String utaolunName = (String) request.getParameter("utaolunName");
		String utaolunMark = (String) request.getParameter("utaolunMark");
		String utaolunMark1 = (String) request.getParameter("utaolunMark1");
		String utaolunMark2 = (String) request.getParameter("utaolunMark2");
		String utaolunDate = (String) request.getParameter("utaolunDate");
		String utaolunDate1 = (String) request.getParameter("utaolunDate1");
		String utaolunType = (String) request.getParameter("utaolunType");
		String utaolunType1 = (String) request.getParameter("utaolunType1");
		String userId = (String) request.getParameter("userId");
		String yonghuId = (String) request.getParameter("yonghuId");
		String utaolunId = (String) request.getParameter("utaolunId");
		Utaolun utaolun = new Utaolun();

		if (StringUtil.isNotEmpty(utaolunId)) {
			utaolun = utaolunService.getUtaolun(Integer.parseInt(utaolunId));
		}
		if (StringUtil.isNotEmpty(utaolunName)) {
			utaolun.setUtaolunName(utaolunName);
		}
		if (StringUtil.isNotEmpty(utaolunMark)) {
			utaolun.setUtaolunMark(utaolunMark);
		}
		if (StringUtil.isNotEmpty(utaolunMark1)) {
			utaolun.setUtaolunMark1(utaolunMark1);
		}
		if (StringUtil.isNotEmpty(utaolunMark2)) {
			utaolun.setUtaolunMark2(utaolunMark2);
		}
		if (StringUtil.isNotEmpty(utaolunDate)) {
			utaolun.setUtaolunDate(DateUtil.formatString(utaolunDate,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(utaolunDate1)) {
			utaolun.setUtaolunDate1(DateUtil.formatString(utaolunDate1,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(utaolunType)) {
			utaolun.setUtaolunType(Integer.parseInt(utaolunType));
		}
		if (StringUtil.isNotEmpty(utaolunType1)) {
			utaolun.setUtaolunType1(Integer.parseInt(utaolunType1));
		}
		if (StringUtil.isNotEmpty(userId)) {
			utaolun.setUserId(Integer.parseInt(userId));
			User user = new User();
			user = userService.getUser(Integer.parseInt(userId));
			utaolun.setUserName(user.getUserName());
			utaolun.setBumenId(user.getBumenId());
			utaolun.setBumenName(user.getBumenName());
		}
		if (StringUtil.isNotEmpty(yonghuId)) {
			utaolun.setYonghuId(Integer.parseInt(yonghuId));
			Yonghu yonghu = new Yonghu();
			yonghu = yonghuService.getYonghu(Integer.parseInt(yonghuId));
			utaolun.setYonghuName(yonghu.getYonghuName());
			utaolun.setYhbumenId(yonghu.getYhbumenId());
			utaolun.setYhbumenName(yonghu.getYhbumenName());
		}
		try {
			if (StringUtil.isNotEmpty(utaolunId)) {
				utaolunService.modifyUtaolun(utaolun);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			} else {
				Date date = new Date();
				utaolun.setUtaolunDate(date);
				utaolun.setUtaolunType(0);
				utaolunService.save(utaolun);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteUtaolun")
	public void deleteUtaolun(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		String delIds = (String) request.getParameter("delIds");
		try {
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				utaolunService.deleteUtaolun(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/utaolunComboList")
	public void utaolunComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String utaolunId = (String) request.getParameter("utaolunId");
		String utaolunType = (String) request.getParameter("utaolunType");
		String utaolunType1 = (String) request.getParameter("utaolunType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		String yhbumenId = (String) request.getParameter("yhbumenId");
		String yonghuId = (String) request.getParameter("yonghuId");
		Utaolun utaolun = new Utaolun();
		if (StringUtil.isNotEmpty(utaolunId)) {
			utaolun.setUtaolunId(Integer.parseInt(utaolunId));
		}
		if (StringUtil.isNotEmpty(utaolunType)) {
			utaolun.setUtaolunType(Integer.parseInt(utaolunType));
		}
		if (StringUtil.isNotEmpty(utaolunType1)) {
			utaolun.setUtaolunType1(Integer.parseInt(utaolunType1));
		}
		if (StringUtil.isNotEmpty(userId)) {
			utaolun.setUserId(Integer.parseInt(userId));
		}
		if (StringUtil.isNotEmpty(bumenId)) {
			utaolun.setBumenId(Integer.parseInt(bumenId));
		}
		if (StringUtil.isNotEmpty(yonghuId)) {
			utaolun.setYonghuId(Integer.parseInt(yonghuId));
		}
		if (StringUtil.isNotEmpty(yhbumenId)) {
			utaolun.setYhbumenId(Integer.parseInt(yhbumenId));
		}
		if (StringUtil.isNotEmpty(yonghuId)) {
			utaolun.setYonghuId(Integer.parseInt(yonghuId));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("utaolunName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(utaolunService.queryUtaoluns(utaolun, 0, 0, null, null)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/utaolunTongji")
	public void utaolunTongji(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sdate=request.getParameter("sdate");
		String edate=request.getParameter("edate");
		List<Integer> utaolunTypes = new ArrayList<Integer>();
		utaolunTypes.add(0);
		utaolunTypes.add(1);
		utaolunTypes.add(2);
		utaolunTypes.add(3);
		List<String> uxtypeNames = new ArrayList<String>();
		uxtypeNames.add("0");
		uxtypeNames.add("1");
		uxtypeNames.add("2");
		uxtypeNames.add("3");
		List<Integer> utaolunZongshus = new ArrayList<Integer>();
		List<Utaolun> utaoluns = new ArrayList<Utaolun>();
		Utaolun utaolun = new Utaolun();
		Integer zongshu = 0;
		try {
			for(int i=0;i<utaolunTypes.size();i++){
				Integer utaolunZongshu = 0;
				utaolun.setUtaolunType(utaolunTypes.get(i));
				utaoluns = utaolunService.queryUtaoluns(utaolun, 0, 0, sdate, edate);
				for(int j=0;j<utaoluns.size();j++){
					utaolunZongshu = utaolunZongshu + utaoluns.size();
				}
				zongshu = zongshu + utaolunZongshu;
				utaolunZongshus.add(utaolunZongshu);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("uxtypeNames", uxtypeNames);
			session.setAttribute("utaolunZongshus", utaolunZongshus);
			session.setAttribute("zongshu", zongshu);
			response.sendRedirect("admin/utaoluntongji.jsp");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/shangchuanUtaolun")
	public void shangchuanUtaolun(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
			throws Exception {
		try {
			String utaolunId = (String) request.getParameter("utaolunId");
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
			Utaolun utaolun = utaolunService.getUtaolun(Integer.parseInt(utaolunId));
			utaolun.setUtaolunImg(shangchuandizhi);
			utaolun.setUtaolunImgName(shangchuanname);
			utaolunService.modifyUtaolun(utaolun);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daoruUtaolun")
	public void daoruUtaolun(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
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
				Utaolun utaolun = new Utaolun();
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
						utaolun.setUtaolunName(cellValue);
						break;
					case 2:
						utaolun.setUtaolunMark(cellValue);
						break;
					}
				}
				utaolunService.save(utaolun);
			}
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daochuUtaolun")
	public void daochuUtaolun(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String delIds = (String) request.getParameter("delIds");
		JSONObject result = new JSONObject();
		String str[] = delIds.split(",");

		// 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("utaoluns记录");
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
			Utaolun utaolun = utaolunService.getUtaolun(Integer.parseInt(str[i]));

			// 创建单元格，并设置值
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(utaolun.getUtaolunId());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(1);
			cell.setCellValue(utaolun.getUtaolunName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(7);
			cell.setCellValue(utaolun.getUtaolunMark());
			cell.setCellStyle(cellStyle);
		}

		// 保存Excel文件
		try {
			Date date = new Date();
			String strdate = DateUtil.formatDate(date, "yyyyMMddhhmmss");
			OutputStream outputStream = new FileOutputStream("D:/utaolun"
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
