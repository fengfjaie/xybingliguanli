package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.*;
import com.model.*;

@Service
public class SpjinService {
	@Autowired
	private SpjinDao spjinDao;

	public List querySpjins(Spjin record,int page,int rows, String sdate, String edate) {
		// TODO Auto-generated method stub
		return spjinDao.getSpjinList(record,page,rows,sdate,edate);
	}

	public Spjin getSpjin(int parseInt) {
		// TODO Auto-generated method stub
		return spjinDao.getSpjinById(parseInt);
	}

	public void modifySpjin(Spjin spjin) {
		// TODO Auto-generated method stub
		spjinDao.update(spjin);
	}

	public void deleteSpjin(Integer id) {
		// TODO Auto-generated method stub
		spjinDao.delete(id);

	}

	public void save(Spjin spjin) {
		// TODO Auto-generated method stub
		spjinDao.add(spjin);

	}

}
