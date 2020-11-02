package com.example.main.controllers;

import java.util.Optional;

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

import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.services.implementations.InstitutionServiceImpl;
import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;

@Controller
public class InstitutionController {

	private InstitutionServiceImpl instService;
	
	@Autowired
	public InstitutionController(InstitutionServiceImpl instService) {
		this.instService = instService;
	}
	
	@GetMapping("/institutions/")
	public String indexInstitutions(Model model) {
		model.addAttribute("institutions", instService.findAll());
		return "institutions/index";
	}
	
	@GetMapping("/institutions/add")
	public String showSaveInst(Model model) {
		model.addAttribute("institution", new Institution());
		return "institutions/add_inst_1";
	}
	
	@PostMapping("/institutions/add")
	public String saveInst1(@Validated({FirstGroup.class, Default.class}) Institution institution, BindingResult bindingResult, Model model, 
			@RequestParam(value = "action", required = true) String action) {
		if(bindingResult.hasErrors() && !action.equals("Cancel")) {
			return "institutions/add_inst_1";
		}
		else {
			if(!action.equals("Cancel")) {
				return "institutions/add_inst_2";
			}
			return "redirect:/institutions/";
		}
	}
	
	@PostMapping("/institutions/add1")
	public String saveInst2(@Validated({SecondGroup.class, Default.class}) Institution institution, BindingResult bindingResult, Model model, 
			@RequestParam(value = "action", required = true) String action) 
					throws URLWithoutProtocolException, InstitutionWithoutNameException {
		if(bindingResult.hasErrors() && !action.equals("Cancel")) {
			return "institutions/add_inst_2";
		}
		else {
			if(!action.equals("Cancel")) {
				instService.saveInstitution(institution);
			}
			return "redirect:/institutions/";
		}
	}
	
	@GetMapping("/institutions/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Optional<Institution> institution = instService.findById(id);
		if (institution == null)
			throw new IllegalArgumentException("Invalid institution Id:" + id);
		model.addAttribute("institution", institution.get());
		return "institutions/update_inst_1";
	}

	@PostMapping("/institutions/edit/{id}")
	public String updateUser1(@PathVariable("id") long id,@RequestParam(value = "action", required = true) String action, 
			@Validated({FirstGroup.class, Default.class}) Institution institution, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "institutions/update_inst_1";
		}else {
			if (action != null && !action.equals("Cancel")) {
				return "institutions/update_inst_2";
			}
			return "redirect:/institutions/";
		}
	}
	
	@PostMapping("/institutions/edit1/{id}")
	public String updateUser2(@PathVariable("id") long id,@RequestParam(value = "action", required = true) String action, 
			@Validated({SecondGroup.class, Default.class}) Institution institution, BindingResult bindingResult, Model model) 
			throws URLWithoutProtocolException, InstitutionWithoutNameException {
		if(bindingResult.hasErrors() && !action.equals("Cancel")) {
			return "institutions/update_inst_2";
		}else {
			if (action != null && !action.equals("Cancel")) {
				instService.saveInstitution(institution);
				model.addAttribute("institutions", instService.findAll());
			}
			return "redirect:/institutions/";
		}
	}
}
