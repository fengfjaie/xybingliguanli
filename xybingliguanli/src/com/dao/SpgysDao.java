package com.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.mapper.*;
import com.model.*;
@Repository
public class SpgysDao extends SqlSessionDaoSupport{
	@Autowired
	private SpgysMapper spgysMapper;

	public List getSpgysList(Spgys record,int page,int rows,String sdate, String edate) {
		List<Spgys> list = spgysMapper.selectAll(record,page,rows,sdate, edate);
		return list;
	}
	
	public Spgys getSpgysById(int id){
		Spgys spgys = spgysMapper.selectByPrimaryKey(id);
		
		return spgys;
	}

	public void update(Spgys spgys) {
		spgysMapper.updateByPrimaryKey(spgys);

	}

	public void delete(Integer id) {
		spgysMapper.deleteByPrimaryKey(id);
	}

	public void add(Spgys spgys) {
		spgysMapper.insert(spgys);
		
	}
}
