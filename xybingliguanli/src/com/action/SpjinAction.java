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
public class SpjinAction {
	@Autowired
	private SpjinService spjinService;
	@Autowired
	private SptypeService sptypeService;
	@Autowired
	private ShangpinService shangpinService;
	@Autowired
	private SpgysService spgysService;
	@Autowired
	private SpcangkuService spcangkuService;
	@Autowired
	private UserService userService;

	/***上传导入开始***/
	private InputStream excelFile;

	public InputStream getExcelFile() {
		return excelFile;
	}
	/***上传导入结束***/

	@RequestMapping("/getSpjins")
	public void getSpjins(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String spjinName = (String) request.getParameter("spjinName");
		String spjinId = (String) request.getParameter("spjinId");
		String sptypeId = (String) request.getParameter("sptypeId");
		String spjinType = (String) request.getParameter("spjinType");
		String spjinType1 = (String) request.getParameter("spjinType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		String roleId = (String) request.getParameter("roleId");
		String spcangkuId = (String) request.getParameter("spcangkuId");
		String spgysId = (String) request.getParameter("spgysId");
		String shangpinId = (String) request.getParameter("shangpinId");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Spjin spjin = new Spjin();
		try {
			if (StringUtil.isNotEmpty(spjinName)) {
				spjin.setSpjinName(spjinName);
			}
			if (StringUtil.isNotEmpty(spjinId)) {
				spjin.setSpjinId(Integer.parseInt(spjinId));
			}
			if (StringUtil.isNotEmpty(sptypeId)) {
				spjin.setSptypeId(Integer.parseInt(sptypeId));
			}
			if (StringUtil.isNotEmpty(spjinType)) {
				spjin.setSpjinType(Integer.parseInt(spjinType));
			}
			if (StringUtil.isNotEmpty(spjinType1)) {
				spjin.setSpjinType1(Integer.parseInt(spjinType1));
			}
			if (StringUtil.isNotEmpty(userId)) {
				spjin.setUserId(Integer.parseInt(userId));
			}
			if (StringUtil.isNotEmpty(bumenId)) {
				spjin.setBumenId(Integer.parseInt(bumenId));
			}
			if (StringUtil.isNotEmpty(roleId)) {
				spjin.setRoleId(Integer.parseInt(roleId));
			}
			if (StringUtil.isNotEmpty(shangpinId)) {
				spjin.setShangpinId(Integer.parseInt(shangpinId));
			}
			if (StringUtil.isNotEmpty(spcangkuId)) {
				spjin.setSpcangkuId(Integer.parseInt(spcangkuId));
			}
			if (StringUtil.isNotEmpty(spgysId)) {
				spjin.setSpgysId(Integer.parseInt(spgysId));
			}
			JSONArray jsonArray = JSONArray.fromObject(spjinService.querySpjins(
					spjin, pageBean.getStart(), pageBean.getRows(), sdate, edate));
			JSONObject result = new JSONObject();
			int total = spjinService.querySpjins(spjin, 0, 0, sdate, edate).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addSpjin")
	public void addSpjin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String spjinName = (String) request.getParameter("spjinName");
		String spjinMark = (String) request.getParameter("spjinMark");
		String spjinMark1 = (String) request.getParameter("spjinMark1");
		String spjinMark2 = (String) request.getParameter("spjinMark2");
		String spjinMark3 = (String) request.getParameter("spjinMark3");
		String spjinZong = (String) request.getParameter("spjinZong");
		String spjinDate = (String) request.getParameter("spjinDate");
		String spjinDate1 = (String) request.getParameter("spjinDate1");
		String spjinType = (String) request.getParameter("spjinType");
		String spjinType1 = (String) request.getParameter("spjinType1");
		String sptypeId = (String) request.getParameter("sptypeId");
		String userId = (String) request.getParameter("userId");
		String spjinJine = (String) request.getParameter("spjinJine");
		String shangpinId = (String) request.getParameter("shangpinId");
		String spgysId = (String) request.getParameter("spgysId");
		String spcangkuId = (String) request.getParameter("spcangkuId");
		String spjinId = (String) request.getParameter("spjinId");
		Spjin spjin = new Spjin();

		if (StringUtil.isNotEmpty(spjinId)) {
			spjin = spjinService.getSpjin(Integer.parseInt(spjinId));
		}
		if (StringUtil.isNotEmpty(spjinName)) {
			spjin.setSpjinName(spjinName);
		}
		if (StringUtil.isNotEmpty(spjinMark)) {
			spjin.setSpjinMark(spjinMark);
		}
		if (StringUtil.isNotEmpty(spjinMark1)) {
			spjin.setSpjinMark1(spjinMark1);
		}
		if (StringUtil.isNotEmpty(spjinMark2)) {
			spjin.setSpjinMark2(spjinMark2);
		}
		if (StringUtil.isNotEmpty(spjinMark3)) {
			spjin.setSpjinMark3(spjinMark3);
		}
		if (StringUtil.isNotEmpty(spjinZong)) {
			spjin.setSpjinZong(Integer.parseInt(spjinZong));
		}
		if (StringUtil.isNotEmpty(spjinJine)) {
			spjin.setSpjinJine(Double.parseDouble(spjinJine));
		}
		if (StringUtil.isNotEmpty(spjinDate)) {
			spjin.setSpjinDate(DateUtil.formatString(spjinDate,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(spjinDate1)) {
			spjin.setSpjinDate1(DateUtil.formatString(spjinDate1,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(spjinType)) {
			spjin.setSpjinType(Integer.parseInt(spjinType));
		}
		if (StringUtil.isNotEmpty(spjinType1)) {
			spjin.setSpjinType1(Integer.parseInt(spjinType1));
		}
		if (StringUtil.isNotEmpty(sptypeId)) {
			spjin.setSptypeId(Integer.parseInt(sptypeId));
			Sptype sptype = new Sptype();
			sptype = sptypeService.getSptype(Integer.parseInt(sptypeId));
			spjin.setSptypeName(sptype.getSptypeName());
		}
		if (StringUtil.isNotEmpty(shangpinId)) {
			spjin.setShangpinId(Integer.parseInt(shangpinId));
			Shangpin shangpin = new Shangpin();
			shangpin = shangpinService.getShangpin(Integer.parseInt(shangpinId));
			spjin.setShangpinName(shangpin.getShangpinName());
			spjin.setSptypeId(shangpin.getSptypeId());
			spjin.setSptypeName(shangpin.getSptypeName());
			spjin.setUserId(shangpin.getUserId());
			spjin.setUserName(shangpin.getUserName());
			spjin.setBumenId(shangpin.getBumenId());
			spjin.setBumenName(shangpin.getBumenName());
			spjin.setRoleId(shangpin.getRoleId());
			spjin.setRoleName(shangpin.getRoleName());
		}
		if (StringUtil.isNotEmpty(userId)) {
			spjin.setUserId(Integer.parseInt(userId));
			User user = new User();
			user = userService.getUser(Integer.parseInt(userId));
			spjin.setUserName(user.getUserName());
			spjin.setBumenId(user.getBumenId());
			spjin.setBumenName(user.getBumenName());
			spjin.setRoleId(user.getRoleId());
			spjin.setRoleName(user.getRoleName());
		}
		if (StringUtil.isNotEmpty(spgysId)) {
			spjin.setSpgysId(Integer.parseInt(spgysId));
			Spgys spgys = new Spgys();
			spgys = spgysService.getSpgys(Integer.parseInt(spgysId));
			spjin.setSpgysName(spgys.getSpgysName());
		}
		if (StringUtil.isNotEmpty(spcangkuId)) {
			spjin.setSpcangkuId(Integer.parseInt(spcangkuId));
			Spcangku spcangku = new Spcangku();
			spcangku = spcangkuService.getSpcangku(Integer.parseInt(spcangkuId));
			spjin.setSpcangkuName(spcangku.getSpcangkuName());
		}
		try {
			if (StringUtil.isNotEmpty(spjinId)) {
				spjin.setSpjinZe(spjin.getSpjinJine()*spjin.getSpjinZong());
				spjinService.modifySpjin(spjin);
				if(spjin.getSpjinType()==1){
					Shangpin shangpin = new Shangpin();
					shangpin = shangpinService.getShangpin(spjin.getShangpinId());
					shangpin.setShangpinZong(shangpin.getShangpinZong()+spjin.getSpjinZong());
					shangpin.setShangpinJin(shangpin.getShangpinJin()+spjin.getSpjinZe());
					shangpin.setShangpinLirun(shangpin.getShangpinXiao()-shangpin.getShangpinJin());
					shangpinService.modifyShangpin(shangpin);
				}
				if(spjin.getSpjinType()==3){
					Shangpin shangpin = new Shangpin();
					shangpin = shangpinService.getShangpin(spjin.getShangpinId());
					shangpin.setShangpinZong(shangpin.getShangpinZong()-spjin.getSpjinZong());
					shangpin.setShangpinJin(shangpin.getShangpinJin()-spjin.getSpjinZe());
					shangpin.setShangpinLirun(shangpin.getShangpinXiao()-shangpin.getShangpinJin());
					shangpinService.modifyShangpin(shangpin);
				}
				result.put("success", "true");
				ResponseUtil.write(response, result);
			} else {
				Date date = new Date();
				spjin.setSpjinDate(date);
				spjin.setSpjinType(0);
				spjin.setSpjinZe(spjin.getSpjinJine()*spjin.getSpjinZong());
				spjinService.save(spjin);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteSpjin")
	public void deleteSpjin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		String delIds = (String) request.getParameter("delIds");
		try {
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				spjinService.deleteSpjin(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/spjinComboList")
	public void spjinComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String spjinId = (String) request.getParameter("spjinId");
		String sptypeId = (String) request.getParameter("sptypeId");
		String spjinType = (String) request.getParameter("spjinType");
		String spjinType1 = (String) request.getParameter("spjinType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		String shangpinId = (String) request.getParameter("shangpinId");
		Spjin spjin = new Spjin();
		if (StringUtil.isNotEmpty(spjinId)) {
			spjin.setSpjinId(Integer.parseInt(spjinId));
		}
		if (StringUtil.isNotEmpty(sptypeId)) {
			spjin.setSptypeId(Integer.parseInt(sptypeId));
		}
		if (StringUtil.isNotEmpty(spjinType)) {
			spjin.setSpjinType(Integer.parseInt(spjinType));
		}
		if (StringUtil.isNotEmpty(spjinType1)) {
			spjin.setSpjinType1(Integer.parseInt(spjinType1));
		}
		if (StringUtil.isNotEmpty(userId)) {
			spjin.setUserId(Integer.parseInt(userId));
		}
		if (StringUtil.isNotEmpty(bumenId)) {
			spjin.setBumenId(Integer.parseInt(bumenId));
		}
		if (StringUtil.isNotEmpty(shangpinId)) {
			spjin.setShangpinId(Integer.parseInt(shangpinId));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("spjinName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(spjinService.querySpjins(spjin, 0, 0, null, null)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/spjinTongji")
	public void spjinTongji(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sdate=request.getParameter("sdate");
		String edate=request.getParameter("edate");
		List<Integer> sptypeIds = new ArrayList<Integer>();
		List<String> sptypeNames = new ArrayList<String>();
		List<Integer> spjinZongshus = new ArrayList<Integer>();
		List<Sptype> sptypes = new ArrayList<Sptype>();
		List<Spjin> spjins = new ArrayList<Spjin>();
		Spjin spjin = new Spjin();
		Integer zongshu = 0;
		try {
			sptypes = sptypeService.querySptypes(null, 0,0);
			for(int i=0;i<sptypes.size();i++){
				sptypeIds.add(sptypes.get(i).getSptypeId());
				sptypeNames.add(sptypes.get(i).getSptypeName());
			}
			for(int i=0;i<sptypeIds.size();i++){
				Integer spjinZongshu = 0;
				spjin.setSptypeId(sptypeIds.get(i));
				spjins = spjinService.querySpjins(spjin, 0, 0, sdate, edate);
				for(int j=0;j<spjins.size();j++){
					spjinZongshu = spjinZongshu + spjins.size();
				}
				zongshu = zongshu + spjinZongshu;
				spjinZongshus.add(spjinZongshu);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("sptypeNames", sptypeNames);
			session.setAttribute("spjinZongshus", spjinZongshus);
			session.setAttribute("zongshu", zongshu);
			response.sendRedirect("admin/spjintongji.jsp");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/shangchuanSpjin")
	public void shangchuanSpjin(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
			throws Exception {
		try {
			String spjinId = (String) request.getParameter("spjinId");
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
			Spjin spjin = spjinService.getSpjin(Integer.parseInt(spjinId));
			spjin.setSpjinImg(shangchuandizhi);
			spjin.setSpjinImgName(shangchuanname);
			spjinService.modifySpjin(spjin);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daoruSpjin")
	public void daoruSpjin(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
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
				Spjin spjin = new Spjin();
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
						spjin.setSpjinName(cellValue);
						break;
					case 2:
						spjin.setSpjinMark(cellValue);
						break;
					}
				}
				spjinService.save(spjin);
			}
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daochuSpjin")
	public void daochuSpjin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String delIds = (String) request.getParameter("delIds");
		JSONObject result = new JSONObject();
		String str[] = delIds.split(",");

		// 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("spjins记录");
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
			Spjin spjin = spjinService.getSpjin(Integer.parseInt(str[i]));

			// 创建单元格，并设置值
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(spjin.getSpjinId());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(1);
			cell.setCellValue(spjin.getSpjinName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(7);
			cell.setCellValue(spjin.getSpjinMark());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(16);
			cell.setCellValue(spjin.getSptypeName());
			cell.setCellStyle(cellStyle);
		}

		// 保存Excel文件
		try {
			Date date = new Date();
			String strdate = DateUtil.formatDate(date, "yyyyMMddhhmmss");
			OutputStream outputStream = new FileOutputStream("D:/spjin"
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
