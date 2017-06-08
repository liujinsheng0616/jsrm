package org.jsrml.sv.organization.controller.login;

import org.jsrml.core.organization.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;

/*	@ApiOperation(value = "DemoTest", notes = "示例测试Controller")
	@ApiImplicitParam(name = "id", value = "操作id", required = false, dataType = "String")*/
	@RequestMapping(value = "/demotest", method = RequestMethod.POST)
	public Object demoTest() throws Exception {
		System.out.println(123123);
		String a = loginService.loginInfo();
		return "{name:"+a+"}";
	}

}