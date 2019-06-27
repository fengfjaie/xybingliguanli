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
public class UyijianAction {
	@Autowired
	private UyijianService uyijianService;
	@Autowired
	private UserService userService;

	/***上传导入开始***/
	private InputStream excelFile;

	public InputStream getExcelFile() {
		return excelFile;
	}
	/***上传导入结束***/

	@RequestMapping("/getUyijians")
	public void getUyijians(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String uyijianName = (String) request.getParameter("uyijianName");
		String uyijianId = (String) request.getParameter("uyijianId");
		String uyijianType = (String) request.getParameter("uyijianType");
		String uyijianType1 = (String) request.getParameter("uyijianType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Uyijian uyijian = new Uyijian();
		try {
			if (StringUtil.isNotEmpty(uyijianName)) {
				uyijian.setUyijianName(uyijianName);
			}
			if (StringUtil.isNotEmpty(uyijianId)) {
				uyijian.setUyijianId(Integer.parseInt(uyijianId));
			}
			if (StringUtil.isNotEmpty(uyijianType)) {
				uyijian.setUyijianType(Integer.parseInt(uyijianType));
			}
			if (StringUtil.isNotEmpty(uyijianType1)) {
				uyijian.setUyijianType1(Integer.parseInt(uyijianType1));
			}
			if (StringUtil.isNotEmpty(userId)) {
				uyijian.setUserId(Integer.parseInt(userId));
			}
			if (StringUtil.isNotEmpty(bumenId)) {
				uyijian.setBumenId(Integer.parseInt(bumenId));
			}
			JSONArray jsonArray = JSONArray.fromObject(uyijianService.queryUyijians(
					uyijian, pageBean.getStart(), pageBean.getRows(), sdate, edate));
			JSONObject result = new JSONObject();
			int total = uyijianService.queryUyijians(uyijian, 0, 0, sdate, edate).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addUyijian")
	public void addUyijian(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String uyijianName = (String) request.getParameter("uyijianName");
		String uyijianMark = (String) request.getParameter("uyijianMark");
		String uyijianMark1 = (String) request.getParameter("uyijianMark1");
		String uyijianMark2 = (String) request.getParameter("uyijianMark2");
		String uyijianDate = (String) request.getParameter("uyijianDate");
		String uyijianDate1 = (String) request.getParameter("uyijianDate1");
		String uyijianType = (String) request.getParameter("uyijianType");
		String uyijianType1 = (String) request.getParameter("uyijianType1");
		String userId = (String) request.getParameter("userId");
		String uyijianId = (String) request.getParameter("uyijianId");
		Uyijian uyijian = new Uyijian();

		if (StringUtil.isNotEmpty(uyijianId)) {
			uyijian = uyijianService.getUyijian(Integer.parseInt(uyijianId));
		}
		if (StringUtil.isNotEmpty(uyijianName)) {
			uyijian.setUyijianName(uyijianName);
		}
		if (StringUtil.isNotEmpty(uyijianMark)) {
			uyijian.setUyijianMark(uyijianMark);
		}
		if (StringUtil.isNotEmpty(uyijianMark1)) {
			uyijian.setUyijianMark1(uyijianMark1);
		}
		if (StringUtil.isNotEmpty(uyijianMark2)) {
			uyijian.setUyijianMark2(uyijianMark2);
		}
		if (StringUtil.isNotEmpty(uyijianDate)) {
			uyijian.setUyijianDate(DateUtil.formatString(uyijianDate,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(uyijianDate1)) {
			uyijian.setUyijianDate1(DateUtil.formatString(uyijianDate1,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(uyijianType)) {
			uyijian.setUyijianType(Integer.parseInt(uyijianType));
		}
		if (StringUtil.isNotEmpty(uyijianType1)) {
			uyijian.setUyijianType1(Integer.parseInt(uyijianType1));
		}
		if (StringUtil.isNotEmpty(userId)) {
			uyijian.setUserId(Integer.parseInt(userId));
			User user = new User();
			user = userService.getUser(Integer.parseInt(userId));
			uyijian.setUserName(user.getUserName());
			uyijian.setBumenId(user.getBumenId());
			uyijian.setBumenName(user.getBumenName());
		}
		try {
			if (StringUtil.isNotEmpty(uyijianId)) {
				uyijianService.modifyUyijian(uyijian);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			} else {
				Date date = new Date();
				uyijian.setUyijianDate(date);
				uyijian.setUyijianType(0);
				uyijianService.save(uyijian);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteUyijian")
	public void deleteUyijian(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		String delIds = (String) request.getParameter("delIds");
		try {
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				uyijianService.deleteUyijian(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/uyijianComboList")
	public void uyijianComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String uyijianId = (String) request.getParameter("uyijianId");
		String uyijianType = (String) request.getParameter("uyijianType");
		String uyijianType1 = (String) request.getParameter("uyijianType1");
		String userId = (String) request.getParameter("userId");
		String bumenId = (String) request.getParameter("bumenId");
		Uyijian uyijian = new Uyijian();
		if (StringUtil.isNotEmpty(uyijianId)) {
			uyijian.setUyijianId(Integer.parseInt(uyijianId));
		}
		if (StringUtil.isNotEmpty(uyijianType)) {
			uyijian.setUyijianType(Integer.parseInt(uyijianType));
		}
		if (StringUtil.isNotEmpty(uyijianType1)) {
			uyijian.setUyijianType1(Integer.parseInt(uyijianType1));
		}
		if (StringUtil.isNotEmpty(userId)) {
			uyijian.setUserId(Integer.parseInt(userId));
		}
		if (StringUtil.isNotEmpty(bumenId)) {
			uyijian.setBumenId(Integer.parseInt(bumenId));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("uyijianName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(uyijianService.queryUyijians(uyijian, 0, 0, null, null)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/uyijianTongji")
	public void uyijianTongji(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sdate=request.getParameter("sdate");
		String edate=request.getParameter("edate");
		List<Integer> uyijianTypes = new ArrayList<Integer>();
		uyijianTypes.add(0);
		uyijianTypes.add(1);
		uyijianTypes.add(2);
		uyijianTypes.add(3);
		List<String> uxtypeNames = new ArrayList<String>();
		uxtypeNames.add("0");
		uxtypeNames.add("1");
		uxtypeNames.add("2");
		uxtypeNames.add("3");
		List<Integer> uyijianZongshus = new ArrayList<Integer>();
		List<Uyijian> uyijians = new ArrayList<Uyijian>();
		Uyijian uyijian = new Uyijian();
		Integer zongshu = 0;
		try {
			for(int i=0;i<uyijianTypes.size();i++){
				Integer uyijianZongshu = 0;
				uyijian.setUyijianType(uyijianTypes.get(i));
				uyijians = uyijianService.queryUyijians(uyijian, 0, 0, sdate, edate);
				for(int j=0;j<uyijians.size();j++){
					uyijianZongshu = uyijianZongshu + uyijians.size();
				}
				zongshu = zongshu + uyijianZongshu;
				uyijianZongshus.add(uyijianZongshu);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("uxtypeNames", uxtypeNames);
			session.setAttribute("uyijianZongshus", uyijianZongshus);
			session.setAttribute("zongshu", zongshu);
			response.sendRedirect("admin/uyijiantongji.jsp");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/shangchuanUyijian")
	public void shangchuanUyijian(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
			throws Exception {
		try {
			String uyijianId = (String) request.getParameter("uyijianId");
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
			Uyijian uyijian = uyijianService.getUyijian(Integer.parseInt(uyijianId));
			uyijian.setUyijianImg(shangchuandizhi);
			uyijian.setUyijianImgName(shangchuanname);
			uyijianService.modifyUyijian(uyijian);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/xiazaiUyijian")
	public void xiazaiUyijian(HttpServletRequest request, HttpServletResponse response)
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

	@RequestMapping("/daoruUyijian")
	public void daoruUyijian(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
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
				Uyijian uyijian = new Uyijian();
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
						uyijian.setUyijianName(cellValue);
						break;
					case 2:
						uyijian.setUyijianMark(cellValue);
						break;
					}
				}
				uyijianService.save(uyijian);
			}
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daochuUyijian")
	public void daochuUyijian(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String delIds = (String) request.getParameter("delIds");
		JSONObject result = new JSONObject();
		String str[] = delIds.split(",");

		// 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("uyijians记录");
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
			Uyijian uyijian = uyijianService.getUyijian(Integer.parseInt(str[i]));

			// 创建单元格，并设置值
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(uyijian.getUyijianId());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(1);
			cell.setCellValue(uyijian.getUyijianName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(7);
			cell.setCellValue(uyijian.getUyijianMark());
			cell.setCellStyle(cellStyle);
		}

		// 保存Excel文件
		try {
			Date date = new Date();
			String strdate = DateUtil.formatDate(date, "yyyyMMddhhmmss");
			OutputStream outputStream = new FileOutputStream("D:/uyijian"
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
