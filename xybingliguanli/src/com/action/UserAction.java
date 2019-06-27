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
public class UserAction {
	@Autowired
	private UserService userService;
	@Autowired
	private BumenService bumenService;
	@Autowired
	private RoleService roleService;

	/***上传导入开始***/
	private InputStream excelFile;

	public InputStream getExcelFile() {
		return excelFile;
	}
	/***上传导入结束***/

	@RequestMapping("/getUsers")
	public void getUsers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String userName = (String) request.getParameter("userName");
		String userXingming = (String) request.getParameter("userXingming");
		String userId = (String) request.getParameter("userId");
		String userType1 = (String) request.getParameter("userType1");
		String userType2 = (String) request.getParameter("userType2");
		String roleId = (String) request.getParameter("roleId");
		String userSex = (String) request.getParameter("userSex");
		String bumenId = (String) request.getParameter("bumenId");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		User user = new User();
		try {

			if (StringUtil.isNotEmpty(userXingming)) {
				user.setUserXingming(userXingming);
			}
			if (StringUtil.isNotEmpty(userName)) {
				user.setUserName(userName);
			}
			if (StringUtil.isNotEmpty(userId)) {
				user.setUserId(Integer.parseInt(userId));
			}
			if (StringUtil.isNotEmpty(userType1)) {
				user.setUserType1(Integer.parseInt(userType1));
			}
			if (StringUtil.isNotEmpty(userType2)) {
				user.setUserType2(Integer.parseInt(userType2));
			}
			if (StringUtil.isNotEmpty(roleId)) {
				user.setRoleId(Integer.parseInt(roleId));
			}
			if (StringUtil.isNotEmpty(userSex)) {
				user.setUserSex(Integer.parseInt(userSex));
			}
			if (StringUtil.isNotEmpty(bumenId)) {
				user.setBumenId(Integer.parseInt(bumenId));
			}
			JSONArray jsonArray = JSONArray.fromObject(userService.queryUsers(
					user, null, pageBean.getStart(), pageBean.getRows(), sdate, edate));
			JSONObject result = new JSONObject();
			int total = userService.queryUsers(user, null, 0, 0, sdate, edate).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addUser")
	public void addUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userName = (String) request.getParameter("userName");
		String userPassword = (String) request.getParameter("userPassword");
		String userAge = (String) request.getParameter("userAge");
		String userXingming = (String) request.getParameter("userXingming");
		String userSex = (String) request.getParameter("userSex");
		String userPhone = (String) request.getParameter("userPhone");
		String userMark1 = (String) request.getParameter("userMark1");
		String userMark2 = (String) request.getParameter("userMark2");
		String userMark3 = (String) request.getParameter("userMark3");
		String userMark4 = (String) request.getParameter("userMark4");
		String userDate1 = (String) request.getParameter("userDate1");
		String userDate2 = (String) request.getParameter("userDate2");
		String userType1 = (String) request.getParameter("userType1");
		String userType2 = (String) request.getParameter("userType2");
		String roleId = (String) request.getParameter("roleId");
		String bumenId = (String) request.getParameter("bumenId");
		String userId = (String) request.getParameter("userId");
		User user = new User();

		if (StringUtil.isNotEmpty(userId)) {
			user = userService.getUser(Integer.parseInt(userId));
		}
		if (StringUtil.isNotEmpty(userName)) {
			user.setUserName(userName);
		}
		if (StringUtil.isNotEmpty(userPassword)) {
			user.setUserPassword(userPassword);
		}
		if (StringUtil.isNotEmpty(userAge)) {
			user.setUserAge(Integer.parseInt(userAge));
		}
		if (StringUtil.isNotEmpty(userXingming)) {
			user.setUserXingming(userXingming);
		}
		if (StringUtil.isNotEmpty(userSex)) {
			user.setUserSex(Integer.parseInt(userSex));
		}
		if (StringUtil.isNotEmpty(userPhone)) {
			user.setUserPhone(userPhone);
		}
		if (StringUtil.isNotEmpty(userMark1)) {
			user.setUserMark1(userMark1);
		}
		if (StringUtil.isNotEmpty(userMark2)) {
			user.setUserMark2(userMark2);
		}
		if (StringUtil.isNotEmpty(userMark3)) {
			user.setUserMark3(userMark3);
		}
		if (StringUtil.isNotEmpty(userMark4)) {
			user.setUserMark4(userMark4);
		}
		if (StringUtil.isNotEmpty(userDate1)) {
			user.setUserDate1(DateUtil.formatString(userDate1,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(userDate2)) {
			user.setUserDate2(DateUtil.formatString(userDate2,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(userType1)) {
			user.setUserType1(Integer.parseInt(userType1));
		}
		if (StringUtil.isNotEmpty(userType2)) {
			user.setUserType2(Integer.parseInt(userType2));
		}
		if (StringUtil.isNotEmpty(roleId)) {
			user.setRoleId(Integer.parseInt(roleId));
			Role role = new Role();
			role = roleService.getRole(Integer.parseInt(roleId));
			user.setRoleName(role.getRoleName());
		}
		if (StringUtil.isNotEmpty(bumenId)) {
			user.setBumenId(Integer.parseInt(bumenId));
			Bumen bumen = new Bumen();
			bumen = bumenService.getBumen(Integer.parseInt(bumenId));
			user.setBumenName(bumen.getBumenName());
		}
		try {
			if (StringUtil.isNotEmpty(userId)) {
				userService.modifyUser(user);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			} else {
				int total = userService.queryUsers(null, userName, 0, 0, null, null).size();
				if (total==0) {
					Date date = new Date();
					user.setUserDate1(date);
					user.setUserType1(0);
					userService.save(user);
					result.put("success", "true");
					ResponseUtil.write(response, result);
				} else {
					result.put("success", "true");
					result.put("errorMsg", "学号重复！");
					ResponseUtil.write(response, result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/mimaUser")
	public void mimaUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userId = (String) request.getParameter("userId");
		String userPassword = (String) request.getParameter("userPassword");
		String userPassword1 = (String) request.getParameter("userPassword1");
		User user = new User();
		try {
			user = userService.getUser(Integer.parseInt(userId));
			if (!user.getUserPassword().equals(userPassword)) {
				request.setAttribute("error", "原密码错误，请重新输入！");
				request.getRequestDispatcher("usermima.jsp").forward(request,
						response);
			}else{
				user.setUserPassword(userPassword1);
				userService.modifyUser(user);
				request.setAttribute("error", "密码修改成功！");
				request.getRequestDispatcher("usermima.jsp").forward(request,
						response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/zhuceUser")
	public void zhuceUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userName = (String) request.getParameter("userName");
		String userPassword = (String) request.getParameter("userPassword");
		String userAge = (String) request.getParameter("userAge");
		String userXingming = (String) request.getParameter("userXingming");
		String userSex = (String) request.getParameter("userSex");
		String userPhone = (String) request.getParameter("userPhone");
		String userMark1 = (String) request.getParameter("userMark1");
		String userMark2 = (String) request.getParameter("userMark2");
		String userMark3 = (String) request.getParameter("userMark3");
		String userMark4 = (String) request.getParameter("userMark4");
		String userDate1 = (String) request.getParameter("userDate1");
		String userDate2 = (String) request.getParameter("userDate2");
		String userType1 = (String) request.getParameter("userType1");
		String userType2 = (String) request.getParameter("userType2");
		String roleId = (String) request.getParameter("roleId");
		String bumenId = (String) request.getParameter("bumenId");
		String userId = (String) request.getParameter("userId");
		User user = new User();

		if (StringUtil.isNotEmpty(userId)) {
			user = userService.getUser(Integer.parseInt(userId));
		}
		if (StringUtil.isNotEmpty(userName)) {
			user.setUserName(userName);
		}
		if (StringUtil.isNotEmpty(userPassword)) {
			user.setUserPassword(userPassword);
		}
		if (StringUtil.isNotEmpty(userAge)) {
			user.setUserAge(Integer.parseInt(userAge));
		}
		if (StringUtil.isNotEmpty(userXingming)) {
			user.setUserXingming(userXingming);
		}
		if (StringUtil.isNotEmpty(userSex)) {
			user.setUserSex(Integer.parseInt(userSex));
		}
		if (StringUtil.isNotEmpty(userPhone)) {
			user.setUserPhone(userPhone);
		}
		if (StringUtil.isNotEmpty(userMark1)) {
			user.setUserMark1(userMark1);
		}
		if (StringUtil.isNotEmpty(userMark2)) {
			user.setUserMark2(userMark2);
		}
		if (StringUtil.isNotEmpty(userMark3)) {
			user.setUserMark3(userMark3);
		}
		if (StringUtil.isNotEmpty(userMark4)) {
			user.setUserMark4(userMark4);
		}
		if (StringUtil.isNotEmpty(userDate1)) {
			user.setUserDate1(DateUtil.formatString(userDate1,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(userDate2)) {
			user.setUserDate2(DateUtil.formatString(userDate2,
					"yyyy-MM-dd hh:mm:ss"));
		}
		if (StringUtil.isNotEmpty(userType1)) {
			user.setUserType1(Integer.parseInt(userType1));
		}
		if (StringUtil.isNotEmpty(userType2)) {
			user.setUserType2(Integer.parseInt(userType2));
		}
		if (StringUtil.isNotEmpty(roleId)) {
			user.setRoleId(Integer.parseInt(roleId));
			Role role = new Role();
			role = roleService.getRole(Integer.parseInt(roleId));
			user.setRoleName(role.getRoleName());
		}
		if (StringUtil.isNotEmpty(bumenId)) {
			user.setBumenId(Integer.parseInt(bumenId));
			Bumen bumen = new Bumen();
			bumen = bumenService.getBumen(Integer.parseInt(bumenId));
			user.setBumenName(bumen.getBumenName());
		}
		try {
			if (StringUtil.isNotEmpty(userId)) {
				userService.modifyUser(user);
				result.put("success", "true");
				ResponseUtil.write(response, result);
			} else {
				int total = userService.queryUsers(null, userName, 0, 0, null, null).size();
				if (total==0) {
					Date date = new Date();
					user.setUserDate1(date);
					user.setUserType1(0);
					userService.save(user);
					request.setAttribute("error", "注册成功，请登录！");
					request.getRequestDispatcher("index.jsp").forward(request,
							response);
				} else {
					request.setAttribute("error", "学号重复，请重新输入！");
					request.getRequestDispatcher("zhuceuser.jsp").forward(request,
							response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteUser")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		String delIds = (String) request.getParameter("delIds");
		try {
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				userService.deleteUser(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/userComboList")
	public void userComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userSex = (String) request.getParameter("userSex");
		String userType1 = (String) request.getParameter("userType1");
		String userType2 = (String) request.getParameter("userType2");
		String roleId = (String) request.getParameter("roleId");
		String bumenId = (String) request.getParameter("bumenId");
		User user = new User();

		if (StringUtil.isNotEmpty(userSex)) {
			user.setUserSex(Integer.parseInt(userSex));
		}
		if (StringUtil.isNotEmpty(userType1)) {
			user.setUserType1(Integer.parseInt(userType1));
		}
		if (StringUtil.isNotEmpty(userType2)) {
			user.setUserType2(Integer.parseInt(userType2));
		}
		if (StringUtil.isNotEmpty(roleId)) {
			user.setRoleId(Integer.parseInt(roleId));
		}
		if (StringUtil.isNotEmpty(bumenId)) {
			user.setBumenId(Integer.parseInt(bumenId));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("userName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(userService.queryUsers(user, null, 0, 0, null, null)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/userTongji")
	public void userTongji(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sdate=request.getParameter("sdate");
		String edate=request.getParameter("edate");
		String roleId=request.getParameter("roleId");
		List<Integer> bumenIds = new ArrayList<Integer>();
		List<String> bumenNames = new ArrayList<String>();
		List<Integer> userZongshus = new ArrayList<Integer>();
		List<Bumen> bumens = new ArrayList<Bumen>();
		List<User> users = new ArrayList<User>();
		User user = new User();
		if (StringUtil.isNotEmpty(roleId)) {
			user.setRoleId(Integer.parseInt(roleId));
		}
		Integer zongshu = 0;
		try {
			bumens = bumenService.queryBumens(null, 0,0);
			for(int i=0;i<bumens.size();i++){
				bumenIds.add(bumens.get(i).getBumenId());
				bumenNames.add(bumens.get(i).getBumenName());
			}
			for(int i=0;i<bumenIds.size();i++){
				Integer userZongshu = 0;
				user.setBumenId(bumenIds.get(i));
				users = userService.queryUsers(user, null, 0, 0, sdate, edate);
				for(int j=0;j<users.size();j++){
					userZongshu = userZongshu + users.size();
				}
				zongshu = zongshu + userZongshu;
				userZongshus.add(userZongshu);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("bumenNames", bumenNames);
			session.setAttribute("userZongshus", userZongshus);
			session.setAttribute("zongshu", zongshu);
			response.sendRedirect("admin/usertongji.jsp");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/shangchuanUser")
	public void shangchuanUser(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
			throws Exception {
		try {
			String userId = (String) request.getParameter("userId");
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
			User user = userService.getUser(Integer.parseInt(userId));
			user.setUserImg(shangchuandizhi);
			user.setUserImgName(shangchuanname);
			userService.modifyUser(user);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/xiazaiUser")
	public void xiazaiUser(HttpServletRequest request, HttpServletResponse response)
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

	@RequestMapping("/daoruUser")
	public void daoruUser(HttpServletRequest request, HttpServletResponse response,MultipartFile uploadFile)
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
				User user = new User();
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
						user.setUserName(cellValue);
						break;
					case 2:
						user.setUserPassword(cellValue);
						break;
					case 3:
						user.setUserXingming(cellValue);
						break;
					case 4:
						user.setUserAge(Integer.parseInt(cellValue));
						break;
					case 5:
						user.setUserSex(Integer.parseInt(cellValue));
						break;
					case 6:
						user.setUserPhone(cellValue);
						break;
					case 7:
						user.setUserMark1(cellValue);
						break;
					case 8:
						user.setUserMark2(cellValue);
						break;
					case 9:
						user.setUserMark3(cellValue);
						break;
					case 10:
						user.setUserMark4(cellValue);
						break;
					case 11:
						user.setUserType1(Integer.parseInt(cellValue));
						break;
					case 12:
						user.setUserType2(Integer.parseInt(cellValue));
						break;
					}
				}
				userService.save(user);
			}
			JSONObject result = new JSONObject();
			result.put("success", "true");
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/daochuUser")
	public void daochuUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String delIds = (String) request.getParameter("delIds");
		JSONObject result = new JSONObject();
		String str[] = delIds.split(",");

		// 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("users记录");
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
			User user = userService.getUser(Integer.parseInt(str[i]));

			// 创建单元格，并设置值
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(user.getUserId());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(1);
			cell.setCellValue(user.getUserName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(2);
			cell.setCellValue(user.getUserPassword());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(3);
			cell.setCellValue(user.getUserXingming());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(4);
			if (user.getUserSex().intValue() == 0) {
				cell.setCellValue("男");
				cell.setCellStyle(cellStyle);
			} else {
				cell.setCellValue("女");
				cell.setCellStyle(cellStyle);
			}

			cell = hssfRow.createCell(5);
			cell.setCellValue(user.getUserAge());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(6);
			cell.setCellValue(user.getUserPhone());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(7);
			cell.setCellValue(user.getUserMark1());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(8);
			cell.setCellValue(user.getUserMark2());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(9);
			cell.setCellValue(user.getUserMark3());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(10);
			cell.setCellValue(user.getUserMark4());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(13);
			cell.setCellValue(user.getUserType1());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(14);
			cell.setCellValue(user.getUserType2());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(15);
			cell.setCellValue(user.getRoleName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(16);
			cell.setCellValue(user.getBumenName());
			cell.setCellStyle(cellStyle);
		}

		// 保存Excel文件
		try {
			Date date = new Date();
			String strdate = DateUtil.formatDate(date, "yyyyMMddhhmmss");
			OutputStream outputStream = new FileOutputStream("D:/user"
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
