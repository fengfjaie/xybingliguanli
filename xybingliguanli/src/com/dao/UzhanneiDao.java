package com.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.mapper.*;
import com.model.*;
@Repository
public class UzhanneiDao extends SqlSessionDaoSupport{
	@Autowired
	private UzhanneiMapper uzhanneiMapper;

	public List getUzhanneiList(Uzhannei record,int page,int rows,String sdate, String edate) {
		List<Uzhannei> list = uzhanneiMapper.selectAll(record,page,rows,sdate,edate);
		return list;
	}
	
	public Uzhannei getUzhanneiById(int id){
		Uzhannei uzhannei = uzhanneiMapper.selectByPrimaryKey(id);
		
		return uzhannei;
	}

	public void update(Uzhannei uzhannei) {
		uzhanneiMapper.updateByPrimaryKey(uzhannei);

	}

	public void delete(Integer id) {
		uzhanneiMapper.deleteByPrimaryKey(id);
	}

	public void add(Uzhannei uzhannei) {
		uzhanneiMapper.insert(uzhannei);
		
	}
}
