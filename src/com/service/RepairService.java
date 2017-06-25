package com.service;

import java.util.List;

import com.model.PageBean;
import com.model.Repair;

public interface RepairService {

	public List<Repair> find(PageBean pageBean,Repair s_repair);
	
	public int count(Repair s_repair);
	
}
