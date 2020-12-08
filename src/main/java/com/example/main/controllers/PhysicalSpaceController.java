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

import com.example.main.exceptions.No5DigitsExternalIDException;
import com.example.main.model.Physicalspace;
import com.example.main.services.implementations.InstitutionCampusServiceImpl;
import com.example.main.services.implementations.PhysicalSpaceServiceImpl;
import com.example.main.services.implementations.PhysicalSpaceTypeServiceImpl;
import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;

@Controller
public class PhysicalSpaceController {
	
	private PhysicalSpaceServiceImpl physicalSpaceService;
	private InstitutionCampusServiceImpl instCampusService;
	private PhysicalSpaceTypeServiceImpl physSpacTypeService;
	
	@Autowired
	public PhysicalSpaceController(PhysicalSpaceServiceImpl physicalSpaceService, InstitutionCampusServiceImpl instCampusService,
			PhysicalSpaceTypeServiceImpl physSpacTypeService) {
		this.physicalSpaceService = physicalSpaceService;
		this.instCampusService = instCampusService;
		this.physSpacTypeService = physSpacTypeService;
	}
	
	@GetMapping("/physicalSpaces")
	public String indexPhySpaces(Model model) {
		model.addAttribute("physicalspaces", physicalSpaceService.findAll());
		return "physicalSpaces/index";
	}
	
	@GetMapping("/physicalSpaces/add")
	public String showSavePhysSpace(Model model) {
		model.addAttribute("physicalspace", new Physicalspace());
		return "physicalSpaces/add_physicalSpace_1";
	}
	
	@PostMapping("/physicalSpaces/add")
	public String savePhysicalSpace1(@Validated({FirstGroup.class, Default.class}) Physicalspace physicalspace, 
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if(bindingResult.hasErrors() && !action.equals("Cancel")) {
			return "physicalSpaces/add_physicalSpace_1";
		}
		else {
			if(!action.equals("Cancel")) {
				model.addAttribute("institutioncampuses", instCampusService.findAll());
				model.addAttribute("physicalspacetypes", physSpacTypeService.findAll());
				return "physicalSpaces/add_physicalSpace_2";
			}
			return "redirect:/physicalSpaces";
		}
	}
	
	@PostMapping("/physicalSpaces/add1")
	public String savePhysicalSpace2(@Validated({SecondGroup.class, Default.class}) Physicalspace physicalspace, 
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action)
			throws NumberFormatException, NoSuchElementException, No5DigitsExternalIDException {
		if(bindingResult.hasErrors() && !action.equals("Cancel")) {
			model.addAttribute("institutioncampuses", instCampusService.findAll());
			model.addAttribute("physicalspacetypes", physSpacTypeService.findAll());
			return "physicalSpaces/add_physicalSpace_2";
		}
		else {
			if(!action.equals("Cancel")) {
				physicalSpaceService.save(physicalspace);
			}
			return "redirect:/physicalSpaces/";
		}
	}
	
	@GetMapping("/physicalSpaces/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Physicalspace physicalspace = physicalSpaceService.findById(id);
		if (physicalspace == null)
			throw new IllegalArgumentException("Invalid physical space Id:" + id);
		model.addAttribute("physicalspace", physicalspace);
		return "physicalSpaces/update_physicalSpace_1";
	}

	@PostMapping("/physicalSpaces/edit/{id}")
	public String updatePhysicalSpace1(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action, 
			@Validated({FirstGroup.class, Default.class}) Physicalspace physicalspace, BindingResult bindingResult, 
			Model model) {
		if(bindingResult.hasErrors()) {
			return "physicalSpaces/update_physicalSpace_1";
		}else {
			if (action != null && !action.equals("Cancel")) {
				model.addAttribute("institutioncampuses", instCampusService.findAll());
				model.addAttribute("physicalspacetypes", physSpacTypeService.findAll());
				return "physicalSpaces/update_physicalSpace_2";
			}
			return "redirect:/physicalSpaces/";
		}
	}
	
	@PostMapping("/physicalSpaces/edit1/{id}")
	public String updatePhysicalSpace2(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action, 
			@Validated({SecondGroup.class, Default.class}) Physicalspace physicalspace, BindingResult bindingResult, 
			Model model) throws NumberFormatException, NoSuchElementException, No5DigitsExternalIDException {
		if(bindingResult.hasErrors() && !action.equals("Cancel")) {
			model.addAttribute("institutioncampuses", instCampusService.findAll());
			model.addAttribute("physicalspacetypes", physSpacTypeService.findAll());
			return "physicalSpaces/update_physicalSpace_2";
		}else {
			if (action != null && !action.equals("Cancel")) {
				physicalSpaceService.update(physicalspace);
			}
			return "redirect:/physicalSpaces/";
		}
	}
	
	@GetMapping("/physicalSpaces/del/{id}")
	public String deletePhysicalSpace(@PathVariable("id") long id, Model model) {
		Physicalspace physicalspace = physicalSpaceService.findById(id);
		physicalSpaceService.delete(physicalspace);
		model.addAttribute("physicalspaces", physicalSpaceService.findAll());
		return "physicalSpaces/index";
	}
	
	@GetMapping("/physicalSpaces/info/{id}")
	public String showInformation(@PathVariable("id") long id, Model model) {
		Physicalspace physicalspace = physicalSpaceService.findById(id);
		model.addAttribute("physicalspace", physicalspace);
		return "physicalSpaces/show_info";
	}
}
