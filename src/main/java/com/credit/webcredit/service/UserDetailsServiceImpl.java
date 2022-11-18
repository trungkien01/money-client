package com.credit.webcredit.service;

import com.credit.webcredit.entity.Profile;
import com.credit.webcredit.enums.Role;
import com.credit.webcredit.entity.Users;
import com.credit.webcredit.enums.TypeLoan;
import com.credit.webcredit.forms.RegisterForm;
import com.credit.webcredit.repository.ProfileRepository;
import com.credit.webcredit.repository.UsersRepository;
import com.credit.webcredit.security.MyUserDetails;
import com.credit.webcredit.utils.StringUtil;
import com.credit.webcredit.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserDetailsServiceImpl implements UsersService {
	private final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ProfileRepository profileRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = usersRepository.getUserByPhone(username);
		
		if (users == null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		
		return new MyUserDetails(users);
	}

	@Override
	public String saveUser(RegisterForm registerForm, Model model) {
		Users users = usersRepository.getUserByPhone(registerForm.getPhone());
		boolean check = true;
		String VALID_NAME = "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶ" +
				"ẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ" +
				"ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";
		Pattern patternName = Pattern.compile(VALID_NAME);
		Matcher matcherName = patternName.matcher(registerForm.getFullName());
		if (registerForm.getFullName().isEmpty()){
			model.addAttribute("errFullName","Họ tên không được để trống!");
			check = false;
		}else if (!matcherName.matches()){
			model.addAttribute("errFullName","Họ tên không úng định dạng!");
			check = false;
		}

		if (users != null){
			model.addAttribute("errUsers","Số điện thoại đã được đăng kí!");
			logger.debug("Register fail" + users.getPhone());
			return "register";
		}
		String VALID_PHONE = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
		Pattern patternPhone = Pattern.compile(VALID_PHONE);
		Matcher matcherPhone = patternPhone.matcher(registerForm.getPhone());
		if (registerForm.getPhone().isEmpty()){
			model.addAttribute("errPhone","Số điện thoại không được để trống!");
			check = false;
		}else if (!matcherPhone.matches()){
			model.addAttribute("errPhone","Số điện thoại không đúng!");
			check = false;
		}

		String VALID_EMAIL = "^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$";
		Pattern pattern = Pattern.compile(VALID_EMAIL);
		Matcher matcher = pattern.matcher(registerForm.getEmail());
		if (registerForm.getEmail().isEmpty()){
			model.addAttribute("errEmail","Email không được để trống!");
			check = false;
		}else if (!matcher.matches()){
			model.addAttribute("errEmail","Email không đúng định dạng!");
			check = false;
		}
		if (registerForm.getPassword().isEmpty()){
			model.addAttribute("errPass","Mật khẩu không được để trống!");
			check = false;
		}else if (registerForm.getPassword().length() <= 6){
			model.addAttribute("errPass","Mật khẩu phải lớn hơn 6 kí tự!");
			check = false;
		}
		if (registerForm.getConfirm().isEmpty()){
			model.addAttribute("errChangPass","Xác nhận mật khẩu không được để trống!");
			check = false;
		}else if (!registerForm.getConfirm().equals(registerForm.getPassword())){
			model.addAttribute("errChangPass","Xác nhận mật khẩu không đúng!");
			check = false;
		}
		if (check){
			users = new Users();
			users.setRole(Role.ROLE_USER);
			users.setFullName(registerForm.getFullName());
			users.setPassword(passwordEncoder.encode(registerForm.getPassword()));
			users.setEmail(registerForm.getEmail());
			users.setPhone(registerForm.getPhone());
			usersRepository.save(users);
			Profile profile = new Profile();
			profile.setUserId(users.getId());
			profile.setFullName(users.getFullName());
			profile.setPhone(users.getPhone());
			profile.setTypeLoan(TypeLoan.NOT_ACTIVE);
			profileRepository.save(profile);
			return "redirect:/login";
		}
		return "register";
	}

	@Override
	public Users getCurrentUser() {
		Users users = usersRepository.getUserByPhone(Utils.getUserName());
		if (users == null) {
			logger.warn("Could not find user"+ logger.getName());
			throw new UsernameNotFoundException("Could not find user");
		}
		return users;
	}

	@Override
	public Long getUserId() {
		Users users = usersRepository.getUserByPhone(Utils.getUserName());
		if (users == null) {
			logger.warn("Could not find user"+ logger.getName());
			throw new UsernameNotFoundException("Could not find user");
		}
		return users.getId();
	}

	@Override
	public void changePassword(Users users, String newPassword) {
		String encodedPassword = passwordEncoder.encode(newPassword);
		users.setPassword(encodedPassword);
		usersRepository.save(users);
	}

	@Override
	public boolean matches(String oldPassWord, String newPassWord) {
		return passwordEncoder.matches(oldPassWord, newPassWord);
	}
	@Override
	public String
	sendEmailPassword(String email,Model model) {
		String getEmail = usersRepository.sendEmailPassword(email);
		if (StringUtils.isEmpty(getEmail)){
			model.addAttribute("smgEmail","Email chưa được đăng ký");
		}
		if (!StringUtils.isEmpty(getEmail)){
			emailService.sendSimpleMail(getEmail);
			model.addAttribute("smgEmail","Mã xác nhận đã được gửi tới Email của bạn.");
		}
		return "resetPassword";
	}
}
