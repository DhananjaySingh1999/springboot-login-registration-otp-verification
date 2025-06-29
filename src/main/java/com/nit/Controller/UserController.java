package com.nit.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nit.MailSender.MailSenderConfig;
import com.nit.Service.UserService;
import com.nit.model.User;
import com.nit.repo.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	
	@Autowired
	public MailSenderConfig mailSender;
	
	@Autowired
	public UserService service;
	
	@Autowired
	public UserRepository userRepository;
	

	@Autowired
	public BCryptPasswordEncoder pwdEncoder;
	
	@GetMapping("/reg")
	public String userReg() {
		return "register";
	}
	
	@PostMapping("/save")
	public String savereg(@ModelAttribute User user, Model model) {
		System.out.println("details:::::"+user);
		user.setUserflag(false);
		String subject = "User Registration Successful";
		String body = "Hi Mr. " + user.getUname() + ",\n\n"
		            + "You have been registered successfully!\n\n"
		            + "Here are your credentials:\n"
		            + "Username: " + user.getUemail() + "\n"
		            + "Password: " + user.getUpwd() + "\n\n"
		            + "Please keep this information safe.\n\n"
		            + "Regards,\n"
		            + "Dhananjay Singh";

		boolean send = mailSender.send(
		    user.getUemail(),
		    subject,
		    body
		);

		user.setUpwd(pwdEncoder.encode(user.getUpwd()));
		String name=service.storeData(user);
		String message = "User \"" + name + "\" has been registered successfully.\n";
		if (send) {
		    message += "Mail has been sent successfully!";
		}
		model.addAttribute("msg",message);
		return "register";
	}
	
	@GetMapping("/home")
	public String homemsg() {
		return "homepage";
	}
	
	@GetMapping("/welcome")
	public String welcomemsg() {
		return "welcomepage";
	}
	
	@GetMapping("/admin")
	public String adminmsg() {
		return "adminpage";
	}
	
	@GetMapping("/emp")
	public String empmsg() {
		return "emppage";
	}
	
	@GetMapping("/student")
	public String studentmsg() {
		return "stdpage";
	}
	
	@GetMapping("/common")
	public String coomonmsg() {
		return "commonpage";
	}
	
	@GetMapping("/denied")
	public String deniedmsg() {
		return "deniedpage";
	}
	
	@GetMapping("/weblogin")
	public String loginpage() {
		return "weblogin";
	}
	
	@GetMapping("/verify-otp")
	public String showOtpPage() {
	    return "otp-page"; 
	}
	
	@PostMapping("/verifyOtp")
	public String verifyOtp(
	        @RequestParam("username") String email,
	        @RequestParam("otp") int enteredOtp,
	        HttpServletRequest request,
	        Model model) {

	    
	    Optional<User> optUser = userRepository.findByUemail(email);
	    if (optUser.isEmpty()) {
	        model.addAttribute("error", "Invalid Email Address");
	        return "otp-page";
	    }

	    User user = optUser.get();

	    if (user.getUserotp() == enteredOtp) {
	        user.setUserflag(true);
	        userRepository.save(user);
	        return "redirect:/welcome"; 
	    } else {
	        model.addAttribute("error", "Invalid OTP");
	        model.addAttribute("username", email); 
	        return "otp-page";
	    }
	}


}
