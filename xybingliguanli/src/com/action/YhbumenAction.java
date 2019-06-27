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
public class YhbumenAction {
	@Autowired
	private YhbumenService yhbumenService;

	@RequestMapping("/getYhbumens")
	public void getYhbumens(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String page = (String) request.getParameter("page");
		String rows = (String) request.getParameter("rows");
		String yhbumenName = (String) request.getParameter("yhbumenName");
		String yhbumenId = (String) request.getParameter("yhbumenId");
		String yhbumenMark2 = (String) request.getParameter("yhbumenMark2");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Yhbumen yhbumen = new Yhbumen();
		try {
			if (StringUtil.isNotEmpty(yhbumenName)) {
				yhbumen.setYhbumenName(yhbumenName);
			}
			if (StringUtil.isNotEmpty(yhbumenId)) {
				yhbumen.setYhbumenId(Integer.parseInt(yhbumenId));
			}
			if (StringUtil.isNotEmpty(yhbumenMark2)) {
				yhbumen.setYhbumenMark2(Integer.parseInt(yhbumenMark2));
			}
			JSONArray jsonArray = JSONArray.fromObject(yhbumenService.queryYhbumens(yhbumen, pageBean.getStart(), pageBean.getRows()));
			JSONObject result = new JSONObject();
			int total = yhbumenService.queryYhbumens(yhbumen, 0, 0).size();
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addYhbumen")
	public void addYhbumen(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			JSONObject result = new JSONObject();
			
			String yhbumenName = (String) request.getParameter("yhbumenName");
			String yhbumenMark = (String) request.getParameter("yhbumenMark");
			String yhbumenMark1 = (String) request.getParameter("yhbumenMark1");
			String yhbumenMark2 = (String) request.getParameter("yhbumenMark2");
			String yhbumenId = (String) request.getParameter("yhbumenId");
			Yhbumen yhbumen = new Yhbumen();
			
			if (StringUtil.isNotEmpty(yhbumenId)) {
				yhbumen = yhbumenService.getYhbumen(Integer.parseInt(yhbumenId));
			}
			if (StringUtil.isNotEmpty(yhbumenName)) {
				yhbumen.setYhbumenName(yhbumenName);
			}
			if (StringUtil.isNotEmpty(yhbumenMark)) {
				yhbumen.setYhbumenMark(yhbumenMark);
			}
			if (StringUtil.isNotEmpty(yhbumenMark1)) {
				yhbumen.setYhbumenMark1(yhbumenMark1);
			}
			if (StringUtil.isNotEmpty(yhbumenMark2)) {
				yhbumen.setYhbumenMark2(Integer.parseInt(yhbumenMark2));
			}

			if (StringUtil.isNotEmpty(yhbumenId)) {
				yhbumenService.modifyYhbumen(yhbumen);
			} else {
				yhbumenService.save(yhbumen);
			}
			result.put("success", "true");

			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteYhbumen")
	public void deleteYhbumen(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String delIds = (String) request.getParameter("delIds");
			System.out.println("delIds = " + delIds);
			JSONObject result = new JSONObject();
			String str[] = delIds.split(",");
			for (int i = 0; i < str.length; i++) {
				yhbumenService.deleteYhbumen(Integer.parseInt(str[i]));
			}
			result.put("success", "true");
			result.put("delNums", str.length);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/yhbumenComboList")
	public void yhbumenComboList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String yhbumenMark2 = (String) request.getParameter("yhbumenMark2");
		Yhbumen yhbumen = new Yhbumen();
		if (StringUtil.isNotEmpty(yhbumenMark2)) {
			yhbumen.setYhbumenMark2(Integer.parseInt(yhbumenMark2));
		}
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("yhbumenName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JSONArray.fromObject(yhbumenService.queryYhbumens(yhbumen, 0, 0)));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
