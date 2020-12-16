package com.example.main.controllers;

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

import com.example.main.delegate.interfaces.HatParameterDelegate;
import com.example.main.delegate.interfaces.InstitutionDelegate;
import com.example.main.model.HatParameter;
import com.example.main.services.implementations.HatParameterServiceImpl;
import com.example.main.services.implementations.InstitutionServiceImpl;
import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;
import javax.validation.groups.Default;

@Controller
public class HatParameterController {

	private InstitutionDelegate instDelegate;
	private HatParameterDelegate hatParameterDelegate;

	@Autowired
	public HatParameterController(InstitutionDelegate instDelegate,
			HatParameterDelegate hatParameterDelegate) {

		this.instDelegate = instDelegate;
		this.hatParameterDelegate = hatParameterDelegate;

	}

	@GetMapping("/hatParameters/")
	public String indexHatParameters(Model model) {
		model.addAttribute("hatparameters", hatParameterDelegate.getAllHatParameters());
		return "hatParameters/index";
	}

	@GetMapping("/hatParameters/add")
	public String showSaveHatParameter(Model model) {
		model.addAttribute("hatparameter", new HatParameter());
		model.addAttribute("institutions", instDelegate.getAllInstitutions());

		return "hatParameters/add_hatParameter_1";
	}

	@PostMapping("/hatParameters/add")
	public String saveHatParameter1(
			@Validated({ FirstGroup.class, Default.class }) @ModelAttribute("hatparameter") HatParameter hatparameter,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (bindingResult.hasErrors() && !action.equals("Cancel")) {
			return "hatParameters/add_hatParameter_1";
		} else {
			if (!action.equals("Cancel")) {
				model.addAttribute("institutions", instDelegate.getAllInstitutions());
			}
			return "hatParameters/add_hatParameter_2";
		}
	}

	@PostMapping("/hatParameters/add1")
	public String saveHatParameter2(
			@Validated({ SecondGroup.class, Default.class }) @ModelAttribute("hatparameter") HatParameter hatparameter,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (bindingResult.hasErrors() && !(action.equals("Cancel"))) {
			model.addAttribute("institutions", instDelegate.getAllInstitutions());
			return "hatParameters/add_hatParameter_2";
		} else {
			if (!action.equals("Cancel")) {
				hatParameterDelegate.createHatParameter(hatparameter);
			}
		}
		return "redirect:/hatParameters/";
	}

	@GetMapping("/hatParameters/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		HatParameter hatParameter = hatParameterDelegate.getHatParameter(id);
		if (hatParameter == null)
			throw new IllegalArgumentException("Invalid Hat Parameter Id:" + id);
		model.addAttribute("hatparameter", hatParameter);
		model.addAttribute("institutions", instDelegate.getAllInstitutions());
		return "hatParameters/update_hatParameter";
	}

	@PostMapping("/hatParameters/edit/{id}")
	public String updateHatParameter(@PathVariable("id") Long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated @ModelAttribute("hatparameter") HatParameter hatparameter, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("institutions", instDelegate.getAllInstitutions());
			return "hatParameters/update_hatParameter";
		} else {
			if (action != null && !action.equals("Cancel")) {
				hatParameterDelegate.updateHatParameter(id, hatparameter);
				model.addAttribute("hatparameters", hatParameterDelegate.getAllHatParameters());
			}
			return "redirect:/hatParameters/";
		}

	}

	@GetMapping("/hatParameters/del/{id}")
	public String deleteHatParameter(@PathVariable("id") long id, Model model) {
		hatParameterDelegate.deleteHatParameter(id);
		model.addAttribute("hatparameters", hatParameterDelegate.getAllHatParameters());
		return "hatParameters/index";
	}

	@GetMapping("/hatParameters/info/{id}")
	public String showInformation(@PathVariable("id") long id, Model model) {
		HatParameter hatParameter = hatParameterDelegate.getHatParameter(id);
		model.addAttribute("hatparameter", hatParameter);
		return "hatParameters/show_info";
	}

}
