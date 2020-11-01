package com.example.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.main.services.implementations.InstitutionServiceImpl;
import com.example.main.services.implementations.PhysicalSpaceTypeServiceImpl;

@Controller
public class PhysicalSpaceTypeController {

	private PhysicalSpaceTypeServiceImpl phySpTypeService;
	private InstitutionServiceImpl instService;
	
	@Autowired
	public PhysicalSpaceTypeController(PhysicalSpaceTypeServiceImpl phySpTypeService, InstitutionServiceImpl instService) {
		this.phySpTypeService = phySpTypeService;
		this.instService = instService;
	}
	
	@GetMapping("/physicalSpaceTypes/")
	public String indexPhysicalSpaceTypes(Model model) {
		model.addAttribute("physicalSpaceTypes", phySpTypeService.findAll());
		return "physicalSpaceTypes/index";
	}
	
}
