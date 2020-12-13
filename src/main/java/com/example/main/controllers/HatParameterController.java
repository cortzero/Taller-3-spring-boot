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

import com.example.main.model.HatParameter;
import com.example.main.services.implementations.HatParameterServiceImpl;
import com.example.main.services.implementations.InstitutionServiceImpl;
import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;
import javax.validation.groups.Default;

@Controller
public class HatParameterController {

	private InstitutionServiceImpl institutionService;
	private HatParameterServiceImpl hatParameterService;

	@Autowired
	public HatParameterController(InstitutionServiceImpl institutionService,
			HatParameterServiceImpl hatParameterService) {

		this.institutionService = institutionService;
		this.hatParameterService = hatParameterService;

	}

	@GetMapping("/hatParameters")
	public String indexHatParameters(Model model) {
		model.addAttribute("hatparameters", hatParameterService.findAll());
		return "hatParameters/index";
	}

	@GetMapping("/hatParameters/add")
	public String showSaveHatParameter(Model model) {
		model.addAttribute("hatparameter", new HatParameter());
		model.addAttribute("institutions", institutionService.findAll());

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
				model.addAttribute("institutions", institutionService.findAll());
			}
			return "hatParameters/add_hatParameter_2";
		}
	}

	@PostMapping("/hatParameters/add1")
	public String saveHatParameter2(
			@Validated({ SecondGroup.class, Default.class }) @ModelAttribute("hatparameter") HatParameter hatparameter,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (bindingResult.hasErrors() && !(action.equals("Cancel"))) {
			model.addAttribute("institutions", institutionService.findAll());
			return "hatParameters/add_hatParameter_2";
		} else {
			if (!action.equals("Cancel")) {
				hatParameterService.save(hatparameter);
			}
		}
		return "redirect:/hatParameters/";
	}

	@GetMapping("/hatParameters/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		HatParameter hatParameter = hatParameterService.findById(id);
		if (hatParameter == null)
			throw new IllegalArgumentException("Invalid Hat Parameter Id:" + id);
		model.addAttribute("hatparameter", hatParameter);
		model.addAttribute("institutions", institutionService.findAll());
		return "hatParameters/update_hatParameter";
	}

	@PostMapping("/hatParameters/edit/{id}")
	public String updateHatParameter(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated @ModelAttribute("hatparameter") HatParameter hatparameter, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("institutions", institutionService.findAll());
			return "hatParameters/update_hatParameter";
		} else {
			if (action != null && !action.equals("Cancel")) {
				hatParameterService.update(hatparameter);
				model.addAttribute("hatparameters", hatParameterService.findAll());
			}
			return "redirect:/hatParameters/";
		}

	}

	@GetMapping("/hatParameters/del/{id}")
	public String deleteHatParameter(@PathVariable("id") long id, Model model) {
		HatParameter hatParameter = hatParameterService.findById(id);
		hatParameterService.delete(hatParameter);
		model.addAttribute("hatparameters", hatParameterService.findAll());
		return "hatParameters/index";
	}

	@GetMapping("/hatParameters/info/{id}")
	public String showInformation(@PathVariable("id") long id, Model model) {
		HatParameter hatParameter = hatParameterService.findById(id);
		model.addAttribute("hatparameter", hatParameter);
		return "hatParameters/show_info";
	}

}
