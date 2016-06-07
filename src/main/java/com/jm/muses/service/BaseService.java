package com.jm.muses.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jm.muses.dao.DaoSupport;
import com.jm.muses.entity.Page;
import com.jm.muses.util.PageData;

/** 
 * 说明： 通用Service
 * 
 * 创建时间：2016-06-04
 * @version
 */
@Service("baseService")
public class BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	private String mapperPrefix;
	
	public void setMapperPrefix(String mapperPrefix) {
		this.mapperPrefix = mapperPrefix;
	}

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save(mapperPrefix + ".save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete(mapperPrefix + ".delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		if(pd!=null){
			throw new RuntimeException();
		}
		dao.update(mapperPrefix + ".edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList(mapperPrefix + ".datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList(mapperPrefix + ".listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject(mapperPrefix + ".findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete(mapperPrefix + ".deleteAll", ArrayDATA_IDS);
	}
	
}
