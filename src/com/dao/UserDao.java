package com.dao;

import java.util.List;

import com.model.PageBean;
import com.model.User;

public interface UserDao {

	public User login(User user);
	
	public List<User> find(PageBean pageBean, User s_user);
	
	public int count(User s_user);
	
	public void delete(int id);
	
	public void add(User user);
	
	public void update(User user);
	
	public User loadById(int id);
	
	public boolean existUserByDeptId(int deptId);
}
