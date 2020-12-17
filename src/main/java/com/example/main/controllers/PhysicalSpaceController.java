package com.example.main.controllers;

import java.util.Date;
import java.util.List;
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

import com.example.main.delegate.interfaces.InstitutionCampusDelegate;
import com.example.main.delegate.interfaces.PhysicalSpaceDelegate;
import com.example.main.delegate.interfaces.PhysicalSpaceTypeDelegate;
import com.example.main.exceptions.No5DigitsExternalIDException;
import com.example.main.model.Physicalspace;
import com.example.main.validation.FirstGroup;
import com.example.main.validation.SecondGroup;

@Controller
public class PhysicalSpaceController {

	private PhysicalSpaceDelegate physicalSpaceDelegate;
	private InstitutionCampusDelegate instCampusDelegate;
	private PhysicalSpaceTypeDelegate physSpacTypeDelegate;

	@Autowired
	public PhysicalSpaceController(PhysicalSpaceDelegate physicalSpaceDelegate,
			InstitutionCampusDelegate instCampusDelegate, PhysicalSpaceTypeDelegate physSpacTypeDelegate) {
		this.physicalSpaceDelegate = physicalSpaceDelegate;
		this.instCampusDelegate = instCampusDelegate;
		this.physSpacTypeDelegate = physSpacTypeDelegate;
	}

	@GetMapping("/physicalSpaces")
	public String indexPhySpaces(Model model) {
		model.addAttribute("physicalspaces", physicalSpaceDelegate.getAllPhysicalSpaces());
		return "physicalSpaces/index";
	}

	@GetMapping("/physicalSpaces/add")
	public String showSavePhysSpace(Model model) {
		model.addAttribute("physicalspace", new Physicalspace());
		return "physicalSpaces/add_physicalSpace_1";
	}

	@PostMapping("/physicalSpaces/add")
	public String savePhysicalSpace1(@Validated({ FirstGroup.class, Default.class }) Physicalspace physicalspace,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (bindingResult.hasErrors() && !action.equals("Cancel")) {
			return "physicalSpaces/add_physicalSpace_1";
		} else {
			if (!action.equals("Cancel")) {
				model.addAttribute("institutioncampuses", instCampusDelegate.getAllCampus());
				model.addAttribute("physicalspacetypes", physSpacTypeDelegate.getAllPhysicalSpaceTypes());
				return "physicalSpaces/add_physicalSpace_2";
			}
			return "redirect:/physicalSpaces";
		}
	}

	@PostMapping("/physicalSpaces/add1")
	public String savePhysicalSpace2(@Validated({ SecondGroup.class, Default.class }) Physicalspace physicalspace,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action)
			throws NumberFormatException, NoSuchElementException, No5DigitsExternalIDException {
		if (bindingResult.hasErrors() && !action.equals("Cancel")) {
			model.addAttribute("institutioncampuses", instCampusDelegate.getAllCampus());
			model.addAttribute("physicalspacetypes", physSpacTypeDelegate.getAllPhysicalSpaceTypes());
			return "physicalSpaces/add_physicalSpace_2";
		} else {
			if (!action.equals("Cancel")) {
				physicalSpaceDelegate.createPhysicalSpace(physicalspace);
			}
			return "redirect:/physicalSpaces/";
		}
	}

	@GetMapping("/physicalSpaces/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Physicalspace physicalspace = physicalSpaceDelegate.getPhysicalSpace(id);
		if (physicalspace == null)
			throw new IllegalArgumentException("Invalid physical space Id:" + id);
		model.addAttribute("physicalspace", physicalspace);
		return "physicalSpaces/update_physicalSpace_1";
	}

	@PostMapping("/physicalSpaces/edit/{id}")
	public String updatePhysicalSpace1(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated({ FirstGroup.class, Default.class }) Physicalspace physicalspace, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "physicalSpaces/update_physicalSpace_1";
		} else {
			if (action != null && !action.equals("Cancel")) {
				model.addAttribute("institutioncampuses", instCampusDelegate.getAllCampus());
				model.addAttribute("physicalspacetypes", physSpacTypeDelegate.getAllPhysicalSpaceTypes());
				return "physicalSpaces/update_physicalSpace_2";
			}
			return "redirect:/physicalSpaces/";
		}
	}

	@PostMapping("/physicalSpaces/edit1/{id}")
	public String updatePhysicalSpace2(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated({ SecondGroup.class, Default.class }) Physicalspace physicalspace, BindingResult bindingResult,
			Model model) throws NumberFormatException, NoSuchElementException, No5DigitsExternalIDException {
		if (bindingResult.hasErrors() && !action.equals("Cancel")) {
			model.addAttribute("institutioncampuses", instCampusDelegate.getAllCampus());
			model.addAttribute("physicalspacetypes", physSpacTypeDelegate.getAllPhysicalSpaceTypes());
			return "physicalSpaces/update_physicalSpace_2";
		} else {
			if (action != null && !action.equals("Cancel")) {
				physicalSpaceDelegate.updatePhysicalSpace(id, physicalspace);
				;
			}
			return "redirect:/physicalSpaces/";
		}
	}

	@GetMapping("/physicalSpaces/del/{id}")
	public String deletePhysicalSpace(@PathVariable("id") long id, Model model) {
		physicalSpaceDelegate.deletePhysicalSpace(id);
		;
		model.addAttribute("physicalspaces", physicalSpaceDelegate.getAllPhysicalSpaces());
		return "physicalSpaces/index";
	}

	@GetMapping("/physicalSpaces/info/{id}")
	public String showInformation(@PathVariable("id") long id, Model model) {
		Physicalspace physicalspace = physicalSpaceDelegate.getPhysicalSpace(id);
		model.addAttribute("physicalspace", physicalspace);
		return "physicalSpaces/show_info";
	}

	@GetMapping("/physicalSpaces/startDate={sDay}?{sMonth}?{sYear}&endDate={eDay}?{eMonth}?{eYear}")
	public String findPhysicalSpacesWithADateRange(Model model, @PathVariable("sDay") int sDay,
			@PathVariable("sMonth") int sMonth, @PathVariable("sYear") int sYear, @PathVariable("eDay") int eDay,
			@PathVariable("eMonth") int eMonth, @PathVariable("eYear") int eYear) {

		List<Physicalspace> ps = physicalSpaceDelegate.findPhysicalSpacesWithADateRange(sDay, sMonth, sYear, eDay,
				eMonth, eYear);
		model.addAttribute("physicalspaces", ps);

		return "/query1";

	}
}
