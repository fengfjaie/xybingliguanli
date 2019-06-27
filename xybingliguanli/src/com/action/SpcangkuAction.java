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
public class SpcangkuAction {
	@Autowired
	private SpcangkuService spcangkuService;

	@RequestMapping("/getSpcangkus")
	public void getSpcangkus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String spcangkuName = (String) request.getParameter("spcangkuName");
		String spcangkuId = (String) request.getParameter("spcangkuId");
		String spcangkuType = (String) request.getParameter("spcangkuType");
		String spcangkuType1 = (String) request.getParameter("spcangkuType1");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Spcangku spcangku = new Spcangku();
		try {
			if (StringUtil.isNotEmpty(spcangkuName)) {
				spcangku.setSpcangkuName(spcangkuName);
			}
			if (StringUtil.isNotEmpty(spcangkuId)) {
				spcangku.setSpcangkuId(Integer.parseInt(spcangkuId));
			}
			if (StringUtil.isNotEmpty(spcangkuType)) {
				spcangku.setSpcangkuType(Integer.parseInt(spcangkuType));
			}
			if (StringUtil.isNotEmpty(spcangkuType1)) {
				spcangku.setSpcangkuType1(Integer.parseInt(spcangkuType1));
			}
			JSONArray jsonArray = JSONArray.fromObject(spcangkuService.querySpcangkus(spcangku, pageBean.getStart(), pageBean.getRows(), sdate, edate));
			JSONObject result = new JSONObject();
			int total = spcangkuService.querySpcangkus(spcangku, 0, 0, sdate, edate).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addSpcangku")
	public void addSpcangku(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			JSONObject result = new JSONObject();
			
			String spcangkuName = (String) request.getParameter("spcangkuName");
			String spcangkuMark = (String) request.getParameter("spcangkuMark");
			String spcangkuMark1 = (String) request.getParameter("spcangkuMark1");
			String spcangkuMark2 = (String) request.getParameter("spcangkuMark2");
			String spcangkuPhone = (String) request.getParameter("spcangkuPhone");
			String spcangkuDizhi = (String) request.getParameter("spcangkuDizhi");
			String spcangkuDate = (String) request.getParameter("spcangkuDate");
			String spcangkuDate1 = (String) request.getParameter("spcangkuDate1");
			String spcangkuType = (String) request.getParameter("spcangkuType");
			String spcangkuType1 = (String) request.getParameter("spcangkuType1");
			String spcangkuId = (String) request.getParameter("spcangkuId");
			Spcangku spcangku = new Spcangku();
			
			if (StringUtil.isNotEmpty(spcangkuId)) {
				spcangku = spcangkuService.getSpcangku(Integer.parseInt(spcangkuId));
			}
			if (StringUtil.isNotEmpty(spcangkuName)) {
				spcangku.setSpcangkuName(spcangkuName);
			}
			if (StringUtil.isNotEmpty(spcangkuMark)) {
				spcangku.setSpcangkuMark(spcangkuMark);
			}
			if (StringUtil.isNotEmpty(spcangkuMark1)) {
				spcangku.setSpcangkuMark1(spcangkuMark1);
			}
			if (StringUtil.isNotEmpty(spcangkuMark2)) {
				spcangku.setSpcangkuMark2(spcangkuMark2);
			}
			if (StringUtil.isNotEmpty(spcangkuPhone)) {
				spcangku.setSpcangkuPhone(spcangkuPhone);
			}
			if (StringUtil.isNotEmpty(spcangkuDizhi)) {
				spcangku.setSpcangkuDizhi(spcangkuDizhi);
			}
			if (StringUtil.isNotEmpty(spcangkuDate)) {
				spcangku.setSpcangkuDate(DateUtil.formatString(spcangkuDate,
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (StringUtil.isNotEmpty(spcangkuDate1)) {
				spcangku.setSpcangkuDate1(DateUtil.formatString(spcangkuDate1,
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (StringUtil.isNotEmpty(spcangkuType)) {
				spcangku.setSpcangkuType(Integer.parseInt(spcangkuType));
			}
			if (StringUtil.isNotEmpty(spcangkuType1)) {
				spcangku.setSpcangkuType1(Integer.parseInt(spcangkuType1));
			}

			if (StringUtil.isNotEmpty(spcangkuId)) {
				spcangkuService.modifySpcangku(spcangku);
			} else {
				Date date = new Date();
				spcangku.setSpcangkuDate(date);
				spcangku.setSpcangkuType(0);
				spcangkuService.save(spcangku);
			}
			result.put("success", "true");

			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteSpcangku")
	public void deleteSpcangku(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String delIds = (String) request.getParameter("delIds");
			System.out.println("delIds = " + delIds);
			JSONObject result = new JSONObject();
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				spcangkuService.deleteSpcangku(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/spcangkuComboList")
	public void spcangkuComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String spcangkuType = (String) request.getParameter("spcangkuType");
		String spcangkuType1 = (String) request.getParameter("spcangkuType1");
		Spcangku spcangku = new Spcangku();
		if (StringUtil.isNotEmpty(spcangkuType)) {
			spcangku.setSpcangkuType(Integer.parseInt(spcangkuType));
		}
		if (StringUtil.isNotEmpty(spcangkuType1)) {
			spcangku.setSpcangkuType1(Integer.parseInt(spcangkuType1));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("spcangkuName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(spcangkuService.querySpcangkus(spcangku, 0, 0, null, null)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
