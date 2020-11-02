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

import com.example.main.exceptions.PhysicalSpaceTypeWithoutInstitutionException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutNameException;
import com.example.main.model.Physicalspacetype;
import com.example.main.services.implementations.InstitutionServiceImpl;
import com.example.main.services.implementations.PhysicalSpaceTypeServiceImpl;
import com.example.main.validation.FirstGroup;

@Controller
public class PhysicalSpaceTypeController {

	private PhysicalSpaceTypeServiceImpl phySpTypeService;
	private InstitutionServiceImpl instService;
	
	@Autowired
	public PhysicalSpaceTypeController(PhysicalSpaceTypeServiceImpl phySpTypeService, InstitutionServiceImpl instService) {
		this.phySpTypeService = phySpTypeService;
		this.instService = instService;
	}
	
	@GetMapping("/physicalSpaceTypes")
	public String indexPhysicalSpaceTypes(Model model) {
		model.addAttribute("physicalSpaceTypes", phySpTypeService.findAll());
		return "physicalSpaceTypes/index";
	}
	
	@GetMapping("/physicalSpaceTypes/add")
	public String showSavePhysicalSpaceTypes(Model model) {
		model.addAttribute("physicalspacetype", new Physicalspacetype());
		return "physicalSpaceTypes/add_phySpaType_1";
	}
	
	@PostMapping("/physicalSpaceTypes/add")
	public String savePhysicalSpaceType1(@Validated({FirstGroup.class, Default.class}) Physicalspacetype physicalspacetype,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if(bindingResult.hasErrors() && !action.equals("Cancelar")) {
			return "physicalSpaceTypes/add_phySpaType_1";
		}
		else {
			if(!action.equals("Cancelar")) {
				model.addAttribute("institutions", instService.findAll());
				return "physicalSpaceTypes/add_phySpaType_2";
			}
			return "redirect:/physicalSpaceTypes";
		}
	}
	
	@PostMapping("/physicalSpaceTypes/add1")
	public String savePhysicalSpaceType2(@Validated({FirstGroup.class, Default.class}) Physicalspacetype physicalspacetype,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action)
			throws NoSuchElementException, PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException {
		if(bindingResult.hasErrors() && !action.equals("Cancelar")) {
			model.addAttribute("institutions", instService.findAll());
			return "physicalSpaceTypes/add_phySpaType_2";
		}
		else {
			if(!action.equals("Cancelar")) {
				phySpTypeService.savePhysicalSpaceType(physicalspacetype);
			}
			return "redirect:/physicalSpaceTypes";
		}
	}
}
