package com.jm.muses.controller.vip.vuser;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang.StringUtils;

import com.jm.muses.controller.base.BaseController;
import com.jm.muses.entity.Page;
import com.jm.muses.service.BaseService;
import com.jm.muses.util.AppUtil;
import com.jm.muses.util.Jurisdiction;
import com.jm.muses.util.ObjectExcelView;
import com.jm.muses.util.PageData;
import com.jm.muses.util.Tools;

/** 
 * 说明：会员
 * 
 * 创建时间：2016-06-07
 */
@Controller
@RequestMapping(value="/vuser")
public class VUserController extends BaseController {
	
	//String menuUrl = "vuser/list.do"; //菜单地址(权限用)
	@Resource(name="baseService")
	private BaseService vuserService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增VUser");
		if(!checkAddPermission()){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("VUSER_ID", this.get32UUID());	//主键
		pd.put("REGTIME", Tools.date2Str(new Date()));	//注册时间
		pd.put("ADDTIME", Tools.date2Str(new Date()));	//创建时间
		pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后登录时间
		pd.put("BONUS", "0");	//奖金
		pd.put("JIFEN", "0");	//奖金
		pd.put("COMPANYID", "11");	//公司id
		pd.put("STATUS", "待审核");	//奖金
		pd.put("AVILIABLEBONUS", "0");	//可用奖金
		vuserService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除VUser");
		if(!checkDelPermission()){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		vuserService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改VUser");
		if(!checkEditPermission()){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		vuserService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表VUser");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(StringUtils.isNotEmpty(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = vuserService.list(page);	//列出VUser列表
		mv.setViewName("vip/vuser/vuser_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("vip/vuser/vuser_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = vuserService.findById(pd);	//根据ID读取
		mv.setViewName("vip/vuser/vuser_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除VUser");
		if(!checkDelPermission()){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			vuserService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出VUser到excel");
		if(!checkListPermission()){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("行业");	//1
		titles.add("姓名");	//2
		titles.add("来源");	//3
		titles.add("网站类型");	//4
		titles.add("昵称");	//5
		titles.add("手机号码");	//6
		titles.add("公司抬头");	//7
		titles.add("注册时间");	//8
		titles.add("创建时间");	//9
		titles.add("最后登录时间");	//10
		titles.add("积分");	//11
		titles.add("状态");	//12
		titles.add("推荐人");	//13
		titles.add("交易员");	//14
		titles.add("奖金");	//15
		titles.add("可用奖金");	//16
		titles.add("用户类型");	//17
		dataMap.put("titles", titles);
		List<PageData> varOList = vuserService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("INDUSTRYTYPE"));	//1
			vpd.put("var2", varOList.get(i).getString("NAME"));	//2
			vpd.put("var3", varOList.get(i).getString("REFER"));	//3
			vpd.put("var4", varOList.get(i).getString("SITENAME"));	//4
			vpd.put("var5", varOList.get(i).getString("NICKNAME"));	//5
			vpd.put("var6", varOList.get(i).getString("TEL"));	//6
			vpd.put("var7", varOList.get(i).getString("COMPANYID"));	//7
			vpd.put("var8", varOList.get(i).getString("REGTIME"));	//8
			vpd.put("var9", varOList.get(i).getString("ADDTIME"));	//9
			vpd.put("var10", varOList.get(i).getString("LASTTIME"));	//10
			vpd.put("var11", varOList.get(i).get("JIFEN").toString());	//11
			vpd.put("var12", varOList.get(i).getString("STATUS"));	//12
			vpd.put("var13", varOList.get(i).getString("REFEREEID"));	//13
			vpd.put("var14", varOList.get(i).getString("DEALERID"));	//14
			vpd.put("var15", varOList.get(i).get("BONUS").toString());	//15
			vpd.put("var16", varOList.get(i).get("AVILIABLEBONUS").toString());	//16
			vpd.put("var17", varOList.get(i).getString("USERTYPE"));	//17
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
}
