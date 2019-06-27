package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.*;
import com.model.*;

@Service
public class UtaolunService {
	@Autowired
	private UtaolunDao utaolunDao;

	public List queryUtaoluns(Utaolun record,int page,int rows, String sdate, String edate) {
		// TODO Auto-generated method stub
		return utaolunDao.getUtaolunList(record,page,rows,sdate,edate);
	}

	public Utaolun getUtaolun(int parseInt) {
		// TODO Auto-generated method stub
		return utaolunDao.getUtaolunById(parseInt);
	}

	public void modifyUtaolun(Utaolun utaolun) {
		// TODO Auto-generated method stub
		utaolunDao.update(utaolun);
	}

	public void deleteUtaolun(Integer id) {
		// TODO Auto-generated method stub
		utaolunDao.delete(id);

	}

	public void save(Utaolun utaolun) {
		// TODO Auto-generated method stub
		utaolunDao.add(utaolun);

	}

}
