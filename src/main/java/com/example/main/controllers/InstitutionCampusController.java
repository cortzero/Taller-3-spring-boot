package com.example.main.controllers;

import java.util.NoSuchElementException;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.model.Institutioncampus;
import com.example.main.model.Physicalspace;
import com.example.main.services.implementations.InstitutionCampusServiceImpl;
import com.example.main.services.implementations.InstitutionServiceImpl;
import com.example.main.services.implementations.PhysicalSpaceServiceImpl;
import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;

@Controller
public class InstitutionCampusController {

	private InstitutionCampusServiceImpl campusService;
	private InstitutionServiceImpl instService;
	private PhysicalSpaceServiceImpl physicalSpaceService;
	
	@Autowired
	public InstitutionCampusController(InstitutionCampusServiceImpl campusService, InstitutionServiceImpl instService,
			PhysicalSpaceServiceImpl physicalSpaceService) {
		this.campusService = campusService;
		this.instService = instService;
		this.physicalSpaceService = physicalSpaceService;
	}
	
	@GetMapping("/campus")
	public String indexCampus(Model model) {
		model.addAttribute("campusList", campusService.findAll());
		return "campus/index";
	}
	
	@GetMapping("/campus/add")
	public String showSaveCampus(Model model) {
		model.addAttribute("institutioncampus", new Institutioncampus());
		return "campus/add_campus_1";
	}
	
	@PostMapping("/campus/add")
	public String saveCampus1(@Validated({FirstGroup.class, Default.class}) Institutioncampus institutioncampus, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {
		if(bindingResult.hasErrors() && !action.equals("Cancel")) {			
			return "campus/add_campus_1";
		}
		else {
			if(!action.equals("Cancel")) {
				model.addAttribute("institutions", instService.findAll());
				return "campus/add_campus_2";
			}
			return "redirect:/campus";
		}
	}
	
	@PostMapping("/campus/add1")
	public String saveCampus2(@Validated({FirstGroup.class, Default.class}) Institutioncampus institutioncampus, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) 
			throws NoSuchElementException, CampusWithoutNameException, CampusWithNoZeroOccupationException {
		if(bindingResult.hasErrors() && !action.equals("Cancel")) {
			model.addAttribute("institutions", instService.findAll());
			return "campus/add_campus_2";
		}
		else {
			if(!action.equals("Cancel")) {
				campusService.save(institutioncampus);
			}
			return "redirect:/campus";
		}
	}
	
	@GetMapping("/campus/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Institutioncampus institutioncampus = campusService.findById(id);
		if (institutioncampus == null)
			throw new IllegalArgumentException("Invalid institution campus Id:" + id);
		model.addAttribute("institutioncampus", institutioncampus);
		return "campus/update_campus_1";
	}

	@PostMapping("/campus/edit/{id}")
	public String updateCampus1(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action, 
			@Validated({FirstGroup.class, Default.class}) Institutioncampus institutioncampus, BindingResult bindingResult, 
			Model model) {
		if(bindingResult.hasErrors()) {
			return "campus/update_campus_1";
		}else {
			if (action != null && !action.equals("Cancel")) {
				model.addAttribute("institutions", instService.findAll());
				return "campus/update_campus_2";
			}
			return "redirect:/campus/";
		}
	}
	
	@PostMapping("/campus/edit1/{id}")
	public String updateCampus2(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action, 
			@Validated({SecondGroup.class, Default.class}) Institutioncampus institutioncampus, BindingResult bindingResult, 
			Model model) throws NoSuchElementException, CampusWithoutNameException, CampusWithNoZeroOccupationException {
		if(bindingResult.hasErrors() && !action.equals("Cancel")) {
			model.addAttribute("institutions", instService.findAll());
			return "campus/update_campus_2";
		}
		else {
			if (action != null && !action.equals("Cancel")) {
				campusService.update(institutioncampus);
			}
			return "redirect:/campus/";
		}
	}
	
	@GetMapping("/campus/del/{id}")
	public String deleteCampus(@PathVariable("id") long id, Model model) {
		Institutioncampus institutioncampus = campusService.findById(id);
		campusService.delete(institutioncampus);
		model.addAttribute("institutioncampus", campusService.findAll());
		return "campus/index";
	}
	
	@GetMapping("/campus/info/{id}")
	public String showInformation(@PathVariable("id") long id, Model model) {
		Institutioncampus institutioncampus = campusService.findById(id);
		model.addAttribute("institutioncampus", institutioncampus);
		return "campus/show_info";
	}
	
	@GetMapping("/campus/info/{campusid}/physicalspace/{physpid}")
	public String showInformationFromPhysicalSpace(@PathVariable("campusid") long campusid, @PathVariable("physpid") long physpid, Model model) {
		Institutioncampus institutioncampus = campusService.findById(campusid);
		Physicalspace physicalspace = physicalSpaceService.findById(physpid);
		model.addAttribute("institutioncampus", institutioncampus);
		model.addAttribute("physicalspace", physicalspace);
		return "campus/show_info";
	}
}
