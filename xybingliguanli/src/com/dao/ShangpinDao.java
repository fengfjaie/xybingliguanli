package com.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.mapper.*;
import com.model.*;
@Repository
public class ShangpinDao extends SqlSessionDaoSupport{
	@Autowired
	private ShangpinMapper shangpinMapper;

	public List getShangpinList(Shangpin record,int page,int rows,String sdate, String edate) {
		List<Shangpin> list = shangpinMapper.selectAll(record,page,rows,sdate,edate);
		return list;
	}
	
	public Shangpin getShangpinById(int id){
		Shangpin shangpin = shangpinMapper.selectByPrimaryKey(id);
		
		return shangpin;
	}

	public void update(Shangpin shangpin) {
		shangpinMapper.updateByPrimaryKey(shangpin);

	}

	public void delete(Integer id) {
		shangpinMapper.deleteByPrimaryKey(id);
	}

	public void add(Shangpin shangpin) {
		shangpinMapper.insert(shangpin);
		
	}
}
