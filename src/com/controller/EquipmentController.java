package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model.Equipment;
import com.model.EquipmentType;
import com.model.PageBean;
import com.model.Repair;
import com.model.User;
import com.service.EquipmentService;
import com.service.EquipmentTypeService;
import com.service.RepairService;
import com.util.PageUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

	@Autowired
	private EquipmentService equipmentService;
	
	@Autowired
	private EquipmentTypeService equipmentTypeService;
	
	@Autowired
	private RepairService repairService;
	
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value="page", required=false)String page, Equipment s_equipment, HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		if(StringUtil.isEmpty(page)){
			page = "1";
			session.setAttribute("s_equipment", s_equipment);
		} else{
			s_equipment = (Equipment)session.getAttribute("s_equipment");
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),3);
		List<Equipment> equipmentList=equipmentService.find(pageBean, s_equipment);
		int total=equipmentService.count(s_equipment);
		String pageCode=PageUtil.getPagation(request.getContextPath()+"/equipment/list.do", total, Integer.parseInt(page), 3);
		mav.addObject("pageCode", pageCode);
		mav.addObject("modeName", "设备管理");
		mav.addObject("equipmentList",equipmentList);
		mav.addObject("mainPage", "equipment/list.jsp");
		mav.setViewName("main");
		return mav;
	}
	
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="id")String id, HttpServletResponse response) throws Exception{
		JSONObject result = new JSONObject();
		equipmentService.delete(Integer.parseInt(id));
		result.put("success", true);
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/preSave")
	public ModelAndView preSave(@RequestParam(value="id", required=false)String id){
		ModelAndView mav = new ModelAndView();
		List<EquipmentType> equipmentTypeList = equipmentTypeService.find(null, null);
		mav.addObject("mainPage", "equipment/save.jsp");
		mav.addObject("modeName", "设备管理");
		mav.addObject("equipmentTypeList",equipmentTypeList);
		mav.setViewName("main");
		if(StringUtil.isNotEmpty(id)){
			mav.addObject("actionName","设备修改");
			Equipment equipment = equipmentService.loadById(Integer.parseInt(id));
			mav.addObject("equipment", equipment);
		}else{
			mav.addObject("actionName","设备添加");
		}
		return mav;
	}
	
	@RequestMapping("/save")
	public String save(Equipment equipment){
		if(equipment.getId()==null){
			equipmentService.add(equipment);
		}else{
			equipmentService.update(equipment);
		}
		return "redirect:/equipment/list.do";
	}
	
	@RequestMapping("/userList")
	public ModelAndView userList(@RequestParam(value="page",required=false)String page,Equipment s_equipment,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		if(StringUtil.isEmpty(page)){
			page = "1";
			session.setAttribute("s_equipment", s_equipment);
		}else{
			s_equipment=(Equipment) session.getAttribute("s_equipment");
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		List<Equipment> equipmentList = equipmentService.find(pageBean, s_equipment);
		int total = equipmentService.count(s_equipment);
		String pageCode = PageUtil.getPagation(request.getContextPath()+"/equipment/userList.do", total, Integer.parseInt(page), 3);
		mav.addObject("pageCode", pageCode);
		mav.addObject("modeName", "使用设备管理");
		mav.addObject("equipmentList", equipmentList);
		mav.addObject("mainPage", "equipment/userList.jsp");
		mav.setViewName("main");
		return mav;		
	}
	
	
	@RequestMapping("/repair")
	public void repair(@RequestParam(value="id")String id,HttpSession session,HttpServletResponse response)throws Exception{
		String userMan=((User)session.getAttribute("currentUser")).getUserName();
		JSONObject result=new JSONObject();
		equipmentService.addRepair(Integer.parseInt(id), userMan);
		result.put("success", true);			
		ResponseUtil.write(result, response);
	}
	
	
	
	@RequestMapping("/repairList")
	public ModelAndView repairList(@RequestParam(value="page",required=false)String page,Repair s_repair,HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
			session.setAttribute("s_repair", s_repair);
		}else{
			s_repair=(Repair) session.getAttribute("s_repair");
		}
		s_repair.setFinishState(1);
		PageBean pageBean=new PageBean(Integer.parseInt(page),3);
		List<Repair> repairList=repairService.find(pageBean, s_repair);
		int total=repairService.count(s_repair);
		String pageCode=PageUtil.getPagation(request.getContextPath()+"/equipment/repairList.do", total, Integer.parseInt(page), 3);
		mav.addObject("pageCode", pageCode);
		mav.addObject("modeName", "维修设备管理");
		mav.addObject("repairList", repairList);
		mav.addObject("mainPage", "equipment/repairList.jsp");
		mav.setViewName("main");
		return mav;
	}
}
