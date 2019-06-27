package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.*;
import com.model.*;

@Service
public class YhbumenService {
	@Autowired
	private YhbumenDao yhbumenDao;

	public List queryYhbumens(Yhbumen record,int page,int rows) {
		// TODO Auto-generated method stub
		return yhbumenDao.getYhbumenList(record,page,rows);
	}

	public Yhbumen getYhbumen(int parseInt) {
		// TODO Auto-generated method stub
		return yhbumenDao.getYhbumenById(parseInt);
	}

	public void modifyYhbumen(Yhbumen yhbumen) {
		// TODO Auto-generated method stub
		yhbumenDao.update(yhbumen);
	}

	public void deleteYhbumen(Integer id) {
		// TODO Auto-generated method stub
		yhbumenDao.delete(id);

	}

	public void save(Yhbumen yhbumen) {
		// TODO Auto-generated method stub
		yhbumenDao.add(yhbumen);

	}

}
