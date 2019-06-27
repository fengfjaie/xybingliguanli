package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.*;
import com.model.*;

@Service
public class UzhanneiService {
	@Autowired
	private UzhanneiDao uzhanneiDao;

	public List queryUzhanneis(Uzhannei record,int page,int rows, String sdate, String edate) {
		// TODO Auto-generated method stub
		return uzhanneiDao.getUzhanneiList(record,page,rows,sdate,edate);
	}

	public Uzhannei getUzhannei(int parseInt) {
		// TODO Auto-generated method stub
		return uzhanneiDao.getUzhanneiById(parseInt);
	}

	public void modifyUzhannei(Uzhannei uzhannei) {
		// TODO Auto-generated method stub
		uzhanneiDao.update(uzhannei);
	}

	public void deleteUzhannei(Integer id) {
		// TODO Auto-generated method stub
		uzhanneiDao.delete(id);

	}

	public void save(Uzhannei uzhannei) {
		// TODO Auto-generated method stub
		uzhanneiDao.add(uzhannei);

	}

}
