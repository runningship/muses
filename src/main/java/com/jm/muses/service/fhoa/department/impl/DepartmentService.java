package com.jm.muses.service.fhoa.department.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jm.muses.dao.DaoSupport;
import com.jm.muses.entity.Page;
import com.jm.muses.entity.system.Department;
import com.jm.muses.service.fhoa.department.DepartmentManager;
import com.jm.muses.util.PageData;

/** 
 * 说明： 组织机构
 * 
 * 创建时间：2015-12-16
 * @version
 */
@Service("departmentService")
public class DepartmentService implements DepartmentManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DepartmentMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DepartmentMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DepartmentMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DepartmentMapper.datalistPage", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DepartmentMapper.findById", pd);
	}
	
	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByBianma(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DepartmentMapper.findByBianma", pd);
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Department> listSubDepartmentByParentId(String parentId) throws Exception {
		return (List<Department>) dao.findForList("DepartmentMapper.listSubDepartmentByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Department> listAllDepartment(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setTreeurl("department/list.do?DEPARTMENT_ID="+depar.getDEPARTMENT_ID());
			depar.setSubDepartment(this.listAllDepartment(depar.getDEPARTMENT_ID()));
			depar.setTarget("treeFrame");
		}
		return departmentList;
	}
	
}

