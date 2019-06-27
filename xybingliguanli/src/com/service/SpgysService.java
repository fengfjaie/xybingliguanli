package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.*;
import com.model.*;

@Service
public class SpgysService {
	@Autowired
	private SpgysDao spgysDao;

	public List querySpgyss(Spgys record,int page,int rows, String sdate, String edate) {
		// TODO Auto-generated method stub
		return spgysDao.getSpgysList(record,page,rows,sdate,edate);
	}

	public Spgys getSpgys(int parseInt) {
		// TODO Auto-generated method stub
		return spgysDao.getSpgysById(parseInt);
	}

	public void modifySpgys(Spgys spgys) {
		// TODO Auto-generated method stub
		spgysDao.update(spgys);
	}

	public void deleteSpgys(Integer id) {
		// TODO Auto-generated method stub
		spgysDao.delete(id);

	}

	public void save(Spgys spgys) {
		// TODO Auto-generated method stub
		spgysDao.add(spgys);

	}

}
