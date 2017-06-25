package com.dao;

import java.util.List;

import com.model.PageBean;
import com.model.Repair;

public interface RepairDao {

	public void add(Repair repair);
	
	public List<Repair> find(PageBean pageBean, Repair s_repair);
	
	public int count(Repair s_repair);
}
