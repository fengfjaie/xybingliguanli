package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.*;
import com.model.*;

@Service
public class ShangpinService {
	@Autowired
	private ShangpinDao shangpinDao;

	public List queryShangpins(Shangpin record,int page,int rows, String sdate, String edate) {
		// TODO Auto-generated method stub
		return shangpinDao.getShangpinList(record,page,rows,sdate,edate);
	}

	public Shangpin getShangpin(int parseInt) {
		// TODO Auto-generated method stub
		return shangpinDao.getShangpinById(parseInt);
	}

	public void modifyShangpin(Shangpin shangpin) {
		// TODO Auto-generated method stub
		shangpinDao.update(shangpin);
	}

	public void deleteShangpin(Integer id) {
		// TODO Auto-generated method stub
		shangpinDao.delete(id);

	}

	public void save(Shangpin shangpin) {
		// TODO Auto-generated method stub
		shangpinDao.add(shangpin);

	}

}
