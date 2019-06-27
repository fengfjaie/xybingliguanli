package com.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.mapper.*;
import com.model.*;
@Repository
public class SpjinDao extends SqlSessionDaoSupport{
	@Autowired
	private SpjinMapper spjinMapper;

	public List getSpjinList(Spjin record,int page,int rows,String sdate, String edate) {
		List<Spjin> list = spjinMapper.selectAll(record,page,rows,sdate,edate);
		return list;
	}
	
	public Spjin getSpjinById(int id){
		Spjin spjin = spjinMapper.selectByPrimaryKey(id);
		
		return spjin;
	}

	public void update(Spjin spjin) {
		spjinMapper.updateByPrimaryKey(spjin);

	}

	public void delete(Integer id) {
		spjinMapper.deleteByPrimaryKey(id);
	}

	public void add(Spjin spjin) {
		spjinMapper.insert(spjin);
		
	}
}
