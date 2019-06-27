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
public class BumenAction {
	@Autowired
	private BumenService bumenService;

	@RequestMapping("/getBumens")
	public void getBumens(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String bumenName = (String) request.getParameter("bumenName");
		String bumenId = (String) request.getParameter("bumenId");
		String bumenMark2 = (String) request.getParameter("bumenMark2");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Bumen bumen = new Bumen();
		try {
			if (StringUtil.isNotEmpty(bumenName)) {
				bumen.setBumenName(bumenName);
			}
			if (StringUtil.isNotEmpty(bumenId)) {
				bumen.setBumenId(Integer.parseInt(bumenId));
			}
			if (StringUtil.isNotEmpty(bumenMark2)) {
				bumen.setBumenMark2(Integer.parseInt(bumenMark2));
			}
			JSONArray jsonArray = JSONArray.fromObject(bumenService.queryBumens(bumen, pageBean.getStart(), pageBean.getRows()));
			JSONObject result = new JSONObject();
			int total = bumenService.queryBumens(bumen, 0, 0).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addBumen")
	public void addBumen(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			JSONObject result = new JSONObject();
			
			String bumenName = (String) request.getParameter("bumenName");
			String bumenMark = (String) request.getParameter("bumenMark");
			String bumenMark1 = (String) request.getParameter("bumenMark1");
			String bumenMark2 = (String) request.getParameter("bumenMark2");
			String bumenId = (String) request.getParameter("bumenId");
			Bumen bumen = new Bumen();
			
			if (StringUtil.isNotEmpty(bumenId)) {
				bumen = bumenService.getBumen(Integer.parseInt(bumenId));
			}
			if (StringUtil.isNotEmpty(bumenName)) {
				bumen.setBumenName(bumenName);
			}
			if (StringUtil.isNotEmpty(bumenMark)) {
				bumen.setBumenMark(bumenMark);
			}
			if (StringUtil.isNotEmpty(bumenMark1)) {
				bumen.setBumenMark1(bumenMark1);
			}
			if (StringUtil.isNotEmpty(bumenMark2)) {
				bumen.setBumenMark2(Integer.parseInt(bumenMark2));
			}

			if (StringUtil.isNotEmpty(bumenId)) {
				bumenService.modifyBumen(bumen);
			} else {
				bumenService.save(bumen);
			}
			result.put("success", "true");

			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteBumen")
	public void deleteBumen(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String delIds = (String) request.getParameter("delIds");
			System.out.println("delIds = " + delIds);
			JSONObject result = new JSONObject();
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				bumenService.deleteBumen(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/bumenComboList")
	public void bumenComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String bumenMark2 = (String) request.getParameter("bumenMark2");
		Bumen bumen = new Bumen();
		if (StringUtil.isNotEmpty(bumenMark2)) {
			bumen.setBumenMark2(Integer.parseInt(bumenMark2));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("bumenName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(bumenService.queryBumens(bumen, 0, 0)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
