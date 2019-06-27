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
public class SptypeAction {
	@Autowired
	private SptypeService sptypeService;

	@RequestMapping("/getSptypes")
	public void getSptypes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String sptypeName = (String) request.getParameter("sptypeName");
		String sptypeId = (String) request.getParameter("sptypeId");
		String sptypeMark2 = (String) request.getParameter("sptypeMark2");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Sptype sptype = new Sptype();
		try {
			if (StringUtil.isNotEmpty(sptypeName)) {
				sptype.setSptypeName(sptypeName);
			}
			if (StringUtil.isNotEmpty(sptypeId)) {
				sptype.setSptypeId(Integer.parseInt(sptypeId));
			}
			if (StringUtil.isNotEmpty(sptypeMark2)) {
				sptype.setSptypeMark2(Integer.parseInt(sptypeMark2));
			}
			JSONArray jsonArray = JSONArray.fromObject(sptypeService.querySptypes(sptype, pageBean.getStart(), pageBean.getRows()));
			JSONObject result = new JSONObject();
			int total = sptypeService.querySptypes(sptype, 0, 0).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addSptype")
	public void addSptype(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			JSONObject result = new JSONObject();
			
			String sptypeName = (String) request.getParameter("sptypeName");
			String sptypeMark = (String) request.getParameter("sptypeMark");
			String sptypeMark1 = (String) request.getParameter("sptypeMark1");
			String sptypeMark2 = (String) request.getParameter("sptypeMark2");
			String sptypeId = (String) request.getParameter("sptypeId");
			Sptype sptype = new Sptype();
			
			if (StringUtil.isNotEmpty(sptypeId)) {
				sptype = sptypeService.getSptype(Integer.parseInt(sptypeId));
			}
			if (StringUtil.isNotEmpty(sptypeName)) {
				sptype.setSptypeName(sptypeName);
			}
			if (StringUtil.isNotEmpty(sptypeMark)) {
				sptype.setSptypeMark(sptypeMark);
			}
			if (StringUtil.isNotEmpty(sptypeMark1)) {
				sptype.setSptypeMark1(sptypeMark1);
			}
			if (StringUtil.isNotEmpty(sptypeMark2)) {
				sptype.setSptypeMark2(Integer.parseInt(sptypeMark2));
			}

			if (StringUtil.isNotEmpty(sptypeId)) {
				sptypeService.modifySptype(sptype);
			} else {
				sptypeService.save(sptype);
			}
			result.put("success", "true");

			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteSptype")
	public void deleteSptype(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String delIds = (String) request.getParameter("delIds");
			System.out.println("delIds = " + delIds);
			JSONObject result = new JSONObject();
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				sptypeService.deleteSptype(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/sptypeComboList")
	public void sptypeComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sptypeMark2 = (String) request.getParameter("sptypeMark2");
		Sptype sptype = new Sptype();
		if (StringUtil.isNotEmpty(sptypeMark2)) {
			sptype.setSptypeMark2(Integer.parseInt(sptypeMark2));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("sptypeName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(sptypeService.querySptypes(sptype, 0, 0)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
