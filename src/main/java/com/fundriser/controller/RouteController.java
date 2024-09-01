package com.fundriser.controller;

import com.fundriser.models.DonationsModel;
import com.fundriser.models.FundriserModel;
import com.fundriser.models.UserModel;
import com.fundriser.service.DonationService;
import com.fundriser.service.FundraiserService;
import com.fundriser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class RouteController {

    @Autowired
    private UserService userService;

    @Autowired
    private FundraiserService fundraiserService;

    @Autowired
    private DonationService donationService;

    @RequestMapping("/landing")
    public ModelAndView landingPage(){
        return new ModelAndView("landing");
    }

    @RequestMapping("/login")
    public ModelAndView loginPage(){
        return new ModelAndView("login");
    }

    @PostMapping("/login-user")
    @ResponseBody
    public UserModel loginUser(@ModelAttribute UserModel userData) {
        try {
            UserModel user = userService.findByEmail(userData.getEmail());
            if (user != null && user.getPassword().equals(userData.getPassword())) {
                return user; // Returning user as JSON
            }
            return null;
        } catch (Exception e) {
            // Handle exceptions appropriately
            return null;
        }
    }

    @RequestMapping("/register")
    public ModelAndView registerPage(){
        return new ModelAndView("register");
    }

    @PostMapping("/register-user")
    @ResponseBody
    public UserModel registerUser(@ModelAttribute UserModel userData) {
        try {
            return userService.saveUser(userData); // Save and return the user
        } catch (Exception e) {
            // Handle exceptions appropriately
            return null;
        }
    }

    @RequestMapping("/")
    public ModelAndView userHomePage(){
        List<FundriserModel> fundrisers = fundraiserService.findAllFundraisers();
        return new ModelAndView("user/user-home", "fundrisers", fundrisers);
    }

    @RequestMapping("/new-fundriser")
    public ModelAndView newFundPage(){
        return new ModelAndView("user/new-fundriser");
    }

    @PostMapping("/add-fundraiser")
    @ResponseBody
    public FundriserModel addNewFundraiser(@ModelAttribute FundriserModel fundraiserData){
        fundraiserData.setCollectedAmount(0);
        return fundraiserService.saveFundraiser(fundraiserData); // Save and return the fundraiser
    }

    @RequestMapping("/my-fundrisers/{id}")
    public ModelAndView myFundPage(@PathVariable("id") String id){
        List<FundriserModel> filteredFundrisers = fundraiserService.findAllFundraisers().stream()
                .filter(fundriser -> id.equals(fundriser.getApplicantId()))
                .collect(Collectors.toList());
        return new ModelAndView("user/my-fundrisers", "fundrisers", filteredFundrisers);
    }

    @RequestMapping("/fundriser/{id}")
    public ModelAndView fundPage(@PathVariable("id") String id){
        Optional<FundriserModel> fundriserData = fundraiserService.findById(id);
        if (!fundriserData.isPresent()) {
            return new ModelAndView("error", "message", "Fundraiser not found");
        }

        FundriserModel fundriser = fundriserData.get();
        List<DonationsModel> filteredDonations = donationService.findAllDonations().stream()
                .filter(donation -> id.equals(donation.getFundriserId()))
                .collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView("user/user-fundriser");
        modelAndView.addObject("fundriser", fundriser);
        modelAndView.addObject("donations", filteredDonations);
        return modelAndView;
    }

    @RequestMapping("/admin-fundriser/{id}")
    public ModelAndView adminFundPage(@PathVariable("id") String id){
        Optional<FundriserModel> fundriserData = fundraiserService.findById(id);
        if (!fundriserData.isPresent()) {
            return new ModelAndView("error", "message", "Fundraiser not found");
        }

        FundriserModel fundriser = fundriserData.get();
        List<DonationsModel> filteredDonations = donationService.findAllDonations().stream()
                .filter(donation -> id.equals(donation.getFundriserId()))
                .collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView("admin/admin-fundriser");
        modelAndView.addObject("fundriser", fundriser);
        modelAndView.addObject("donations", filteredDonations);
        return modelAndView;
    }

    @RequestMapping("/update-fundriser/{id}")
    public ModelAndView updateFundPage(@PathVariable("id") String id){
        Optional<FundriserModel> fundriserInfo = fundraiserService.findById(id);
        if (!fundriserInfo.isPresent()) {
            return new ModelAndView("error", "message", "Fundraiser not found");
        }
        FundriserModel fundriser = fundriserInfo.get();
        return new ModelAndView("user/update-fundriser", "fundriser", fundriser);
    }

    @PostMapping("/update-fund")
    @ResponseBody
    public FundriserModel updateFundraiser(@ModelAttribute FundriserModel fundraiserData){
        Optional<FundriserModel> fundriserInfo = fundraiserService.findById(fundraiserData.get_id());
        if (!fundriserInfo.isPresent()) {
            return null; // Handle error appropriately
        }

        FundriserModel fundriser = fundriserInfo.get();
        fundriser.setApplicantName(fundraiserData.getApplicantName());
        fundriser.setApplicantEmail(fundraiserData.getApplicantEmail());
        fundriser.setApplicantMobile(fundraiserData.getApplicantMobile());
        fundriser.setFundriserPurpose(fundraiserData.getFundriserPurpose());
        fundriser.setTargetAmount(fundraiserData.getTargetAmount());
        fundriser.setDescription(fundraiserData.getDescription());
        fundriser.setStatus(fundraiserData.getStatus());
        return fundraiserService.saveFundraiser(fundriser); // Save and return updated fundraiser
    }

    @RequestMapping("/add-donation/{id}")
    public ModelAndView addDonationPage(@PathVariable("id") String id){
        FundriserModel fundriser = fundraiserService.findById(id).orElse(null);
        if (fundriser == null) {
            return new ModelAndView("error", "message", "Fundraiser not found");
        }
        return new ModelAndView("user/add-donation", "fundriser", fundriser);
    }

    @PostMapping("/add-donation")
    @ResponseBody
    public DonationsModel addDonation(@ModelAttribute DonationsModel donationData){
        return donationService.saveDonation(donationData); // Save and return the donation
    }

    @RequestMapping("/admin-dashboard")
    public ModelAndView adminDashboard(){
        List<UserModel> users = userService.findAllUsers();
        List<FundriserModel> fundrisers = fundraiserService.findAllFundraisers();
        List<DonationsModel> donations = donationService.findAllDonations();

        ModelAndView modelAndView = new ModelAndView("admin/dashboard");
        modelAndView.addObject("users", users);
        modelAndView.addObject("fundrisers", fundrisers);
        modelAndView.addObject("donations", donations);
        return modelAndView;
    }

    @RequestMapping("/all-users")
    public ModelAndView allUsersPage(){
        List<UserModel> users = userService.findAllUsers();
        return new ModelAndView("admin/all-users", "users", users);
    }

    @RequestMapping("/all-fundrisers")
    public ModelAndView allFundrisersPage(){
        List<FundriserModel> fundrisers = fundraiserService.findAllFundraisers();
        return new ModelAndView("admin/all-fundrisers", "fundrisers", fundrisers);
    }

    @RequestMapping("/all-donations")
    public ModelAndView allDonationsPage(){
        List<DonationsModel> donations = donationService.findAllDonations();
        return new ModelAndView("admin/all-donations", "donations", donations);
    }
}
