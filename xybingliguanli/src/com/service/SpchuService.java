package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.*;
import com.model.*;

@Service
public class SpchuService {
	@Autowired
	private SpchuDao spchuDao;

	public List querySpchus(Spchu record,int page,int rows, String sdate, String edate) {
		// TODO Auto-generated method stub
		return spchuDao.getSpchuList(record,page,rows,sdate,edate);
	}

	public Spchu getSpchu(int parseInt) {
		// TODO Auto-generated method stub
		return spchuDao.getSpchuById(parseInt);
	}

	public void modifySpchu(Spchu spchu) {
		// TODO Auto-generated method stub
		spchuDao.update(spchu);
	}

	public void deleteSpchu(Integer id) {
		// TODO Auto-generated method stub
		spchuDao.delete(id);

	}

	public void save(Spchu spchu) {
		// TODO Auto-generated method stub
		spchuDao.add(spchu);

	}

}
