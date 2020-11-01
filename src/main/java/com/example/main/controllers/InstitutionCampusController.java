package com.example.main.controllers;

import java.util.NoSuchElementException;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.model.Institutioncampus;
import com.example.main.services.implementations.InstitutionCampusServiceImpl;
import com.example.main.services.implementations.InstitutionServiceImpl;
import com.example.main.validation.FirstGroup;

@Controller
public class InstitutionCampusController {

	private InstitutionCampusServiceImpl campusService;
	private InstitutionServiceImpl instService;
	
	@Autowired
	public InstitutionCampusController(InstitutionCampusServiceImpl campusService, InstitutionServiceImpl instService) {
		this.campusService = campusService;
		this.instService = instService;
	}
	
	@GetMapping("/campus")
	public String indexCampus(Model model) {
		model.addAttribute("campusList", campusService.findAll());
		return "campus/index";
	}
	
	@GetMapping("/campus/add")
	public String showSaveCampus(Model model) {
		model.addAttribute("campus", new Institutioncampus());
		return "campus/add_campus_1";
	}
	
	@PostMapping("/campus/add")
	public String saveCampus1(@Validated({FirstGroup.class, Default.class}) Institutioncampus campus, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {
		if(bindingResult.hasErrors() && !action.equals("Cancelar")) {			
			return "campus/add_campus_1";
		}
		else {
			if(!action.equals("Cancelar")) {
				model.addAttribute("campus", campus);
				model.addAttribute("institutions", instService.findAll());
				return "campus/add_campus_2";
			}
			return "redirect:/campus";
		}
	}
	
	@PostMapping("/campus/add1")
	public String saveCampus2(@Validated({FirstGroup.class, Default.class}) Institutioncampus campus, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) 
			throws NoSuchElementException, CampusWithoutNameException, CampusWithNoZeroOccupationException {
		if(bindingResult.hasErrors() && !action.equals("Cancelar")) {
			model.addAttribute("campus", campus);
			model.addAttribute("institutions", instService.findAll());
			return "campus/add_campus_2";
		}
		else {
			if(!action.equals("Cancelar")) {
				campusService.saveInstitutionCampus(campus);
			}
			return "redirect:/campus";
		}
	}	
	
}
