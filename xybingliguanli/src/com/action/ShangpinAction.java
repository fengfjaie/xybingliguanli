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
public class ShangpinAction {
	@Autowired
	private ShangpinService shangpinService;
	@Autowired
	private SptypeService sptypeService;
	@Autowired
	private UserService userService;

	/***上传导入开始***/
	private InputStream excelFile;

	public InputStream getExcelFile() {
		return excelFile;
	}
	/***上传导入结束***/

	@RequestMapping("/getShangpins")
	public void getShangpins(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String shangpinName = (String) request.getParameter("shangpinName");
		String shangpinId = (String) request.getParameter("shangpinId");
		String sptypeId = (String) request.getParameter("sptypeId");
		String shangpinType = (String) request.getParameter("shangpinType");
		String shangpinType1 = (String) request.getParameter("shangpinType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		String roleId = (String) request.getParameter("roleId");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Shangpin shangpin = new Shangpin();
		try {
			if (StringUtil.isNotEmpty(shangpinName)) {
				shangpin.setShangpinName(shangpinName);
			}
			if (StringUtil.isNotEmpty(shangpinId)) {
				shangpin.setShangpinId(Integer.parseInt(shangpinId));
			}
			if (StringUtil.isNotEmpty(sptypeId)) {
				shangpin.setSptypeId(Integer.parseInt(sptypeId));
			}
			if (StringUtil.isNotEmpty(shangpinType)) {
				shangpin.setShangpinType(Integer.parseInt(shangpinType));
			}
			if (StringUtil.isNotEmpty(shangpinType1)) {
				shangpin.setShangpinType1(Integer.parseInt(shangpinType1));
			}
			if (StringUtil.isNotEmpty(userId)) {
				shangpin.setUserId(Integer.parseInt(userId));
			}
			if (StringUtil.isNotEmpty(bumenId)) {
				shangpin.setBumenId(Integer.parseInt(bumenId));
			}
			if (StringUtil.isNotEmpty(roleId)) {
				shangpin.setRoleId(Integer.parseInt(roleId));
			}
			JSONArray jsonArray = JSONArray.fromObject(shangpinService.queryShangpins(
					shangpin, pageBean.getStart(), pageBean.getRows(), sdate, edate));
			JSONObject result = new JSONObject();
			int total = shangpinService.queryShangpins(shangpin, 0, 0, sdate, edate).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addShangpin")
	public void addShangpin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String shangpinName = (String) request.getParameter("shangpinName");
		String shangpinMark = (String) request.getParameter("shangpinMark");
		String shangpinMark1 = (String) request.getParameter("shangpinMark1");
		String shangpinMark2 = (String) request.getParameter("shangpinMark2");
		String shangpinMark3 = (String) request.getParameter("shangpinMark3");
		String shangpinZong = (String) request.getParameter("shangpinZong");
		String shangpinDate = (String) request.getParameter("shangpinDate");
		String shangpinDate1 = (String) request.getParameter("shangpinDate1");
		String shangpinType = (String) request.getParameter("shangpinType");
		String shangpinType1 = (String) request.getParameter("shangpinType1");
		String sptypeId = (String) request.getParameter("sptypeId");
		String userId = (String) request.getParameter("userId");
		String shangpinJin = (String) request.getParameter("shangpinJin");
		String shangpinId = (String) request.getParameter("shangpinId");
		Shangpin shangpin = new Shangpin();

		if (StringUtil.isNotEmpty(shangpinId)) {
			shangpin = shangpinService.getShangpin(Integer.parseInt(shangpinId));
		}
		if (StringUtil.isNotEmpty(shangpinName)) {
			shangpin.setShangpinName(shangpinName);
		}
		if (StringUtil.isNotEmpty(shangpinMark)) {
			shangpin.setShangpinMark(shangpinMark);
		}
		if (StringUtil.isNotEmpty(shangpinMark1)) {
			shangpin.setShangpinMark1(shangpinMark1);
		}
		if (StringUtil.isNotEmpty(shangpinMark2)) {
			shangpin.setShangpinMark2(shangpinMark2);
		}
		if (StringUtil.isNotEmpty(shangpinMark3)) {
			shangpin.setShangpinMark3(shangpinMark3);
		}
		if (StringUtil.isNotEmpty(shangpinZong)) {
			shangpin.setShangpinZong(Integer.parseInt(shangpinZong));
		}
		if (StringUtil.isNotEmpty(shangpinJin)) {
			shangpin.setShangpinJin(Double.parseDouble(shangpinJin));
		}
		if (StringUtil.isNotEmpty(shangpinDate)) {
			shangpin.setShangpinDate(DateUtil.formatString(shangpinDate,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(shangpinDate1)) {
			shangpin.setShangpinDate1(DateUtil.formatString(shangpinDate1,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(shangpinType)) {
			shangpin.setShangpinType(Integer.parseInt(shangpinType));
		}
		if (StringUtil.isNotEmpty(shangpinType1)) {
			shangpin.setShangpinType1(Integer.parseInt(shangpinType1));
		}
		if (StringUtil.isNotEmpty(sptypeId)) {
			shangpin.setSptypeId(Integer.parseInt(sptypeId));
			Sptype sptype = new Sptype();
			sptype = sptypeService.getSptype(Integer.parseInt(sptypeId));
			shangpin.setSptypeName(sptype.getSptypeName());
		}
		if (StringUtil.isNotEmpty(userId)) {
			shangpin.setUserId(Integer.parseInt(userId));
			User user = new User();
			user = userService.getUser(Integer.parseInt(userId));
			shangpin.setUserName(user.getUserName());
			shangpin.setBumenId(user.getBumenId());
			shangpin.setBumenName(user.getBumenName());
			shangpin.setRoleId(user.getRoleId());
			shangpin.setRoleName(user.getRoleName());
		}
		try {
			if (StringUtil.isNotEmpty(shangpinId)) {
				shangpinService.modifyShangpin(shangpin);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			} else {
				Date date = new Date();
				shangpin.setShangpinDate(date);
				shangpin.setShangpinType(0);
				shangpin.setShangpinZong(0);
				shangpin.setShangpinJin(0.0);
				shangpin.setShangpinXiao(0.0);
				shangpin.setShangpinLirun(0.0);
				shangpinService.save(shangpin);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteShangpin")
	public void deleteShangpin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		String delIds = (String) request.getParameter("delIds");
		try {
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				shangpinService.deleteShangpin(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/shangpinComboList")
	public void shangpinComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String shangpinId = (String) request.getParameter("shangpinId");
		String sptypeId = (String) request.getParameter("sptypeId");
		String shangpinType = (String) request.getParameter("shangpinType");
		String shangpinType1 = (String) request.getParameter("shangpinType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		Shangpin shangpin = new Shangpin();
		if (StringUtil.isNotEmpty(shangpinId)) {
			shangpin.setShangpinId(Integer.parseInt(shangpinId));
		}
		if (StringUtil.isNotEmpty(sptypeId)) {
			shangpin.setSptypeId(Integer.parseInt(sptypeId));
		}
		if (StringUtil.isNotEmpty(shangpinType)) {
			shangpin.setShangpinType(Integer.parseInt(shangpinType));
		}
		if (StringUtil.isNotEmpty(shangpinType1)) {
			shangpin.setShangpinType1(Integer.parseInt(shangpinType1));
		}
		if (StringUtil.isNotEmpty(userId)) {
			shangpin.setUserId(Integer.parseInt(userId));
		}
		if (StringUtil.isNotEmpty(bumenId)) {
			shangpin.setBumenId(Integer.parseInt(bumenId));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("shangpinName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(shangpinService.queryShangpins(shangpin, 0, 0, null, null)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/shangpinTongji")
	public void shangpinTongji(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sdate=request.getParameter("sdate");
		String edate=request.getParameter("edate");
		List<Integer> sptypeIds = new ArrayList<Integer>();
		List<String> sptypeNames = new ArrayList<String>();
		List<Integer> shangpinZongshus = new ArrayList<Integer>();
		List<Sptype> sptypes = new ArrayList<Sptype>();
		List<Shangpin> shangpins = new ArrayList<Shangpin>();
		Shangpin shangpin = new Shangpin();
		Integer zongshu = 0;
		try {
			sptypes = sptypeService.querySptypes(null, 0,0);
			for(int i=0;i<sptypes.size();i++){
				sptypeIds.add(sptypes.get(i).getSptypeId());
				sptypeNames.add(sptypes.get(i).getSptypeName());
			}
			for(int i=0;i<sptypeIds.size();i++){
				Integer shangpinZongshu = 0;
				shangpin.setSptypeId(sptypeIds.get(i));
				shangpins = shangpinService.queryShangpins(shangpin, 0, 0, sdate, edate);
				for(int j=0;j<shangpins.size();j++){
					shangpinZongshu = shangpinZongshu + shangpins.size();
				}
				zongshu = zongshu + shangpinZongshu;
				shangpinZongshus.add(shangpinZongshu);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("sptypeNames", sptypeNames);
			session.setAttribute("shangpinZongshus", shangpinZongshus);
			session.setAttribute("zongshu", zongshu);
			response.sendRedirect("admin/shangpintongji.jsp");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/shangchuanShangpin")
	public void shangchuanShangpin(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
			throws Exception {
		try {
			String shangpinId = (String) request.getParameter("shangpinId");
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
			Shangpin shangpin = shangpinService.getShangpin(Integer.parseInt(shangpinId));
			shangpin.setShangpinImg(shangchuandizhi);
			shangpin.setShangpinImgName(shangchuanname);
			shangpinService.modifyShangpin(shangpin);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daoruShangpin")
	public void daoruShangpin(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
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
				Shangpin shangpin = new Shangpin();
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
						shangpin.setShangpinName(cellValue);
						break;
					case 2:
						shangpin.setShangpinMark(cellValue);
						break;
					}
				}
				shangpinService.save(shangpin);
			}
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daochuShangpin")
	public void daochuShangpin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String delIds = (String) request.getParameter("delIds");
		JSONObject result = new JSONObject();
		String str[] = delIds.split(",");

		// 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("shangpins记录");
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
			Shangpin shangpin = shangpinService.getShangpin(Integer.parseInt(str[i]));

			// 创建单元格，并设置值
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(shangpin.getShangpinId());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(1);
			cell.setCellValue(shangpin.getShangpinName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(7);
			cell.setCellValue(shangpin.getShangpinMark());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(16);
			cell.setCellValue(shangpin.getSptypeName());
			cell.setCellStyle(cellStyle);
		}

		// 保存Excel文件
		try {
			Date date = new Date();
			String strdate = DateUtil.formatDate(date, "yyyyMMddhhmmss");
			OutputStream outputStream = new FileOutputStream("D:/shangpin"
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
