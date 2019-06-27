package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.*;
import com.model.*;

@Service
public class SptypeService {
	@Autowired
	private SptypeDao sptypeDao;

	public List querySptypes(Sptype record,int page,int rows) {
		// TODO Auto-generated method stub
		return sptypeDao.getSptypeList(record,page,rows);
	}

	public Sptype getSptype(int parseInt) {
		// TODO Auto-generated method stub
		return sptypeDao.getSptypeById(parseInt);
	}

	public void modifySptype(Sptype sptype) {
		// TODO Auto-generated method stub
		sptypeDao.update(sptype);
	}

	public void deleteSptype(Integer id) {
		// TODO Auto-generated method stub
		sptypeDao.delete(id);

	}

	public void save(Sptype sptype) {
		// TODO Auto-generated method stub
		sptypeDao.add(sptype);

	}

}
