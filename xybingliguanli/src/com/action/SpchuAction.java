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
public class SpchuAction {
	@Autowired
	private SpchuService spchuService;
	@Autowired
	private SptypeService sptypeService;
	@Autowired
	private ShangpinService shangpinService;
	@Autowired
	private YonghuService yonghuService;
	@Autowired
	private UserService userService;

	/***上传导入开始***/
	private InputStream excelFile;

	public InputStream getExcelFile() {
		return excelFile;
	}
	/***上传导入结束***/

	@RequestMapping("/getSpchus")
	public void getSpchus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String spchuName = (String) request.getParameter("spchuName");
		String spchuId = (String) request.getParameter("spchuId");
		String sptypeId = (String) request.getParameter("sptypeId");
		String spchuType = (String) request.getParameter("spchuType");
		String spchuType1 = (String) request.getParameter("spchuType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		String roleId = (String) request.getParameter("roleId");
		String yhroleId = (String) request.getParameter("yhroleId");
		String yonghuId = (String) request.getParameter("yonghuId");
		String yhbumenId = (String) request.getParameter("yhbumenId");
		String shangpinId = (String) request.getParameter("shangpinId");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Spchu spchu = new Spchu();
		try {
			if (StringUtil.isNotEmpty(spchuName)) {
				spchu.setSpchuName(spchuName);
			}
			if (StringUtil.isNotEmpty(spchuId)) {
				spchu.setSpchuId(Integer.parseInt(spchuId));
			}
			if (StringUtil.isNotEmpty(sptypeId)) {
				spchu.setSptypeId(Integer.parseInt(sptypeId));
			}
			if (StringUtil.isNotEmpty(spchuType)) {
				spchu.setSpchuType(Integer.parseInt(spchuType));
			}
			if (StringUtil.isNotEmpty(spchuType1)) {
				spchu.setSpchuType1(Integer.parseInt(spchuType1));
			}
			if (StringUtil.isNotEmpty(userId)) {
				spchu.setUserId(Integer.parseInt(userId));
			}
			if (StringUtil.isNotEmpty(bumenId)) {
				spchu.setBumenId(Integer.parseInt(bumenId));
			}
			if (StringUtil.isNotEmpty(roleId)) {
				spchu.setRoleId(Integer.parseInt(roleId));
			}
			if (StringUtil.isNotEmpty(shangpinId)) {
				spchu.setShangpinId(Integer.parseInt(shangpinId));
			}
			if (StringUtil.isNotEmpty(yhroleId)) {
				spchu.setYhroleId(Integer.parseInt(yhroleId));
			}
			if (StringUtil.isNotEmpty(yhbumenId)) {
				spchu.setYhbumenId(Integer.parseInt(yhbumenId));
			}
			if (StringUtil.isNotEmpty(yonghuId)) {
				spchu.setYonghuId(Integer.parseInt(yonghuId));
			}
			JSONArray jsonArray = JSONArray.fromObject(spchuService.querySpchus(
					spchu, pageBean.getStart(), pageBean.getRows(), sdate, edate));
			JSONObject result = new JSONObject();
			int total = spchuService.querySpchus(spchu, 0, 0, sdate, edate).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addSpchu")
	public void addSpchu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String spchuName = (String) request.getParameter("spchuName");
		String spchuMark = (String) request.getParameter("spchuMark");
		String spchuMark1 = (String) request.getParameter("spchuMark1");
		String spchuMark2 = (String) request.getParameter("spchuMark2");
		String spchuMark3 = (String) request.getParameter("spchuMark3");
		String spchuZong = (String) request.getParameter("spchuZong");
		String spchuDate = (String) request.getParameter("spchuDate");
		String spchuDate1 = (String) request.getParameter("spchuDate1");
		String spchuType = (String) request.getParameter("spchuType");
		String spchuType1 = (String) request.getParameter("spchuType1");
		String sptypeId = (String) request.getParameter("sptypeId");
		String userId = (String) request.getParameter("userId");
		String spchuJine = (String) request.getParameter("spchuJine");
		String shangpinId = (String) request.getParameter("shangpinId");
		String yonghuId = (String) request.getParameter("yonghuId");
		String spchuId = (String) request.getParameter("spchuId");
		Spchu spchu = new Spchu();

		if (StringUtil.isNotEmpty(spchuId)) {
			spchu = spchuService.getSpchu(Integer.parseInt(spchuId));
		}
		if (StringUtil.isNotEmpty(spchuName)) {
			spchu.setSpchuName(spchuName);
		}
		if (StringUtil.isNotEmpty(spchuMark)) {
			spchu.setSpchuMark(spchuMark);
		}
		if (StringUtil.isNotEmpty(spchuMark1)) {
			spchu.setSpchuMark1(spchuMark1);
		}
		if (StringUtil.isNotEmpty(spchuMark2)) {
			spchu.setSpchuMark2(spchuMark2);
		}
		if (StringUtil.isNotEmpty(spchuMark3)) {
			spchu.setSpchuMark3(spchuMark3);
		}
		if (StringUtil.isNotEmpty(spchuZong)) {
			spchu.setSpchuZong(Integer.parseInt(spchuZong));
		}
		if (StringUtil.isNotEmpty(spchuJine)) {
			spchu.setSpchuJine(Double.parseDouble(spchuJine));
		}
		if (StringUtil.isNotEmpty(spchuDate)) {
			spchu.setSpchuDate(DateUtil.formatString(spchuDate,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(spchuDate1)) {
			spchu.setSpchuDate1(DateUtil.formatString(spchuDate1,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(spchuType)) {
			spchu.setSpchuType(Integer.parseInt(spchuType));
		}
		if (StringUtil.isNotEmpty(spchuType1)) {
			spchu.setSpchuType1(Integer.parseInt(spchuType1));
		}
		if (StringUtil.isNotEmpty(sptypeId)) {
			spchu.setSptypeId(Integer.parseInt(sptypeId));
			Sptype sptype = new Sptype();
			sptype = sptypeService.getSptype(Integer.parseInt(sptypeId));
			spchu.setSptypeName(sptype.getSptypeName());
		}
		if (StringUtil.isNotEmpty(shangpinId)) {
			spchu.setShangpinId(Integer.parseInt(shangpinId));
			Shangpin shangpin = new Shangpin();
			shangpin = shangpinService.getShangpin(Integer.parseInt(shangpinId));
			spchu.setShangpinName(shangpin.getShangpinName());
			spchu.setSptypeId(shangpin.getSptypeId());
			spchu.setSptypeName(shangpin.getSptypeName());
			spchu.setUserId(shangpin.getUserId());
			spchu.setUserName(shangpin.getUserName());
			spchu.setBumenId(shangpin.getBumenId());
			spchu.setBumenName(shangpin.getBumenName());
			spchu.setRoleId(shangpin.getRoleId());
			spchu.setRoleName(shangpin.getRoleName());
		}
		if (StringUtil.isNotEmpty(userId)) {
			spchu.setUserId(Integer.parseInt(userId));
			User user = new User();
			user = userService.getUser(Integer.parseInt(userId));
			spchu.setUserName(user.getUserName());
			spchu.setBumenId(user.getBumenId());
			spchu.setBumenName(user.getBumenName());
			spchu.setRoleId(user.getRoleId());
			spchu.setRoleName(user.getRoleName());
		}
		if (StringUtil.isNotEmpty(yonghuId)) {
			spchu.setYonghuId(Integer.parseInt(yonghuId));
			Yonghu yonghu = new Yonghu();
			yonghu = yonghuService.getYonghu(Integer.parseInt(yonghuId));
			spchu.setYonghuName(yonghu.getYonghuName());
			spchu.setYhbumenId(yonghu.getYhbumenId());
			spchu.setYhbumenName(yonghu.getYhbumenName());
			spchu.setYhroleId(yonghu.getYhroleId());
			spchu.setYhroleName(yonghu.getYhroleName());
		}
		try {
			if (StringUtil.isNotEmpty(spchuId)) {
				spchu.setSpchuZe(spchu.getSpchuJine()*spchu.getSpchuZong());
				spchuService.modifySpchu(spchu);
				if(spchu.getSpchuType()==1){
					Shangpin shangpin = new Shangpin();
					shangpin = shangpinService.getShangpin(spchu.getShangpinId());
					shangpin.setShangpinZong(shangpin.getShangpinZong()-spchu.getSpchuZong());
					shangpin.setShangpinXiao(shangpin.getShangpinXiao()+spchu.getSpchuZe());
					shangpin.setShangpinLirun(shangpin.getShangpinXiao()-shangpin.getShangpinJin());
					shangpinService.modifyShangpin(shangpin);
				}
				if(spchu.getSpchuType()==3){
					Shangpin shangpin = new Shangpin();
					shangpin = shangpinService.getShangpin(spchu.getShangpinId());
					shangpin.setShangpinZong(shangpin.getShangpinZong()+spchu.getSpchuZong());
					shangpin.setShangpinXiao(shangpin.getShangpinXiao()-spchu.getSpchuZe());
					shangpin.setShangpinLirun(shangpin.getShangpinXiao()-shangpin.getShangpinJin());
					shangpinService.modifyShangpin(shangpin);
				}
				result.put("success", "true");
				ResponseUtil.write(response, result);
			} else {
				Date date = new Date();
				spchu.setSpchuDate(date);
				spchu.setSpchuType(0);
				spchu.setSpchuZe(spchu.getSpchuJine()*spchu.getSpchuZong());
				Shangpin shangpin = new Shangpin();
				shangpin = shangpinService.getShangpin(spchu.getShangpinId());
				if(spchu.getSpchuZong()>shangpin.getShangpinZong()){
					result.put("success", "true");
					result.put("errorMsg", "商品库存不够！");
					ResponseUtil.write(response, result);
				}else{
					spchuService.save(spchu);
					result.put("success", "true");
					ResponseUtil.write(response, result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteSpchu")
	public void deleteSpchu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		String delIds = (String) request.getParameter("delIds");
		try {
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				spchuService.deleteSpchu(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/spchuComboList")
	public void spchuComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String spchuId = (String) request.getParameter("spchuId");
		String sptypeId = (String) request.getParameter("sptypeId");
		String spchuType = (String) request.getParameter("spchuType");
		String spchuType1 = (String) request.getParameter("spchuType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		String shangpinId = (String) request.getParameter("shangpinId");
		Spchu spchu = new Spchu();
		if (StringUtil.isNotEmpty(spchuId)) {
			spchu.setSpchuId(Integer.parseInt(spchuId));
		}
		if (StringUtil.isNotEmpty(sptypeId)) {
			spchu.setSptypeId(Integer.parseInt(sptypeId));
		}
		if (StringUtil.isNotEmpty(spchuType)) {
			spchu.setSpchuType(Integer.parseInt(spchuType));
		}
		if (StringUtil.isNotEmpty(spchuType1)) {
			spchu.setSpchuType1(Integer.parseInt(spchuType1));
		}
		if (StringUtil.isNotEmpty(userId)) {
			spchu.setUserId(Integer.parseInt(userId));
		}
		if (StringUtil.isNotEmpty(bumenId)) {
			spchu.setBumenId(Integer.parseInt(bumenId));
		}
		if (StringUtil.isNotEmpty(shangpinId)) {
			spchu.setShangpinId(Integer.parseInt(shangpinId));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("spchuName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(spchuService.querySpchus(spchu, 0, 0, null, null)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/spchuTongji")
	public void spchuTongji(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sdate=request.getParameter("sdate");
		String edate=request.getParameter("edate");
		List<Integer> sptypeIds = new ArrayList<Integer>();
		List<String> sptypeNames = new ArrayList<String>();
		List<Integer> spchuZongshus = new ArrayList<Integer>();
		List<Sptype> sptypes = new ArrayList<Sptype>();
		List<Spchu> spchus = new ArrayList<Spchu>();
		Spchu spchu = new Spchu();
		Integer zongshu = 0;
		try {
			sptypes = sptypeService.querySptypes(null, 0,0);
			for(int i=0;i<sptypes.size();i++){
				sptypeIds.add(sptypes.get(i).getSptypeId());
				sptypeNames.add(sptypes.get(i).getSptypeName());
			}
			for(int i=0;i<sptypeIds.size();i++){
				Integer spchuZongshu = 0;
				spchu.setSptypeId(sptypeIds.get(i));
				spchus = spchuService.querySpchus(spchu, 0, 0, sdate, edate);
				for(int j=0;j<spchus.size();j++){
					spchuZongshu = spchuZongshu + spchus.size();
				}
				zongshu = zongshu + spchuZongshu;
				spchuZongshus.add(spchuZongshu);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("sptypeNames", sptypeNames);
			session.setAttribute("spchuZongshus", spchuZongshus);
			session.setAttribute("zongshu", zongshu);
			response.sendRedirect("admin/spchutongji.jsp");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/shangchuanSpchu")
	public void shangchuanSpchu(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
			throws Exception {
		try {
			String spchuId = (String) request.getParameter("spchuId");
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
			Spchu spchu = spchuService.getSpchu(Integer.parseInt(spchuId));
			spchu.setSpchuImg(shangchuandizhi);
			spchu.setSpchuImgName(shangchuanname);
			spchuService.modifySpchu(spchu);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daoruSpchu")
	public void daoruSpchu(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
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
				Spchu spchu = new Spchu();
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
						spchu.setSpchuName(cellValue);
						break;
					case 2:
						spchu.setSpchuMark(cellValue);
						break;
					}
				}
				spchuService.save(spchu);
			}
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daochuSpchu")
	public void daochuSpchu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String delIds = (String) request.getParameter("delIds");
		JSONObject result = new JSONObject();
		String str[] = delIds.split(",");

		// 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("spchus记录");
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
			Spchu spchu = spchuService.getSpchu(Integer.parseInt(str[i]));

			// 创建单元格，并设置值
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(spchu.getSpchuId());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(1);
			cell.setCellValue(spchu.getSpchuName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(7);
			cell.setCellValue(spchu.getSpchuMark());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(16);
			cell.setCellValue(spchu.getSptypeName());
			cell.setCellStyle(cellStyle);
		}

		// 保存Excel文件
		try {
			Date date = new Date();
			String strdate = DateUtil.formatDate(date, "yyyyMMddhhmmss");
			OutputStream outputStream = new FileOutputStream("D:/spchu"
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
