package com.fh.interceptor.shiro;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;


/**
 *  2015-3-6
 */
public class MusesCasRealm extends CasRealm {

	/*
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		Session session = Jurisdiction.getSession();
		UserManager userService = (UserManager)Const.WEB_APP_CONTEXT.getBean("userService");
		PageData pd = new PageData();
		pd.put("USERNAME", "admin");
		String passwd = new SimpleHash("SHA-1", "admin", "1").toString();	//密码加密
		pd.put("PASSWORD", passwd);
		try{
			//TODO 最终会根据uid来查找用户信息
			pd = userService.getUserByNameAndPwd(pd);	//根据用户名和密码去读取用户信息
			if(pd!=null){
				pd.put("LAST_LOGIN",DateUtil.getTime().toString());
				userService.updateLastLogin(pd);
				User user = new User();
				user.setUSER_ID(pd.getString("USER_ID"));
				user.setUSERNAME(pd.getString("USERNAME"));
				user.setPASSWORD(pd.getString("PASSWORD"));
				user.setNAME(pd.getString("NAME"));
				user.setRIGHTS(pd.getString("RIGHTS"));
				user.setROLE_ID(pd.getString("ROLE_ID"));
				user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
				user.setIP(pd.getString("IP"));
				user.setSTATUS(pd.getString("STATUS"));
				session.setAttribute(Const.SESSION_USER, user);			//把用户信息放session中
				
				SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
				// add roles
		        simpleAuthorizationInfo.addRole(user.getROLE_ID());
		        simpleAuthorizationInfo.addRole("DEFAULT_USER");
		        // add default permissions
		        Role role = user.getRole();
		        if(role!=null){
		        	simpleAuthorizationInfo.addStringPermission(user.getRole().getRIGHTS());
		        }
		        return simpleAuthorizationInfo;
			}
		}catch(Exception ex){
			return null;
		}
		return null;
	}

}
