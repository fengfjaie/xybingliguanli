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
public class YonghuAction {
	@Autowired
	private YonghuService yonghuService;
	@Autowired
	private YhbumenService yhbumenService;
	@Autowired
	private YhroleService yhroleService;
	@Autowired
	private UserService userService;

	/***上传导入开始***/
	private InputStream excelFile;

	public InputStream getExcelFile() {
		return excelFile;
	}
	/***上传导入结束***/

	@RequestMapping("/getYonghus")
	public void getYonghus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String yonghuName = (String) request.getParameter("yonghuName");
		String yonghuXingming = (String) request.getParameter("yonghuXingming");
		String yonghuId = (String) request.getParameter("yonghuId");
		String yonghuType1 = (String) request.getParameter("yonghuType1");
		String yonghuType2 = (String) request.getParameter("yonghuType2");
		String yhroleId = (String) request.getParameter("yhroleId");
		String yonghuSex = (String) request.getParameter("yonghuSex");
		String yhbumenId = (String) request.getParameter("yhbumenId");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Yonghu yonghu = new Yonghu();
		try {

			if (StringUtil.isNotEmpty(yonghuXingming)) {
				yonghu.setYonghuXingming(yonghuXingming);
			}
			if (StringUtil.isNotEmpty(yonghuName)) {
				yonghu.setYonghuName(yonghuName);
			}
			if (StringUtil.isNotEmpty(yonghuId)) {
				yonghu.setYonghuId(Integer.parseInt(yonghuId));
			}
			if (StringUtil.isNotEmpty(yonghuType1)) {
				yonghu.setYonghuType1(Integer.parseInt(yonghuType1));
			}
			if (StringUtil.isNotEmpty(yonghuType2)) {
				yonghu.setYonghuType2(Integer.parseInt(yonghuType2));
			}
			if (StringUtil.isNotEmpty(yhroleId)) {
				yonghu.setYhroleId(Integer.parseInt(yhroleId));
			}
			if (StringUtil.isNotEmpty(yonghuSex)) {
				yonghu.setYonghuSex(Integer.parseInt(yonghuSex));
			}
			if (StringUtil.isNotEmpty(yhbumenId)) {
				yonghu.setYhbumenId(Integer.parseInt(yhbumenId));
			}
			JSONArray jsonArray = JSONArray.fromObject(yonghuService.queryYonghus(
					yonghu, null, pageBean.getStart(), pageBean.getRows(), sdate, edate));
			JSONObject result = new JSONObject();
			int total = yonghuService.queryYonghus(yonghu, null, 0, 0, sdate, edate).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			System.out.println(result);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addYonghu")
	public void addYonghu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String yonghuName = (String) request.getParameter("yonghuName");
		String yonghuPassword = (String) request.getParameter("yonghuPassword");
		String yonghuAge = (String) request.getParameter("yonghuAge");
		String yonghuXingming = (String) request.getParameter("yonghuXingming");
		String yonghuSex = (String) request.getParameter("yonghuSex");
		String yonghuPhone = (String) request.getParameter("yonghuPhone");
		String yonghuMark1 = (String) request.getParameter("yonghuMark1");
		String yonghuMark2 = (String) request.getParameter("yonghuMark2");
		String yonghuMark3 = (String) request.getParameter("yonghuMark3");
		String yonghuMark4 = (String) request.getParameter("yonghuMark4");
		String yonghuDate1 = (String) request.getParameter("yonghuDate1");
		String yonghuDate2 = (String) request.getParameter("yonghuDate2");
		String yonghuType1 = (String) request.getParameter("yonghuType1");
		String yonghuType2 = (String) request.getParameter("yonghuType2");
		String yhroleId = (String) request.getParameter("yhroleId");
		String yhbumenId = (String) request.getParameter("yhbumenId");
		String yonghuId = (String) request.getParameter("yonghuId");
		Yonghu yonghu = new Yonghu();

		if (StringUtil.isNotEmpty(yonghuId)) {
			yonghu = yonghuService.getYonghu(Integer.parseInt(yonghuId));
		}
		if (StringUtil.isNotEmpty(yonghuName)) {
			yonghu.setYonghuName(yonghuName);
		}
		if (StringUtil.isNotEmpty(yonghuPassword)) {
			yonghu.setYonghuPassword(yonghuPassword);
		}
		if (StringUtil.isNotEmpty(yonghuAge)) {
			yonghu.setYonghuAge(Integer.parseInt(yonghuAge));
		}
		if (StringUtil.isNotEmpty(yonghuXingming)) {
			yonghu.setYonghuXingming(yonghuXingming);
		}
		if (StringUtil.isNotEmpty(yonghuSex)) {
			yonghu.setYonghuSex(Integer.parseInt(yonghuSex));
		}
		if (StringUtil.isNotEmpty(yonghuPhone)) {
			yonghu.setYonghuPhone(yonghuPhone);
		}
		if (StringUtil.isNotEmpty(yonghuMark1)) {
			yonghu.setYonghuMark1(yonghuMark1);
		}
		if (StringUtil.isNotEmpty(yonghuMark2)) {
			yonghu.setYonghuMark2(yonghuMark2);
		}
		if (StringUtil.isNotEmpty(yonghuMark3)) {
			yonghu.setYonghuMark3(yonghuMark3);
		}
		if (StringUtil.isNotEmpty(yonghuMark4)) {
			yonghu.setYonghuMark4(yonghuMark4);
		}
		if (StringUtil.isNotEmpty(yonghuDate1)) {
			yonghu.setYonghuDate1(DateUtil.formatString(yonghuDate1,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(yonghuDate2)) {
			yonghu.setYonghuDate2(DateUtil.formatString(yonghuDate2,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(yonghuType1)) {
			yonghu.setYonghuType1(Integer.parseInt(yonghuType1));
		}
		if (StringUtil.isNotEmpty(yonghuType2)) {
			yonghu.setYonghuType2(Integer.parseInt(yonghuType2));
		}
		if (StringUtil.isNotEmpty(yhroleId)) {
			yonghu.setYhroleId(Integer.parseInt(yhroleId));
			Yhrole yhrole = new Yhrole();
			yhrole = yhroleService.getYhrole(Integer.parseInt(yhroleId));
			yonghu.setYhroleName(yhrole.getYhroleName());
		}
		if (StringUtil.isNotEmpty(yhbumenId)) {
			yonghu.setYhbumenId(Integer.parseInt(yhbumenId));
			User user = new User();
			user = userService.getUser(Integer.parseInt(yhbumenId));
			yonghu.setYhbumenName(user.getUserXingming());
		}
		try {
			if (StringUtil.isNotEmpty(yonghuId)) {
				yonghuService.modifyYonghu(yonghu);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			} else {
				int total = yonghuService.queryYonghus(null, yonghuName, 0, 0, null, null).size();
				if (total==0) {
					Date date = new Date();
					yonghu.setYonghuDate1(date);
					yonghu.setYonghuType1(0);
					yonghuService.save(yonghu);
					result.put("success", "true");
					ResponseUtil.write(response, result);
				} else {
					result.put("success", "true");
					result.put("errorMsg", "用户名重复！");
					ResponseUtil.write(response, result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/mimaYonghu")
	public void mimaYonghu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String yonghuId = (String) request.getParameter("yonghuId");
		String yonghuPassword = (String) request.getParameter("yonghuPassword");
		String yonghuPassword1 = (String) request.getParameter("yonghuPassword1");
		Yonghu yonghu = new Yonghu();
		try {
			yonghu = yonghuService.getYonghu(Integer.parseInt(yonghuId));
			if (!yonghu.getYonghuPassword().equals(yonghuPassword)) {
				request.setAttribute("error", "原密码错误，请重新输入！");
				request.getRequestDispatcher("yonghumima.jsp").forward(request,
						response);
			}else{
				yonghu.setYonghuPassword(yonghuPassword1);
				yonghuService.modifyYonghu(yonghu);
				request.setAttribute("error", "密码修改成功！");
				request.getRequestDispatcher("yonghumima.jsp").forward(request,
						response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/zhuceYonghu")
	public void zhuceYonghu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String yonghuName = (String) request.getParameter("yonghuName");
		String yonghuPassword = (String) request.getParameter("yonghuPassword");
		String yonghuAge = (String) request.getParameter("yonghuAge");
		String yonghuXingming = (String) request.getParameter("yonghuXingming");
		String yonghuSex = (String) request.getParameter("yonghuSex");
		String yonghuPhone = (String) request.getParameter("yonghuPhone");
		String yonghuMark1 = (String) request.getParameter("yonghuMark1");
		String yonghuMark2 = (String) request.getParameter("yonghuMark2");
		String yonghuMark3 = (String) request.getParameter("yonghuMark3");
		String yonghuMark4 = (String) request.getParameter("yonghuMark4");
		String yonghuDate1 = (String) request.getParameter("yonghuDate1");
		String yonghuDate2 = (String) request.getParameter("yonghuDate2");
		String yonghuType1 = (String) request.getParameter("yonghuType1");
		String yonghuType2 = (String) request.getParameter("yonghuType2");
		String yhroleId = (String) request.getParameter("yhroleId");
		String yhbumenId = (String) request.getParameter("yhbumenId");
		String yonghuId = (String) request.getParameter("yonghuId");
		Yonghu yonghu = new Yonghu();

		if (StringUtil.isNotEmpty(yonghuId)) {
			yonghu = yonghuService.getYonghu(Integer.parseInt(yonghuId));
		}
		if (StringUtil.isNotEmpty(yonghuName)) {
			yonghu.setYonghuName(yonghuName);
		}
		if (StringUtil.isNotEmpty(yonghuPassword)) {
			yonghu.setYonghuPassword(yonghuPassword);
		}
		if (StringUtil.isNotEmpty(yonghuAge)) {
			yonghu.setYonghuAge(Integer.parseInt(yonghuAge));
		}
		if (StringUtil.isNotEmpty(yonghuXingming)) {
			yonghu.setYonghuXingming(yonghuXingming);
		}
		if (StringUtil.isNotEmpty(yonghuSex)) {
			yonghu.setYonghuSex(Integer.parseInt(yonghuSex));
		}
		if (StringUtil.isNotEmpty(yonghuPhone)) {
			yonghu.setYonghuPhone(yonghuPhone);
		}
		if (StringUtil.isNotEmpty(yonghuMark1)) {
			yonghu.setYonghuMark1(yonghuMark1);
		}
		if (StringUtil.isNotEmpty(yonghuMark2)) {
			yonghu.setYonghuMark2(yonghuMark2);
		}
		if (StringUtil.isNotEmpty(yonghuMark3)) {
			yonghu.setYonghuMark3(yonghuMark3);
		}
		if (StringUtil.isNotEmpty(yonghuMark4)) {
			yonghu.setYonghuMark4(yonghuMark4);
		}
		if (StringUtil.isNotEmpty(yonghuDate1)) {
			yonghu.setYonghuDate1(DateUtil.formatString(yonghuDate1,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(yonghuDate2)) {
			yonghu.setYonghuDate2(DateUtil.formatString(yonghuDate2,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(yonghuType1)) {
			yonghu.setYonghuType1(Integer.parseInt(yonghuType1));
		}
		if (StringUtil.isNotEmpty(yonghuType2)) {
			yonghu.setYonghuType2(Integer.parseInt(yonghuType2));
		}
		if (StringUtil.isNotEmpty(yhroleId)) {
			yonghu.setYhroleId(Integer.parseInt(yhroleId));
			Yhrole yhrole = new Yhrole();
			yhrole = yhroleService.getYhrole(Integer.parseInt(yhroleId));
			yonghu.setYhroleName(yhrole.getYhroleName());
		}
		if (StringUtil.isNotEmpty(yhbumenId)) {
			yonghu.setYhbumenId(Integer.parseInt(yhbumenId));
			Yhbumen yhbumen = new Yhbumen();
			yhbumen = yhbumenService.getYhbumen(Integer.parseInt(yhbumenId));
			yonghu.setYhbumenName(yhbumen.getYhbumenName());
		}
		try {
			if (StringUtil.isNotEmpty(yonghuId)) {
				yonghuService.modifyYonghu(yonghu);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			} else {
				int total = yonghuService.queryYonghus(null, yonghuName, 0, 0, null, null).size();
				if (total==0) {
					yonghuService.save(yonghu);
					request.setAttribute("error", "注册成功，请登录！");
					request.getRequestDispatcher("index.jsp").forward(request,
							response);
				} else {
					request.setAttribute("error", "用户名重复，请重新输入！");
					request.getRequestDispatcher("zhuceyonghu.jsp").forward(request,
							response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteYonghu")
	public void deleteYonghu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		String delIds = (String) request.getParameter("delIds");
		try {
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				yonghuService.deleteYonghu(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/yonghuComboList")
	public void yonghuComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String yonghuSex = (String) request.getParameter("yonghuSex");
		String yonghuType1 = (String) request.getParameter("yonghuType1");
		String yonghuType2 = (String) request.getParameter("yonghuType2");
		String yhroleId = (String) request.getParameter("yhroleId");
		String yhbumenId = (String) request.getParameter("yhbumenId");
		Yonghu yonghu = new Yonghu();

		if (StringUtil.isNotEmpty(yonghuSex)) {
			yonghu.setYonghuSex(Integer.parseInt(yonghuSex));
		}
		if (StringUtil.isNotEmpty(yonghuType1)) {
			yonghu.setYonghuType1(Integer.parseInt(yonghuType1));
		}
		if (StringUtil.isNotEmpty(yonghuType2)) {
			yonghu.setYonghuType2(Integer.parseInt(yonghuType2));
		}
		if (StringUtil.isNotEmpty(yhroleId)) {
			yonghu.setYhroleId(Integer.parseInt(yhroleId));
		}
		if (StringUtil.isNotEmpty(yhbumenId)) {
			yonghu.setYhbumenId(Integer.parseInt(yhbumenId));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("yonghuName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(yonghuService.queryYonghus(yonghu, null, 0, 0, null, null)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/yonghuTongji")
	public void yonghuTongji(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sdate=request.getParameter("sdate");
		String edate=request.getParameter("edate");
		List<Integer> yonghuTypeIds = new ArrayList<Integer>();
		yonghuTypeIds.add(0);
		yonghuTypeIds.add(1);
		List<String> yonghuTypeNames = new ArrayList<String>();
		yonghuTypeNames.add("住院");
		yonghuTypeNames.add("出院");
		List<Integer> yonghuZongshus = new ArrayList<Integer>();
		List<Yonghu> yonghus = new ArrayList<Yonghu>();
		Yonghu yonghu = new Yonghu();
		Integer zongshu = 0;
		try {
			for(int i=0;i<yonghuTypeIds.size();i++){
				Integer yonghuZongshu = 0;
				yonghu.setYonghuType1(yonghuTypeIds.get(i));
				yonghus = yonghuService.queryYonghus(yonghu, null, 0, 0, sdate, edate);
				yonghuZongshu = yonghuZongshu + yonghus.size();
				zongshu = zongshu + yonghuZongshu;
				yonghuZongshus.add(yonghuZongshu);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("yonghuTypeNames", yonghuTypeNames);
			session.setAttribute("yonghuZongshus", yonghuZongshus);
			session.setAttribute("zongshu", zongshu);
			response.sendRedirect("admin/yonghutongji.jsp");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/shangchuanYonghu")
	public void shangchuanYonghu(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
			throws Exception {
		try {
			String yonghuId = (String) request.getParameter("yonghuId");
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
			Yonghu yonghu = yonghuService.getYonghu(Integer.parseInt(yonghuId));
			yonghu.setYonghuImg(shangchuandizhi);
			yonghu.setYonghuImgName(shangchuanname);
			yonghuService.modifyYonghu(yonghu);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daoruYonghu")
	public void daoruYonghu(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
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
				Yonghu yonghu = new Yonghu();
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
					case 3:
						cellValue = cell.getStringCellValue();
						break;
					case 4:
						cellValue = cell.getStringCellValue();
						break;
					case 5:
						cellValue = cell.getStringCellValue();
						break;
					case 6:
						cellValue = cell.getStringCellValue();
						break;
					case 7:
						cellValue = cell.getStringCellValue();
						break;
					case 8:
						cellValue = cell.getStringCellValue();
						break;
					case 9:
						cellValue = cell.getStringCellValue();
						break;
					case 10:
						cellValue = cell.getStringCellValue();
						break;
					case 11:
						cellValue = cell.getStringCellValue();
						break;
					case 12:
						cellValue = cell.getStringCellValue();
						break;
					case 13:
						cellValue = cell.getStringCellValue();
						break;
					case 14:
						cellValue = cell.getStringCellValue();
						break;
					}

					switch (j) {// 通过列数来判断对应插如的字段
					case 1:
						yonghu.setYonghuName(cellValue);
						break;
					case 2:
						yonghu.setYonghuPassword(cellValue);
						break;
					case 3:
						yonghu.setYonghuXingming(cellValue);
						break;
					case 4:
						yonghu.setYonghuAge(Integer.parseInt(cellValue));
						break;
					case 5:
						yonghu.setYonghuSex(Integer.parseInt(cellValue));
						break;
					case 6:
						yonghu.setYonghuPhone(cellValue);
						break;
					case 7:
						yonghu.setYonghuMark1(cellValue);
						break;
					case 8:
						yonghu.setYonghuMark2(cellValue);
						break;
					case 9:
						yonghu.setYonghuMark3(cellValue);
						break;
					case 10:
						yonghu.setYonghuMark4(cellValue);
						break;
					case 11:
						yonghu.setYonghuType1(Integer.parseInt(cellValue));
						break;
					case 12:
						yonghu.setYonghuType2(Integer.parseInt(cellValue));
						break;
					}
				}
				yonghuService.save(yonghu);
			}
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daochuYonghu")
	public void daochuYonghu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String delIds = (String) request.getParameter("delIds");
		JSONObject result = new JSONObject();
		String str[] = delIds.split(",");

		// 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("yonghus记录");
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
			Yonghu yonghu = yonghuService.getYonghu(Integer.parseInt(str[i]));

			// 创建单元格，并设置值
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(yonghu.getYonghuId());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(1);
			cell.setCellValue(yonghu.getYonghuName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(2);
			cell.setCellValue(yonghu.getYonghuPassword());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(3);
			cell.setCellValue(yonghu.getYonghuXingming());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(4);
			if (yonghu.getYonghuSex().intValue() == 0) {
				cell.setCellValue("男");
				cell.setCellStyle(cellStyle);
			} else {
				cell.setCellValue("女");
				cell.setCellStyle(cellStyle);
			}

			cell = hssfRow.createCell(5);
			cell.setCellValue(yonghu.getYonghuAge());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(6);
			cell.setCellValue(yonghu.getYonghuPhone());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(7);
			cell.setCellValue(yonghu.getYonghuMark1());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(8);
			cell.setCellValue(yonghu.getYonghuMark2());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(9);
			cell.setCellValue(yonghu.getYonghuMark3());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(10);
			cell.setCellValue(yonghu.getYonghuMark4());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(13);
			cell.setCellValue(yonghu.getYonghuType1());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(14);
			cell.setCellValue(yonghu.getYonghuType2());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(15);
			cell.setCellValue(yonghu.getYhroleName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(16);
			cell.setCellValue(yonghu.getYhbumenName());
			cell.setCellStyle(cellStyle);
		}

		// 保存Excel文件
		try {
			Date date = new Date();
			String strdate = DateUtil.formatDate(date, "yyyyMMddhhmmss");
			OutputStream outputStream = new FileOutputStream("D:/yonghu"
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
