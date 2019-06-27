package com.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.mapper.*;
import com.model.*;
@Repository
public class UtaolunDao extends SqlSessionDaoSupport{
	@Autowired
	private UtaolunMapper utaolunMapper;

	public List getUtaolunList(Utaolun record,int page,int rows,String sdate, String edate) {
		List<Utaolun> list = utaolunMapper.selectAll(record,page,rows,sdate,edate);
		return list;
	}
	
	public Utaolun getUtaolunById(int id){
		Utaolun utaolun = utaolunMapper.selectByPrimaryKey(id);
		
		return utaolun;
	}

	public void update(Utaolun utaolun) {
		utaolunMapper.updateByPrimaryKey(utaolun);

	}

	public void delete(Integer id) {
		utaolunMapper.deleteByPrimaryKey(id);
	}

	public void add(Utaolun utaolun) {
		utaolunMapper.insert(utaolun);
		
	}
}
