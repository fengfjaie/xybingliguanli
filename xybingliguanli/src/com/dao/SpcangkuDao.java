package com.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.mapper.*;
import com.model.*;
@Repository
public class SpcangkuDao extends SqlSessionDaoSupport{
	@Autowired
	private SpcangkuMapper spcangkuMapper;

	public List getSpcangkuList(Spcangku record,int page,int rows,String sdate, String edate) {
		List<Spcangku> list = spcangkuMapper.selectAll(record,page,rows,sdate, edate);
		return list;
	}
	
	public Spcangku getSpcangkuById(int id){
		Spcangku spcangku = spcangkuMapper.selectByPrimaryKey(id);
		
		return spcangku;
	}

	public void update(Spcangku spcangku) {
		spcangkuMapper.updateByPrimaryKey(spcangku);

	}

	public void delete(Integer id) {
		spcangkuMapper.deleteByPrimaryKey(id);
	}

	public void add(Spcangku spcangku) {
		spcangkuMapper.insert(spcangku);
		
	}
}
