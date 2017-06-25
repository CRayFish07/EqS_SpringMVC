package com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.EquipmentDao;
import com.dao.RepairDao;
import com.model.Equipment;
import com.model.PageBean;
import com.model.Repair;
import com.service.EquipmentService;

@Service("equipmentService")
public class EquipmentServiceImpl implements EquipmentService{

	@Resource
	private EquipmentDao equipmentDao;
	
	@Resource
	private RepairDao repairDao;
	
	@Override
	public List<Equipment> find(PageBean pageBean, Equipment s_equipment) {
		return equipmentDao.find(pageBean, s_equipment);
	}

	@Override
	public int count(Equipment s_equipment) {
		return equipmentDao.count(s_equipment);
	}

	@Override
	public void delete(int id) {
		equipmentDao.delete(id);
	}

	@Override
	public void add(Equipment equipment) {
		equipmentDao.add(equipment);
	}

	@Override
	public void update(Equipment equipment) {
		equipmentDao.update(equipment);
	}

	@Override
	public Equipment loadById(int id) {
		return equipmentDao.loadById(id);
	}

	@Override
	public boolean existEquipmentByTypeId(int typeId) {
		return equipmentDao.existEquipmentByTypeId(typeId);
	}

	@Override
	public void addRepair(int id, String userMan) {
		Repair repair=new Repair();
		repair.setEquipmentId(id);
		repair.setUserMan(userMan);
		repairDao.add(repair);
		
		Equipment equipment=equipmentDao.loadById(id);
		equipment.setState(2);
		equipmentDao.update(equipment);
	}



}
