package com.credit.webcredit.service;

import com.credit.webcredit.dto.BankDTO;
import com.credit.webcredit.dto.HouseHoldDTO;
import com.credit.webcredit.dto.ProfileDto;
import com.credit.webcredit.dto.UsersDto;
import com.credit.webcredit.entity.Profile;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

public interface ProfileService {
    Profile getImgUser(Long id);

    String savePersonalInformation(ProfileDto profileDto, Model model) throws ParseException;

    String saveCmndOrCccd(MultipartFile multipartFile1, MultipartFile multipartFile2, MultipartFile multipartFile3, ProfileDto profileDto, Model model) throws IOException;

    String saveBank(BankDTO bankDTO, Model model);

    BankDTO findBankByProfile();

    HouseHoldDTO findFormHouseHold();

    String houseHoldDTO(HouseHoldDTO houseHoldDTO, MultipartFile imgHouseholdLeft, Model model) throws IOException;

    String changePasswordProcessing(HttpServletRequest request, Model model) throws ServletException;

    void saveProfile(Profile profile);

    String saveLoanApplicationForm(ProfileDto profileDto, Model model);

    ProfileDto getLoanApplication(Model model);
}
