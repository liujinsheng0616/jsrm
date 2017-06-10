package org.jsrml.sv.organization.controller.login;

import org.jsrml.core.organization.qo.AdminQO;
import org.jsrml.core.organization.service.AdminService;
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
	private AdminService adminService;

/*	@ApiOperation(value = "DemoTest", notes = "示例测试Controller")
	@ApiImplicitParam(name = "id", value = "操作id", required = false, dataType = "String")*/
	@RequestMapping(value = "/demotest", method = RequestMethod.POST)
	public Object demoTest() throws Exception {
		AdminQO qo = new AdminQO();
		Integer a = adminService.queryCount(qo);
		return "{name:"+a+"}";
	}
	
}
