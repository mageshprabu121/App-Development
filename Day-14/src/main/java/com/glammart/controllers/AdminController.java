package com.glammart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glammart.entities.Admin;
import com.glammart.entities.Customer;
import com.glammart.models.LoginDTO;
import com.glammart.models.LoginResponse;
import com.glammart.models.Response;
import com.glammart.services.AdminService;
import com.glammart.services.CustomerService;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired private AdminService adminService;
	@Autowired private CustomerService customerService;
	
	@PostMapping("/validate")
	public ResponseEntity<?> validateUser(@RequestBody LoginDTO dto) {
		System.out.println(dto);
		Admin admin=adminService.validate(dto.getUserid(),dto.getPwd());
		Customer user=customerService.validate(dto.getUserid(),dto.getPwd());
		LoginResponse response;
		if(admin!=null) 
			response=new LoginResponse(0,admin.getUserid(),admin.getUname(),"admin");
		else if(user!=null)
			response=new LoginResponse(user.getId(), user.getUserid(), user.getName(), "Customer");
		else
			return Response.status(HttpStatus.NOT_FOUND);
		return Response.success(response);
	}
	
	@PostMapping
	public ResponseEntity<?> updateProfile(@RequestBody Admin admin) {
		adminService.updateAdmin(admin);
		return Response.status(HttpStatus.OK);
	}

}
