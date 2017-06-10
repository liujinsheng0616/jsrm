package org.jsrml.sv.organization.controller.admin;

import org.jsrml.common.util.JSONResultDTO;
import org.jsrml.core.organization.command.CreateAdminCommand;
import org.jsrml.core.organization.entity.Admin;
import org.jsrml.core.organization.service.AdminService;
import org.jsrml.sv.organization.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminController extends BaseController{

	@Autowired
	private AdminService adminService;
		
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public JSONResultDTO save(@RequestBody CreateAdminCommand command) throws Exception {
		Admin admin = adminService.createAdmin(command);
		return createSuccessResult(admin);
	}
	
}
