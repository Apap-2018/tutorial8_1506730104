package com.apap.tutorial8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleDb userDb;

	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String regex = "^(?=.*[0-9])(?=.*[a-z]).{8,}$";
		if(user.getPassword().matches(regex)) {
			String pass = encrypt(user.getPassword());
			user.setPassword(pass);
			return userDb.save(user);
		}
		return null;
	}

	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	@Override
	public void updatePassword(String username, String password_lama, String password_baru, String konfirmasi_password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(passwordEncoder.matches(password_lama, userDb.findByUsername(username).getPassword())) {
			if(password_baru.equals(konfirmasi_password)) {
				String regex = "^(?=.*[0-9])(?=.*[a-z]).{8,}$";
				if(password_baru.matches(regex)) {
					String pass = encrypt(password_baru);
					userDb.findByUsername(username).setPassword(pass);
					userDb.save(userDb.findByUsername(username));
				}
				
			}
		}
		
	}
	
	
}
