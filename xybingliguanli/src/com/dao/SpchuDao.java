package com.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.mapper.*;
import com.model.*;
@Repository
public class SpchuDao extends SqlSessionDaoSupport{
	@Autowired
	private SpchuMapper spchuMapper;

	public List getSpchuList(Spchu record,int page,int rows,String sdate, String edate) {
		List<Spchu> list = spchuMapper.selectAll(record,page,rows,sdate,edate);
		return list;
	}
	
	public Spchu getSpchuById(int id){
		Spchu spchu = spchuMapper.selectByPrimaryKey(id);
		
		return spchu;
	}

	public void update(Spchu spchu) {
		spchuMapper.updateByPrimaryKey(spchu);

	}

	public void delete(Integer id) {
		spchuMapper.deleteByPrimaryKey(id);
	}

	public void add(Spchu spchu) {
		spchuMapper.insert(spchu);
		
	}
}
