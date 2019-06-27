package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.*;
import com.model.*;

@Service
public class SpcangkuService {
	@Autowired
	private SpcangkuDao spcangkuDao;

	public List querySpcangkus(Spcangku record,int page,int rows, String sdate, String edate) {
		// TODO Auto-generated method stub
		return spcangkuDao.getSpcangkuList(record,page,rows,sdate,edate);
	}

	public Spcangku getSpcangku(int parseInt) {
		// TODO Auto-generated method stub
		return spcangkuDao.getSpcangkuById(parseInt);
	}

	public void modifySpcangku(Spcangku spcangku) {
		// TODO Auto-generated method stub
		spcangkuDao.update(spcangku);
	}

	public void deleteSpcangku(Integer id) {
		// TODO Auto-generated method stub
		spcangkuDao.delete(id);

	}

	public void save(Spcangku spcangku) {
		// TODO Auto-generated method stub
		spcangkuDao.add(spcangku);

	}

}
