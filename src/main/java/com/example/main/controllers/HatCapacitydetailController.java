package com.example.main.controllers;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.main.delegate.interfaces.HatCapacityDelegate;
import com.example.main.delegate.interfaces.InstitutionCampusDelegate;
import com.example.main.model.HatCapacitydetail;
import com.example.main.services.interfaces.HatCapacitydetailService;
import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;

@Controller
public class HatCapacitydetailController {

	private HatCapacityDelegate hatCapacityDelegate;
	private InstitutionCampusDelegate instCampusDelegate;

	@Autowired
	public HatCapacitydetailController(HatCapacityDelegate hatCapacityDelegate,
			InstitutionCampusDelegate instCampusDelegate) {
		this.hatCapacityDelegate = hatCapacityDelegate;
		this.instCampusDelegate = instCampusDelegate;
	}

	@GetMapping("/campusCapacities")
	public String indexCampusCapacities(Model model) {
		model.addAttribute("campuscapacities", hatCapacityDelegate.getAllCapacities());
		return "campusCapacities/index";
	}

	@GetMapping("/campusCapacities/add")
	public String showSaveCampusCapacity(Model model) {
		model.addAttribute("campuscapacity", new HatCapacitydetail());
		model.addAttribute("institutioncampuses", instCampusDelegate.getAllCampus());
		return "campusCapacities/add_campusCapacity_1";
	}

	@PostMapping("/campusCapacities/add")
	public String saveCampusCapacity1(
			@Validated({ FirstGroup.class,
					Default.class }) @ModelAttribute("campuscapacity") HatCapacitydetail campuscapacity,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {

		if (bindingResult.hasErrors() && !action.equals("Cancel")) {
			return "campusCapacities/add_campusCapacity_1";
		} else {
			if (!action.equals("Cancel")) {
				model.addAttribute("institutioncampuses", instCampusDelegate.getAllCampus());
				return "campusCapacities/add_campusCapacity_2";
			}
			return "redirect:/campusCapacities";
		}

	}

	@PostMapping("/campusCapacities/add1")
	public String saveCampusCapacity2(
			@Validated({ SecondGroup.class,
					Default.class }) @ModelAttribute("campuscapacity") HatCapacitydetail campuscapacity,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (bindingResult.hasErrors() && !action.equals("Cancel")) {
			model.addAttribute("institutioncampuses", instCampusDelegate.getAllCampus());
			return "campusCapacities/add_campusCapacity_2";
		} else {
			if (!action.equals("Cancel")) {
				hatCapacityDelegate.createCapacity(campuscapacity);
			}
			return "redirect:/campusCapacities/";
		}
	}
	
	@GetMapping("/campusCapacities/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		HatCapacitydetail capacity = hatCapacityDelegate.getCapacity(id);
		if (capacity == null)
			throw new IllegalArgumentException("Invalid Capacity Id:" + id);
		model.addAttribute("campuscapacity", capacity);
		model.addAttribute("institutioncampuses", instCampusDelegate.getAllCampus());
		return "campusCapacities/update_campusCapacity";
	}

	@PostMapping("/campusCapacities/edit/{id}")
	public String updateCampusCapacity(@PathVariable("id") Long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated @ModelAttribute("campuscapacity") HatCapacitydetail capacity, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("institutioncampuses", instCampusDelegate.getAllCampus());
			return "campusCapacities/update_campusCapacity";
		} else {
			if (action != null && !action.equals("Cancel")) {
				hatCapacityDelegate.updateCapacity(id, capacity);
				model.addAttribute("campuscapacities", hatCapacityDelegate.getAllCapacities());
			}
			return "redirect:/campusCapacities/";
		}

	}

	@GetMapping("/campusCapacities/del/{id}")
	public String deleteHatParameter(@PathVariable("id") long id, Model model) {
		hatCapacityDelegate.deleteCapacity(id);
		model.addAttribute("campuscapacities", hatCapacityDelegate.getAllCapacities());
		return "campusCapacities/index";
	}

	@GetMapping("/campusCapacities/info/{id}")
	public String showInformation(@PathVariable("id") long id, Model model) {
		HatCapacitydetail campuscapacity = hatCapacityDelegate.getCapacity(id);
		model.addAttribute("campuscapacity", campuscapacity);
		return "campusCapacities/show_info";
	}

}
