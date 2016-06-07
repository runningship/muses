package com.jm.muses.controller.base;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.jm.muses.entity.Page;
import com.jm.muses.util.Jurisdiction;
import com.jm.muses.util.Logger;
import com.jm.muses.util.PageData;
import com.jm.muses.util.UuidUtil;

/**
 * 修改时间：2015、12、11
 */
public class BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	/** new PageData对象
	 * @return
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**得到ModelAndView
	 * @return
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**得到request对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}
	
	/**得到分页列表的信息
	 * @return
	 */
	public Page getPage(){
		return new Page();
	}
	
	public void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	protected boolean checkAddPermission() {
		if(!Jurisdiction.buttonJurisdiction(getMenuUrl(), "add")){
			return false;
		}
		return true;
	}
	
	protected boolean checkEditPermission() {
		if(!Jurisdiction.buttonJurisdiction(getMenuUrl(), "edit")){
			return false;
		}
		return true;
	}
	
	protected boolean checkDelPermission() {
		if(!Jurisdiction.buttonJurisdiction(getMenuUrl(), "del")){
			return false;
		}
		return true;
	}
	
	protected boolean checkListPermission() {
		if(!Jurisdiction.buttonJurisdiction(getMenuUrl(), "cha")){
			return false;
		}
		return true;
	}
	
	private String getMenuUrl(){
		String menuUrl = this.getClass().getSimpleName().toLowerCase().replace("controller", "")+"/list.do";
		return menuUrl;
	}
}
