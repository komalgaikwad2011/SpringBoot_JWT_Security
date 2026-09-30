package com.examples.springSecurity.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.springSecurity.Entity.Roles;
import com.examples.springSecurity.Service.roleService;

@RestController
@RequestMapping("/api/roles")
public class roleController {

		private roleService roleservice;
		
		public roleController(roleService roleservice)
		{
			this.roleservice=roleservice;
		}
		
		public ResponseEntity<String> addRole(@RequestBody Roles role)
		{
			roleservice.addRole(role);
			return ResponseEntity.ok("done");
		}
}
