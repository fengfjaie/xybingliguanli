package com.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.mapper.*;
import com.model.*;
@Repository
public class YhbumenDao extends SqlSessionDaoSupport{
	@Autowired
	private YhbumenMapper yhbumenMapper;

	public List getYhbumenList(Yhbumen record,int page,int rows) {
		List<Yhbumen> list = yhbumenMapper.selectAll(record,page,rows);
		return list;
	}
	
	public Yhbumen getYhbumenById(int id){
		Yhbumen yhbumen = yhbumenMapper.selectByPrimaryKey(id);
		
		return yhbumen;
	}

	public void update(Yhbumen yhbumen) {
		yhbumenMapper.updateByPrimaryKey(yhbumen);

	}

	public void delete(Integer id) {
		yhbumenMapper.deleteByPrimaryKey(id);
	}

	public void add(Yhbumen yhbumen) {
		yhbumenMapper.insert(yhbumen);
		
	}
}
