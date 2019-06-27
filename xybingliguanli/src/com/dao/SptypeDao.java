package com.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.mapper.*;
import com.model.*;
@Repository
public class SptypeDao extends SqlSessionDaoSupport{
	@Autowired
	private SptypeMapper sptypeMapper;

	public List getSptypeList(Sptype record,int page,int rows) {
		List<Sptype> list = sptypeMapper.selectAll(record,page,rows);
		return list;
	}
	
	public Sptype getSptypeById(int id){
		Sptype sptype = sptypeMapper.selectByPrimaryKey(id);
		
		return sptype;
	}

	public void update(Sptype sptype) {
		sptypeMapper.updateByPrimaryKey(sptype);

	}

	public void delete(Integer id) {
		sptypeMapper.deleteByPrimaryKey(id);
	}

	public void add(Sptype sptype) {
		sptypeMapper.insert(sptype);
		
	}
}
