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
public class UzhanneiAction {
	@Autowired
	private UzhanneiService uzhanneiService;
	@Autowired
	private UxtypeService uxtypeService;
	@Autowired
	private UserService userService;

	/***上传导入开始***/
	private InputStream excelFile;

	public InputStream getExcelFile() {
		return excelFile;
	}
	/***上传导入结束***/

	@RequestMapping("/getUzhanneis")
	public void getUzhanneis(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String uzhanneiName = (String) request.getParameter("uzhanneiName");
		String uzhanneiId = (String) request.getParameter("uzhanneiId");
		String uxtypeId = (String) request.getParameter("uxtypeId");
		String uzhanneiType = (String) request.getParameter("uzhanneiType");
		String uzhanneiType1 = (String) request.getParameter("uzhanneiType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		String shouuserId = request.getParameter("shouuserId");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Uzhannei uzhannei = new Uzhannei();
		try {
			if (StringUtil.isNotEmpty(uzhanneiName)) {
				uzhannei.setUzhanneiName(uzhanneiName);
			}
			if (StringUtil.isNotEmpty(uzhanneiId)) {
				uzhannei.setUzhanneiId(Integer.parseInt(uzhanneiId));
			}
			if (StringUtil.isNotEmpty(uxtypeId)) {
				uzhannei.setUxtypeId(Integer.parseInt(uxtypeId));
			}
			if (StringUtil.isNotEmpty(uzhanneiType)) {
				uzhannei.setUzhanneiType(Integer.parseInt(uzhanneiType));
			}
			if (StringUtil.isNotEmpty(uzhanneiType1)) {
				uzhannei.setUzhanneiType1(Integer.parseInt(uzhanneiType1));
			}
			if (StringUtil.isNotEmpty(userId)) {
				uzhannei.setUserId(Integer.parseInt(userId));
			}
			if (StringUtil.isNotEmpty(bumenId)) {
				uzhannei.setBumenId(Integer.parseInt(bumenId));
			}
			if (StringUtil.isNotEmpty(shouuserId)) {
				uzhannei.setShouuserId(Integer.parseInt(shouuserId));
			}
			JSONArray jsonArray = JSONArray.fromObject(uzhanneiService.queryUzhanneis(
					uzhannei, pageBean.getStart(), pageBean.getRows(), sdate, edate));
			JSONObject result = new JSONObject();
			int total = uzhanneiService.queryUzhanneis(uzhannei, 0, 0, sdate, edate).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addUzhannei")
	public void addUzhannei(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String uzhanneiName = (String) request.getParameter("uzhanneiName");
		String uzhanneiMark = (String) request.getParameter("uzhanneiMark");
		String uzhanneiMark1 = (String) request.getParameter("uzhanneiMark1");
		String uzhanneiMark2 = (String) request.getParameter("uzhanneiMark2");
		String uzhanneiDate = (String) request.getParameter("uzhanneiDate");
		String uzhanneiType = (String) request.getParameter("uzhanneiType");
		String uzhanneiType1 = (String) request.getParameter("uzhanneiType1");
		String uxtypeId = (String) request.getParameter("uxtypeId");
		String userId = (String) request.getParameter("userId");
		String shouuserId = request.getParameter("shouuserId");
		String uzhanneiDate1 = request.getParameter("uzhanneiDate1");
		String uzhanneiId = (String) request.getParameter("uzhanneiId");
		Uzhannei uzhannei = new Uzhannei();

		if (StringUtil.isNotEmpty(uzhanneiId)) {
			uzhannei = uzhanneiService.getUzhannei(Integer.parseInt(uzhanneiId));
		}
		if (StringUtil.isNotEmpty(uzhanneiName)) {
			uzhannei.setUzhanneiName(uzhanneiName);
		}
		if (StringUtil.isNotEmpty(uzhanneiMark)) {
			uzhannei.setUzhanneiMark(uzhanneiMark);
		}
		if (StringUtil.isNotEmpty(uzhanneiMark1)) {
			uzhannei.setUzhanneiMark1(uzhanneiMark1);
		}
		if (StringUtil.isNotEmpty(uzhanneiMark2)) {
			uzhannei.setUzhanneiMark2(uzhanneiMark2);
			Date date = new Date();
			uzhannei.setUzhanneiDate1(date);
			uzhannei.setUzhanneiType(1);
		}
		if (StringUtil.isNotEmpty(uzhanneiDate)) {
			uzhannei.setUzhanneiDate(DateUtil.formatString(uzhanneiDate,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(uzhanneiDate1)) {
			uzhannei.setUzhanneiDate1(DateUtil.formatString(uzhanneiDate1,
					"yyyy-MM-dd HH:mm:ss"));
		}
		if (StringUtil.isNotEmpty(uzhanneiType)) {
			uzhannei.setUzhanneiType(Integer.parseInt(uzhanneiType));
		}
		if (StringUtil.isNotEmpty(uzhanneiType1)) {
			uzhannei.setUzhanneiType1(Integer.parseInt(uzhanneiType1));
		}
		if (StringUtil.isNotEmpty(uxtypeId)) {
			uzhannei.setUxtypeId(Integer.parseInt(uxtypeId));
			Uxtype uxtype = new Uxtype();
			uxtype = uxtypeService.getUxtype(Integer.parseInt(uxtypeId));
			uzhannei.setUxtypeName(uxtype.getUxtypeName());
		}
		if (StringUtil.isNotEmpty(userId)) {
			uzhannei.setUserId(Integer.parseInt(userId));
			User user = new User();
			user = userService.getUser(Integer.parseInt(userId));
			uzhannei.setUserName(user.getUserName());
			uzhannei.setBumenId(user.getBumenId());
			uzhannei.setBumenName(user.getBumenName());
		}
		if (StringUtil.isNotEmpty(shouuserId)) {
			uzhannei.setShouuserId(Integer.parseInt(shouuserId));
			User user = new User();
			user = userService.getUser(Integer.parseInt(shouuserId));
			uzhannei.setShouuserName(user.getUserName());
		}
		try {
			if (StringUtil.isNotEmpty(uzhanneiId)) {
				uzhanneiService.modifyUzhannei(uzhannei);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			} else {
				Date date = new Date();
				uzhannei.setUzhanneiDate(date);
				uzhanneiService.save(uzhannei);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteUzhannei")
	public void deleteUzhannei(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		String delIds = (String) request.getParameter("delIds");
		try {
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				uzhanneiService.deleteUzhannei(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/uzhanneiComboList")
	public void uzhanneiComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String uzhanneiId = (String) request.getParameter("uzhanneiId");
		String uxtypeId = (String) request.getParameter("uxtypeId");
		String uzhanneiType = (String) request.getParameter("uzhanneiType");
		String uzhanneiType1 = (String) request.getParameter("uzhanneiType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		Uzhannei uzhannei = new Uzhannei();
		if (StringUtil.isNotEmpty(uzhanneiId)) {
			uzhannei.setUzhanneiId(Integer.parseInt(uzhanneiId));
		}
		if (StringUtil.isNotEmpty(uxtypeId)) {
			uzhannei.setUxtypeId(Integer.parseInt(uxtypeId));
		}
		if (StringUtil.isNotEmpty(uzhanneiType)) {
			uzhannei.setUzhanneiType(Integer.parseInt(uzhanneiType));
		}
		if (StringUtil.isNotEmpty(uzhanneiType1)) {
			uzhannei.setUzhanneiType1(Integer.parseInt(uzhanneiType1));
		}
		if (StringUtil.isNotEmpty(userId)) {
			uzhannei.setUserId(Integer.parseInt(userId));
		}
		if (StringUtil.isNotEmpty(bumenId)) {
			uzhannei.setBumenId(Integer.parseInt(bumenId));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("uzhanneiName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(uzhanneiService.queryUzhanneis(uzhannei, 0, 0, null, null)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/uzhanneiTongji")
	public void uzhanneiTongji(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sdate=request.getParameter("sdate");
		String edate=request.getParameter("edate");
		List<Integer> uxtypeIds = new ArrayList<Integer>();
		List<String> uxtypeNames = new ArrayList<String>();
		List<Integer> uzhanneiZongshus = new ArrayList<Integer>();
		List<Uxtype> uxtypes = new ArrayList<Uxtype>();
		List<Uzhannei> uzhanneis = new ArrayList<Uzhannei>();
		Uzhannei uzhannei = new Uzhannei();
		Integer zongshu = 0;
		try {
			uxtypes = uxtypeService.queryUxtypes(null, 0,0);
			for(int i=0;i<uxtypes.size();i++){
				uxtypeIds.add(uxtypes.get(i).getUxtypeId());
				uxtypeNames.add(uxtypes.get(i).getUxtypeName());
			}
			for(int i=0;i<uxtypeIds.size();i++){
				Integer uzhanneiZongshu = 0;
				uzhannei.setUxtypeId(uxtypeIds.get(i));
				uzhanneis = uzhanneiService.queryUzhanneis(uzhannei, 0, 0, sdate, edate);
				for(int j=0;j<uzhanneis.size();j++){
					uzhanneiZongshu = uzhanneiZongshu + uzhanneis.size();
				}
				zongshu = zongshu + uzhanneiZongshu;
				uzhanneiZongshus.add(uzhanneiZongshu);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("uxtypeNames", uxtypeNames);
			session.setAttribute("uzhanneiZongshus", uzhanneiZongshus);
			session.setAttribute("zongshu", zongshu);
			response.sendRedirect("admin/uzhanneitongji.jsp");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/shangchuanUzhannei")
	public void shangchuanUzhannei(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
			throws Exception {
		try {
			String uzhanneiId = (String) request.getParameter("uzhanneiId");
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
			Uzhannei uzhannei = uzhanneiService.getUzhannei(Integer.parseInt(uzhanneiId));
			uzhannei.setUzhanneiImg(shangchuandizhi);
			uzhannei.setUzhanneiImgName(shangchuanname);
			uzhanneiService.modifyUzhannei(uzhannei);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/xiazaiUzhannei")
	public void xiazaiUzhannei(HttpServletRequest request, HttpServletResponse response)
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

	@RequestMapping("/daoruUzhannei")
	public void daoruUzhannei(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
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
				Uzhannei uzhannei = new Uzhannei();
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
						uzhannei.setUzhanneiName(cellValue);
						break;
					case 2:
						uzhannei.setUzhanneiMark(cellValue);
						break;
					}
				}
				uzhanneiService.save(uzhannei);
			}
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daochuUzhannei")
	public void daochuUzhannei(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String delIds = (String) request.getParameter("delIds");
		JSONObject result = new JSONObject();
		String str[] = delIds.split(",");

		// 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("uzhanneis记录");
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
			Uzhannei uzhannei = uzhanneiService.getUzhannei(Integer.parseInt(str[i]));

			// 创建单元格，并设置值
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(uzhannei.getUzhanneiId());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(1);
			cell.setCellValue(uzhannei.getUzhanneiName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(7);
			cell.setCellValue(uzhannei.getUzhanneiMark());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(16);
			cell.setCellValue(uzhannei.getUxtypeName());
			cell.setCellStyle(cellStyle);
		}

		// 保存Excel文件
		try {
			Date date = new Date();
			String strdate = DateUtil.formatDate(date, "yyyyMMddhhmmss");
			OutputStream outputStream = new FileOutputStream("D:/uzhannei"
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
