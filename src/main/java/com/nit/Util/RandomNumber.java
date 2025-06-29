package com.nit.Util;

import org.springframework.stereotype.Component;

@Component
public class RandomNumber {
	
	public String generateOtp() {
        return String.valueOf((int)(Math.random() * 900000) + 100000); // 6-digit OTP
    }

}
