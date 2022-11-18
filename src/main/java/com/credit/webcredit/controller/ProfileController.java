package com.credit.webcredit.controller;

import com.credit.webcredit.dto.BankDTO;
import com.credit.webcredit.dto.HouseHoldDTO;
import com.credit.webcredit.dto.ProfileDto;
import com.credit.webcredit.dto.UsersDto;
import com.credit.webcredit.entity.Profile;
import com.credit.webcredit.entity.Users;
import com.credit.webcredit.enums.CardCitizen;
import com.credit.webcredit.security.MyUserDetails;
import com.credit.webcredit.service.BankService;
import com.credit.webcredit.service.ProfileService;
import com.credit.webcredit.service.UsersService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/profile/")
public class ProfileController {
    private final ProfileService profileService;

    private final UsersService usersService;

    private final BankService bankService;

    public ProfileController(ProfileService profileService, UsersService usersService, BankService bankService) {
        this.profileService = profileService;
        this.usersService = usersService;
        this.bankService = bankService;
    }

    @GetMapping("/myInformation")
    public String myInformation(){

        return "users/myInformation";
    }
    @GetMapping("/personalInformation")
    public String personalInformation(Model model){
        Long userId = usersService.getUserId();
        Profile profile = profileService.getImgUser(userId);
        if (profile == null){
            model.addAttribute("profile", new ProfileDto());
        }else {
            model.addAttribute("profile",profile);
        }
        return "users/personalInformation";
    }

    @PostMapping("/savePersonalInformation")
    public String savePersonalInformation(@ModelAttribute("profile") ProfileDto profileDto,Model model) throws ParseException {
        return profileService.savePersonalInformation(profileDto, model);
//        return "users/personalInformation";
    }

    @GetMapping("/cmndCccd")
    public String cmndCccd(Model model){
        Long userId = usersService.getUserId();
        Profile profile = profileService.getImgUser(userId);
        List<String> list = new ArrayList<>();
        list.add(CardCitizen.CCCD.name());
        list.add(CardCitizen.CMND.name());
        model.addAttribute("cccdCmndLst", list);
        ProfileDto profileDto = new ProfileDto();
        profileDto.setFullName(profile.getFullName());
        if (!Strings.isEmpty(profile.getImgCard1()) && !Strings.isEmpty(profile.getImgCard2())
        && !Strings.isEmpty(profile.getImgUsers())){
            profileDto.setImgCard1(profile.getImgCard1());
            profileDto.setImgCard2(profile.getImgCard2());
            profileDto.setImgUsers(profile.getImgUsers());
        }
        if (!Strings.isEmpty(profile.getNumberCccdCmnd())){
            profileDto.setNumberCccdCmnd(profile.getNumberCccdCmnd());
        }
        if (profile.getCardCitizen() != null){
            profileDto.setCccdCmnd(profile.getCardCitizen());
        }
        model.addAttribute("profile", profileDto);
        return "users/cmndCccd";
    }

    @PostMapping("/saveCmndOrCccd")
    public String saveCmndOrCccd(@ModelAttribute("profile") ProfileDto profileDto, Model model,
                           @RequestParam("image1") MultipartFile multipartFile1,
                           @RequestParam("image2") MultipartFile multipartFile2,
                           @RequestParam("image3") MultipartFile multipartFile3) throws IOException {
        return profileService.saveCmndOrCccd(multipartFile1, multipartFile2, multipartFile3, profileDto, model);
    }

    @GetMapping("/formBank")
    public String formBank(Model model){
        model.addAttribute("bankDTO", profileService.findBankByProfile());
        model.addAttribute("banks", bankService.findAllBank());
        return "users/formBank";
    }

    //save bank
    @PostMapping("/saveBank")
    public String saveBank(@ModelAttribute("bankDTO") BankDTO bankDTO, Model model){
        model.addAttribute("bankDTO", profileService.findBankByProfile());
        model.addAttribute("banks", bankService.findAllBank());
        return profileService.saveBank(bankDTO, model);
    }

    @GetMapping("/formHousehold")
    public String formHousehold(Model model){
        model.addAttribute("houseHoldDTO", profileService.findFormHouseHold());
        return "users/formHousehold";
    }

    @PostMapping("/saveHouseHold")
    public String saveHouseHold(@ModelAttribute("houseHoldDTO") HouseHoldDTO houseHoldDTO, Model model,
                                @RequestParam("imgHouseholdLefts") MultipartFile imgHouseholdLeft) throws IOException{
        model.addAttribute("houseHoldDTO", profileService.findFormHouseHold());
        return profileService.houseHoldDTO(houseHoldDTO, imgHouseholdLeft,model);
    }
    @GetMapping("/changePassword")
    public String changePassword(Model model){
        model.addAttribute("users",new UsersDto());
        return "users/changePassword";
    }

    @PostMapping("/changePassword")
    public String processChangePassword(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException {
       return profileService.changePasswordProcessing(request,model);
    }
    @GetMapping("/resetPassword")
    public String user(Model model){
       // model.addAttribute("resetPassword",new UsersDto());
        return "resetPassword";
    }
    @PostMapping(value = "/sendEmailPassword")
    public String sendEmailPassword(@RequestParam("email") String email, Model model){
        return usersService.sendEmailPassword(email, model);
    }

    @GetMapping("/loanApplicationForm")
    public String loanApplicationForm(Model model){
        model.addAttribute("loanApplicationForm", profileService.getLoanApplication(model));
        return "users/loanApplicationForm";
    }
    @PostMapping("/saveLoanApplicationForm")
    public String saveLoanApplicationForm(@ModelAttribute("loanApplicationForm") ProfileDto profileDto, Model model){
        profileService.saveLoanApplicationForm(profileDto,model);
        return profileService.saveLoanApplicationForm(profileDto,model);
    }
    @GetMapping("/wallet")
    public String myLoan(){
        return "users/wallet";
    }

    @GetMapping("/wallet-register")
    public String walletRegister(){
        return "users/wallet-register";
    }
    @PostMapping("/saveWallet")
    public String saveWallet(){
        return "users/wallet-register";
    }
    @GetMapping("/payMyDebt")
    public String payMyDebt(){
        return "users/payMyDebt";
    }
}
