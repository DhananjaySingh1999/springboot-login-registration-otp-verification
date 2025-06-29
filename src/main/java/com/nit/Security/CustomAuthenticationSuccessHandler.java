package com.nit.Security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.nit.MailSender.MailSenderConfig;
import com.nit.Util.RandomNumber;
import com.nit.model.User;
import com.nit.repo.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	public RandomNumber randomvalues;
	
	@Autowired
	public UserRepository repo;

	@Autowired
	public MailSenderConfig mailSender;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        
        // Get the authenticated user's email
        String email = authentication.getName();
        
        // Send OTP email here
        String otp = randomvalues.generateOtp(); 
        Optional<User> byUemail = repo.findByUemail(email);
        if(byUemail.isPresent()) {
        	byUemail.get().setUserotp(Integer.parseInt(otp));
        	repo.save(byUemail.get());
        	mailSender.send(email, "OTP Verification", "Your OTP is: " + otp);
        }

        // Store OTP in session to verify later
        request.getSession().setAttribute("otp", otp);
        request.getSession().setAttribute("email", email);

        // Redirect to OTP page
        response.sendRedirect("/verify-otp");
    }

    
}
