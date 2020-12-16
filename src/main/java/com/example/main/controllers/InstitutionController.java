package com.example.main.controllers;

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

import com.example.main.delegate.interfaces.InstitutionCampusDelegate;
import com.example.main.delegate.interfaces.InstitutionDelegate;
import com.example.main.delegate.interfaces.PhysicalSpaceTypeDelegate;
import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.model.Institutioncampus;
import com.example.main.model.Physicalspacetype;
import com.example.main.services.implementations.InstitutionCampusServiceImpl;
import com.example.main.services.implementations.InstitutionServiceImpl;
import com.example.main.services.implementations.PhysicalSpaceTypeServiceImpl;
import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;

@Controller
public class InstitutionController {

	private InstitutionDelegate instDelegate;
	private InstitutionCampusDelegate campusDelegate;
	private PhysicalSpaceTypeDelegate physiSpaceTyDelegate;
	
	@Autowired
	public InstitutionController(InstitutionDelegate instDelegate, InstitutionCampusDelegate campusDelegate, 
			PhysicalSpaceTypeDelegate physiSpaceTyDelegate) {
		this.instDelegate = instDelegate;
		this.campusDelegate = campusDelegate;
		this.physiSpaceTyDelegate = physiSpaceTyDelegate;
	}
	
	@GetMapping("/institutions/")
	public String indexInstitutions(Model model) {
		model.addAttribute("institutions", instDelegate.getAllInstitutions());
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
				instDelegate.createInstitution(institution);
			}
			return "redirect:/institutions/";
		}
	}
	
	@GetMapping("/institutions/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Institution institution = instDelegate.getInstitution(id);
		if (institution == null)
			throw new IllegalArgumentException("Invalid institution Id:" + id);
		model.addAttribute("institution", institution);
		return "institutions/update_inst_1";
	}

	@PostMapping("/institutions/edit/{id}")
	public String updateUser1(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action, 
			@Validated({FirstGroup.class, Default.class}) Institution institution, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "institutions/update_inst_1";
		}
		else {
			if (action != null && !action.equals("Cancel")) {
				return "institutions/update_inst_2";
			}
			return "redirect:/institutions/";
		}
	}
	
	@PostMapping("/institutions/edit1/{id}")
	public String updateUser2(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action, 
			@Validated({SecondGroup.class, Default.class}) Institution institution, BindingResult bindingResult, Model model) 
			throws URLWithoutProtocolException, InstitutionWithoutNameException {
		if(bindingResult.hasErrors() && !action.equals("Cancel")) {
			return "institutions/update_inst_2";
		}
		else {
			if (action != null && !action.equals("Cancel")) {
				instDelegate.updateInstitution(id, institution);;
				model.addAttribute("institutions", instDelegate.getAllInstitutions());
			}
			return "redirect:/institutions/";
		}
	}
	
	@GetMapping("/institutions/del/{id}")
	public String deleteInstitution(@PathVariable("id") long id, Model model) {
		instDelegate.deleteInstitution(id);
		model.addAttribute("institutions", instDelegate.getAllInstitutions());
		return "institutions/index";
	}
	
	@GetMapping("/institutions/info/{id}")
	public String showInformation(@PathVariable("id") long id, Model model) {
		Institution institution = instDelegate.getInstitution(id);
		model.addAttribute("institution", institution);
		return "institutions/show_inst_information";
	}
	
	@GetMapping("/institutions/info/{instid}/campus/{campusid}")
	public String showInformationFromCampus(@PathVariable("instid") long instid, @PathVariable("campusid") long campusid, Model model) {
		Institution institution = instDelegate.getInstitution(instid);
		Institutioncampus institutioncampus = campusDelegate.getCampus(campusid);
		model.addAttribute("institution", institution);
		model.addAttribute("institutioncampus", institutioncampus);
		return "institutions/show_inst_information";
	}
	
	@GetMapping("/institutions/info/{instid}/physicalspacetype/{physptyid}")
	public String showInformationFromPhysicalSpaceType(@PathVariable("instid") long instid, @PathVariable("physptyid") long physptyid, Model model) {
		Institution institution = instDelegate.getInstitution(instid);
		Physicalspacetype physicalspacetype = physiSpaceTyDelegate.getPhysicalSpaceType(physptyid);
		model.addAttribute("institution", institution);
		model.addAttribute("physicalspacetype", physicalspacetype);
		return "institutions/show_inst_information";
	}
}
