package com.credit.webcredit.service;

import com.credit.webcredit.dto.BankDTO;
import com.credit.webcredit.dto.HouseHoldDTO;
import com.credit.webcredit.dto.ProfileDto;
import com.credit.webcredit.entity.Profile;
import com.credit.webcredit.entity.Users;
import com.credit.webcredit.enums.CardCitizen;
import com.credit.webcredit.enums.TypeLoan;
import com.credit.webcredit.repository.ProfileRepository;
import com.credit.webcredit.utils.StringUtil;
import com.credit.webcredit.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final Logger logger = LogManager.getLogger(ProfileServiceImpl.class);

    private final ProfileRepository profileRepository;

    private final UsersService usersService;

    public ProfileServiceImpl(ProfileRepository profileRepository, UsersService usersService) {
        this.profileRepository = profileRepository;
        this.usersService = usersService;
    }

    @Override
    public Profile getImgUser(Long id) {
        return id == null ? null : profileRepository.getImgUser(id);
    }

    @Override
    public String savePersonalInformation(@ModelAttribute("profile") ProfileDto profileDto, Model model) throws ParseException {
        Long userId = usersService.getUserId();
        Profile profile = profileRepository.getImgUser(userId);
        if (!validatePersonalInformation(profileDto, model)) {
            profile.setFullName(profileDto.getFullName());
            profile.setAge(profileDto.getAge());
            profile.setAddress(profileDto.getAddress());
            profile.setDateOfBirth(profileDto.getDateOfBirth());
            profile.setPhone(profileDto.getPhone());
            profile.setGender(profileDto.getGender());
            profile.setUserId(userId);
            profileRepository.save(profile);
            return "redirect:/profile/personalInformation";
        }
        return "users/personalInformation";
    }

    public boolean validatePersonalInformation(ProfileDto profileDto, Model model) throws ParseException {
        if (profileDto.getFullName().isEmpty()) {
            model.addAttribute("errFullName", "Họ tên không được để trống!");
            return true;
        }
        if (profileDto.getAge() == null) {
            model.addAttribute("errAge", "Tuổi không được để trống!");
            return true;
        } else if (profileDto.getAge() < 18 || profileDto.getAge() > 40) {
            model.addAttribute("errAge", "Tuổi phải từ 18 đến 40!");
            return true;
        }
        long millis = System.currentTimeMillis();
        //Ngày hiện tại
        Date date = new Date(millis);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = formatter.format(date);
        //Ngày nhập từ form
        DateFormat formatterAge = new SimpleDateFormat("yyyyMMdd");
        int convertAge = Integer.parseInt(formatterAge.format(date));
        Date dateOfBirth = formatterAge.parse(profileDto.getDateOfBirth());
        int convertAgeDate = Integer.parseInt(formatterAge.format(dateOfBirth));
        //Tính tuổi
        int age = (convertAge - convertAgeDate) / 10000;
        if (profileDto.getDateOfBirth().isEmpty()) {
            model.addAttribute("errDateOfBirth", "Ngày sinh không được để trống!");
            return true;
        } else if (profileDto.getDateOfBirth().compareTo(stringDate) > 0 || age != profileDto.getAge()) {
            model.addAttribute("errDateOfBirth", "Ngày sinh không đúng!");
            return true;
        }
        String VALID_PHONE = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
        Pattern patternPhone = Pattern.compile(VALID_PHONE);
        Matcher matcherPhone = patternPhone.matcher(profileDto.getPhone());
        if (profileDto.getPhone().isEmpty()) {
            model.addAttribute("errPhone", "Số điện thoại không được để trống!");
            return true;
        } else if (!matcherPhone.matches()) {
            model.addAttribute("errPhone", "Số điện thoại không đúng!");
            return true;
        }
        if (profileDto.getAddress().isEmpty()) {
            model.addAttribute("errAddress", "Địa chỉ không được để trống!");
            return true;
        }
        return false;
    }

    @Override
    public String saveCmndOrCccd(MultipartFile multipartFile1, MultipartFile multipartFile2, MultipartFile multipartFile3, ProfileDto profileDto, Model model) throws IOException {
        String imgCard1 = StringUtils.cleanPath(Objects.requireNonNull(multipartFile1.getOriginalFilename()));
        String imgCard2 = StringUtils.cleanPath(Objects.requireNonNull(multipartFile2.getOriginalFilename()));
        String imgUsers = StringUtils.cleanPath(Objects.requireNonNull(multipartFile3.getOriginalFilename()));

        Long userId = usersService.getUserId();

        String VALID_NAME = "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶ" +
                "ẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ" +
                "ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";
        Pattern patternName = Pattern.compile(VALID_NAME);
        Matcher matcherName = patternName.matcher(profileDto.getFullName());
        if (profileDto.getFullName().isEmpty()) {
            model.addAttribute("errFullName", "Họ tên không được để trống!");
            return "users/cmndCccd";
        } else if (!matcherName.matches()) {
            model.addAttribute("errFullName", "Họ tên không úng định dạng!");
            return "users/cmndCccd";
        }

        String cccdCmnd = "";
        if (profileDto.getCccdCmnd().equals(CardCitizen.CCCD)) {
            cccdCmnd = "^((001|002|004|006|008|010|011|012|014|015|017|019|020|022|024|025|026|027|030|031|033|034|035|036|037|038|040|042|044|045|046|048|" +
                    "049|051|052|054|056|058|060|062|064|066|067|068|070|072|074|075|077|079|080|082|083|084|086|087|089|091|092|093|094|095|096)[0-9]{9})$";
        } else {
            cccdCmnd = "^((01|02|03|04|05|06|07|08|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|29|30|31|32|33|34|35|36|37|38)([0-9]{7}))|((090|091|092|095|230|231|245|280|281|285)([0-9]{6}))$";
        }

        Pattern patternCccd = Pattern.compile(cccdCmnd);
        Matcher matcherCccd = patternCccd.matcher(profileDto.getNumberCccdCmnd());
        if (profileDto.getNumberCccdCmnd().isEmpty()) {
            model.addAttribute("errCmndCccd", "Số CMND/CCCD không được để trống!");
            return "users/cmndCccd";
        }
        if (profileDto.getNumberCccdCmnd().isEmpty() && !matcherCccd.matches()) {
            model.addAttribute("errCmndCccd", "Số CMND/CCCD không đúng!");
            return "users/cmndCccd";
        }

        if (StringUtils.isEmpty(imgCard1) || StringUtils.isEmpty(imgCard2) || StringUtils.isEmpty(imgUsers)) {
            model.addAttribute("errImg", "Bạn chưa chọn đủ ảnh!");
            return "users/cmndCccd";
        }

        String uploadDir = StringUtil.DIR_NAME + userId;
        Utils.saveFile(uploadDir, imgCard1, multipartFile1);
        Utils.saveFile(uploadDir, imgCard2, multipartFile2);
        Utils.saveFile(uploadDir, imgUsers, multipartFile3);

        Profile profile = profileRepository.getImgUser(userId);
        profile.setFullName(profileDto.getFullName());
        profile.setNumberCccdCmnd(profileDto.getNumberCccdCmnd());
        profile.setCardCitizen(profileDto.getCccdCmnd());
        profile.setImgCard1("/" + uploadDir + "/" + imgCard1);
        profile.setImgCard2("/" + uploadDir + "/" + imgCard2);
        profile.setImgUsers("/" + uploadDir + "/" + imgUsers);
        profile.setUserId(userId);
        profileRepository.save(profile);
        return "redirect:/profile/cmndCccd";
    }

    @Override
    @Transactional
    public String saveBank(BankDTO bankDTO, Model model) {
        Long userId = usersService.getUserId();
        Profile profile = profileRepository.getImgUser(userId);
        if (profile == null) {
            logger.debug("Null Profile ---- " + logger.getName());
            throw new NullPointerException("Null Profile");
        }
        String VALID_NAME = "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶ" +
                "ẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ" +
                "ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";
        Pattern patternName = Pattern.compile(VALID_NAME);
        Matcher matcherName = patternName.matcher(bankDTO.getNameUserBank());
        if (bankDTO.getNameUserBank().isEmpty()) {
            model.addAttribute("errNameUserBank", "Tên chủ thẻ không được để trống!");
            return "users/formBank";
        } else if (!matcherName.matches()) {
            model.addAttribute("errNameUserBank", "Tên chủ thẻ không đúng định dạng!");
            return "users/formBank";
        }

        if (bankDTO.getNameBank().isEmpty()) {
            model.addAttribute("errNameBank", "Tên ngân hàng không được để trống!");
            return "users/formBank";
        }
        String VALID_NUMBER_BANK = "^\\d+$";
        Pattern patternNumberBank = Pattern.compile(VALID_NUMBER_BANK);
        Matcher matcherNumberBank = patternNumberBank.matcher(bankDTO.getNumberBank());
        if (bankDTO.getNumberBank().isEmpty()) {
            model.addAttribute("errNumberBank", "Số tài khoản ngân hàng không được để trống!");
            return "users/formBank";
        } else if (!matcherNumberBank.matches()) {
            model.addAttribute("errNumberBank", "Số tài khoản ngân hàng không đúng!");
            return "users/formBank";
        }

        if (!bankDTO.getNumberBank().equals(profile.getBankAccount())) {
            profile.setBankAccount(bankDTO.getNumberBank());
        }
        if (!bankDTO.getNameBank().equals(profile.getNameBankAccount())) {
            profile.setNameBankAccount(bankDTO.getNameBank());
        }
        if (!bankDTO.getNameUserBank().equals(profile.getNameBankUsers())) {
            profile.setNameBankUsers(bankDTO.getNameUserBank());
        }
        logger.debug("--- saveBank profile ---- " + profile);
        profileRepository.save(profile);
        return "redirect:/profile/myInformation";
    }

    @Override
    public BankDTO findBankByProfile() {
        Users users = usersService.getCurrentUser();
        Profile profile = profileRepository.getImgUser(users.getId());
        if (profile == null) {
            profile = new Profile();
            logger.debug("Null Profile ---- " + logger.getName());
            throw new NullPointerException("Null Profile");

        }
        BankDTO bankDTO = new BankDTO();
        if (profile.getNumberCccdCmnd() != null) {
            bankDTO.setCmndCccd(profile.getNumberCccdCmnd());
        }
        if (profile.getBankAccount() != null) {
            bankDTO.setNumberBank(profile.getBankAccount());
        }
        if (profile.getNameBankAccount() != null) {
            bankDTO.setNameBank(profile.getNameBankAccount());
        }
        if (profile.getNameBankUsers() != null) {
            bankDTO.setNameUserBank(profile.getNameBankUsers());
        }
        logger.debug("--- findBankByProfile form bank  ---- " + profile);
        return bankDTO;
    }

    @Override
    public HouseHoldDTO findFormHouseHold() {
        Users users = usersService.getCurrentUser();
        Profile profile = profileRepository.getImgUser(users.getId());
        if (profile == null) {
            profile = new Profile();
            logger.debug("Null Profile ---- " + logger.getName());
            throw new NullPointerException("Null Profile");
        }
        HouseHoldDTO houseHoldDTO = new HouseHoldDTO();
        if (profile.getFullName() != null) {
            houseHoldDTO.setUserName(profile.getFullName());
        }
        if (profile.getNumberCccdCmnd() != null) {
            houseHoldDTO.setCmndCccd(profile.getNumberCccdCmnd());
        }
        if (profile.getImgHouseholdLeft() != null) {
            houseHoldDTO.setImgHouseholdLeft(profile.getImgHouseholdLeft());
        }

        logger.debug("--- findFormHouseHold form bank  ---- "+profile);
        return houseHoldDTO;
    }

    @Override
    public String houseHoldDTO(HouseHoldDTO houseHoldDTO, MultipartFile imgHouseholdLeft, Model model) throws IOException {
        Users users = usersService.getCurrentUser();
        Profile profile = profileRepository.getImgUser(users.getId());

        String imgHouseholdLeftReq = StringUtils.cleanPath(Objects.requireNonNull(imgHouseholdLeft.getOriginalFilename()));
        if (imgHouseholdLeftReq.isEmpty()){
            model.addAttribute("errImgHouse","Bạn chưa chọn đầy đủ ảnh!");
            return "users/formHousehold";
        }
        String uploadDir = StringUtil.DIR_NAME + users.getId();
        Utils.saveFile(uploadDir, imgHouseholdLeftReq, imgHouseholdLeft);

        String imgHouseholdLeftRes = "/" + uploadDir + "/" + imgHouseholdLeftReq;

        if (!imgHouseholdLeftRes.equals(profile.getImgHouseholdLeft())){
            profile.setImgHouseholdLeft(imgHouseholdLeftRes);
        }
        profileRepository.save(profile);
        return "redirect:/profile/myInformation";
    }

    @Override
    public String changePasswordProcessing(HttpServletRequest request, Model model) throws ServletException {
        Users users = usersService.getCurrentUser();
        String oldPassword = request.getParameter("passOld");
        String newPassword = request.getParameter("newPassword");
        String checkNewPassword = request.getParameter("checkNewPassword");
        if (!usersService.matches(oldPassword, users.getPassword())) {
            model.addAttribute("errPass","Mật khẩu cũ không đúng!");
            return "users/changePassword";
        }
        if (newPassword.length() <= 6){
            model.addAttribute("errChangPass","Mật khẩu phải lớn hơn 6 kí tự!");
            return "users/changePassword";
        }
        if (!checkNewPassword.equals(newPassword)){
            model.addAttribute("errNewPassword","Nhập lại mật khẩu không đúng!");
            return "users/changePassword";
        }
        usersService.changePassword(users, newPassword);
        request.logout();
        return "redirect:/login";
    }

    @Override
    public void saveProfile(Profile profile) {
        profileRepository.save(profile);
    }

    @Override
    public String saveLoanApplicationForm(ProfileDto profileDto, Model model) {
        Users users = usersService.getCurrentUser();
        Profile profile = profileRepository.getImgUser(users.getId());// lấy profile theo userId
        profile.setLoanPrice(profileDto.getLoanPrice());
        profile.setPayMoneyMonth(profileDto.getPayMoneyMonth());
        profile.setInterestRate(profileDto.getInterestRate());
        profile.setMoneyMonth(profileDto.getMoneyMonth());
        profile.setTypeLoan(TypeLoan.ACTIVE);
        profileRepository.save(profile);
        return "redirect:/profile/myInformation";
    }

    @Override
    public ProfileDto getLoanApplication(Model model) {
        Users users = usersService.getCurrentUser();
        Profile profile = profileRepository.getImgUser(users.getId());
        ProfileDto profileDto = new ProfileDto();
        profileDto.setFullName(profile.getFullName());
        profileDto.setPhone(profile.getPhone());
        profileDto.setAddress(profile.getAddress());
        profileDto.setLoanPrice(profile.getLoanPrice());
        profileDto.setMoneyMonth(profile.getMoneyMonth());
        profileDto.setPayMoneyMonth(profile.getPayMoneyMonth());
        return profileDto;
    }

}
