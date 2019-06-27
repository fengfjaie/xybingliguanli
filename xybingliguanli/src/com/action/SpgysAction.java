package com.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.model.*;
import com.service.*;
import com.util.*;

@Controller
public class SpgysAction {
	@Autowired
	private SpgysService spgysService;

	@RequestMapping("/getSpgyss")
	public void getSpgyss(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String spgysName = (String) request.getParameter("spgysName");
		String spgysId = (String) request.getParameter("spgysId");
		String spgysType = (String) request.getParameter("spgysType");
		String spgysType1 = (String) request.getParameter("spgysType1");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Spgys spgys = new Spgys();
		try {
			if (StringUtil.isNotEmpty(spgysName)) {
				spgys.setSpgysName(spgysName);
			}
			if (StringUtil.isNotEmpty(spgysId)) {
				spgys.setSpgysId(Integer.parseInt(spgysId));
			}
			if (StringUtil.isNotEmpty(spgysType)) {
				spgys.setSpgysType(Integer.parseInt(spgysType));
			}
			if (StringUtil.isNotEmpty(spgysType1)) {
				spgys.setSpgysType1(Integer.parseInt(spgysType1));
			}
			JSONArray jsonArray = JSONArray.fromObject(spgysService.querySpgyss(spgys, pageBean.getStart(), pageBean.getRows(), sdate, edate));
			JSONObject result = new JSONObject();
			int total = spgysService.querySpgyss(spgys, 0, 0, sdate, edate).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addSpgys")
	public void addSpgys(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			JSONObject result = new JSONObject();
			
			String spgysName = (String) request.getParameter("spgysName");
			String spgysMark = (String) request.getParameter("spgysMark");
			String spgysMark1 = (String) request.getParameter("spgysMark1");
			String spgysMark2 = (String) request.getParameter("spgysMark2");
			String spgysPhone = (String) request.getParameter("spgysPhone");
			String spgysDizhi = (String) request.getParameter("spgysDizhi");
			String spgysDate = (String) request.getParameter("spgysDate");
			String spgysDate1 = (String) request.getParameter("spgysDate1");
			String spgysType = (String) request.getParameter("spgysType");
			String spgysType1 = (String) request.getParameter("spgysType1");
			String spgysId = (String) request.getParameter("spgysId");
			Spgys spgys = new Spgys();
			
			if (StringUtil.isNotEmpty(spgysId)) {
				spgys = spgysService.getSpgys(Integer.parseInt(spgysId));
			}
			if (StringUtil.isNotEmpty(spgysName)) {
				spgys.setSpgysName(spgysName);
			}
			if (StringUtil.isNotEmpty(spgysMark)) {
				spgys.setSpgysMark(spgysMark);
			}
			if (StringUtil.isNotEmpty(spgysMark1)) {
				spgys.setSpgysMark1(spgysMark1);
			}
			if (StringUtil.isNotEmpty(spgysMark2)) {
				spgys.setSpgysMark2(spgysMark2);
			}
			if (StringUtil.isNotEmpty(spgysPhone)) {
				spgys.setSpgysPhone(spgysPhone);
			}
			if (StringUtil.isNotEmpty(spgysDizhi)) {
				spgys.setSpgysDizhi(spgysDizhi);
			}
			if (StringUtil.isNotEmpty(spgysDate)) {
				spgys.setSpgysDate(DateUtil.formatString(spgysDate,
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (StringUtil.isNotEmpty(spgysDate1)) {
				spgys.setSpgysDate1(DateUtil.formatString(spgysDate1,
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (StringUtil.isNotEmpty(spgysType)) {
				spgys.setSpgysType(Integer.parseInt(spgysType));
			}
			if (StringUtil.isNotEmpty(spgysType1)) {
				spgys.setSpgysType1(Integer.parseInt(spgysType1));
			}

			if (StringUtil.isNotEmpty(spgysId)) {
				spgysService.modifySpgys(spgys);
			} else {
				Date date = new Date();
				spgys.setSpgysDate(date);
				spgys.setSpgysType(0);
				spgysService.save(spgys);
			}
			result.put("success", "true");

			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteSpgys")
	public void deleteSpgys(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String delIds = (String) request.getParameter("delIds");
			System.out.println("delIds = " + delIds);
			JSONObject result = new JSONObject();
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				spgysService.deleteSpgys(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/spgysComboList")
	public void spgysComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String spgysType = (String) request.getParameter("spgysType");
		String spgysType1 = (String) request.getParameter("spgysType1");
		Spgys spgys = new Spgys();
		if (StringUtil.isNotEmpty(spgysType)) {
			spgys.setSpgysType(Integer.parseInt(spgysType));
		}
		if (StringUtil.isNotEmpty(spgysType1)) {
			spgys.setSpgysType1(Integer.parseInt(spgysType1));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("spgysName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(spgysService.querySpgyss(spgys, 0, 0, null, null)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
