package com.nishan.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nishan.demo.dto.AuthToken;
import com.nishan.demo.dto.LoginRequest;
import com.nishan.demo.dto.UserDto;
import com.nishan.demo.entity.Role;
import com.nishan.demo.entity.User;
import com.nishan.demo.exception.CustomException;
import com.nishan.demo.exception.ResourceNotFoundException;
import com.nishan.demo.exception.UserException;
import com.nishan.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private JavaMailSender javaMailSender;

	public UserDto register(UserDto userDto) {
		Optional<User> opt = userRepository.findByEmail(userDto.getEmail());
		if (opt.isEmpty()) {
			User user = new User();
			user.setName(userDto.getName());
			user.setAddress(userDto.getAddress());
			user.setAge(userDto.getAge());
			user.setContact(userDto.getContact());
			user.setCreatedDateTime(LocalDateTime.now());
			user.setEmail(userDto.getEmail());
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			user.setMaritialStatus(userDto.getMaritialStatus());
			user.setSex(userDto.getSex());
			user.setRole(Role.USER);
			user.setUsername(userDto.getUsername());

			User savedUser = userRepository.save(user);
			
			MimeMessagePreparator messagePreparator = mimeMessage -> {
	            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
	            messageHelper.setFrom("springreddit@email.com");
	            messageHelper.setTo(userDto.getEmail());
	            messageHelper.setSubject("sucessfully signed up ");
	            messageHelper.setText("you are sucessfully signup to the demo application");
	        };
	        
	        try {
	            javaMailSender.send(messagePreparator);
	        } catch (MailException e) {
	            throw new CustomException("Exception occurred when sending mail to " + userDto.getEmail());
	        }
	        

			return modelMapper.map(savedUser, UserDto.class);
		} else {
			throw new UserException("user already present with this email");
		}
	}

	public AuthToken login(LoginRequest req) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
		var user = userRepository.findByEmail(req.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("user not found with this email"));

		var jwt = jwtService.generateToken(user);

		return AuthToken.builder().token(jwt).build();
	}

	public UserDto updateUser(UserDto userDto, String username) {
		User user = userRepository.findByEmail(userDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("user not found "));

		User user2 = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("user not found "));
		if (user2.getRole() == Role.ADMIN) {
			user.setName(userDto.getName());
			user.setAddress(userDto.getAddress());
			user.setAge(userDto.getAge());
			user.setContact(userDto.getContact());
			user.setUpdatedDateTime(LocalDateTime.now());
			user.setEmail(userDto.getEmail());
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			user.setMaritialStatus(userDto.getMaritialStatus());
			user.setSex(userDto.getSex());
			user.setUsername(userDto.getUsername());

			userRepository.save(user);

			return modelMapper.map(user, UserDto.class);
		} else {
			throw new CustomException("you are not admin");
		}

	}
	
	public void deleteUser(String email,String username) {
		User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user not found "));
		User user2 = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("user not found "));

		if(user2.getRole()==Role.ADMIN) {
			userRepository.delete(user);
		}
		else {
			throw new CustomException("you are not admin");
		}
	}
	
	public List<UserDto> findAllUser(){

			List<User> findAll = userRepository.findAll();
			return findAll.stream().map(us->modelMapper.map(findAll, UserDto.class)).collect(Collectors.toList());
		
	}
	
	public UserDto getByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("user not found "));

		return modelMapper.map(user, UserDto.class);
		

	}
	
	public List<UserDto> findByAddress(String address,String username){
		User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user not found "));
		if(user.getRole()==Role.ADMIN) {
			List<User> findByAddress = userRepository.findByAddress(address);
			return findByAddress.stream().map(fa->modelMapper.map(fa, UserDto.class)).collect(Collectors.toList());
		}
		else {
			throw new CustomException("you are not admin");
		}
	}
	
	public List<UserDto> findByAge(Integer age,String username){
		User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user not found "));
		if(user.getRole()==Role.ADMIN) {
			List<User> findByAge= userRepository.findByAge(age);
			return findByAge.stream().map(fa->modelMapper.map(fa, UserDto.class)).collect(Collectors.toList());
		}
		else {
			throw new CustomException("you are not admin");
		}
	}
	
	public List<UserDto> findByName(String fullname,String username){
		User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user not found "));
		if(user.getRole()==Role.ADMIN) {
			List<User> findByName= userRepository.findByName(fullname);
			return findByName.stream().map(fa->modelMapper.map(fa, UserDto.class)).collect(Collectors.toList());
		}
		else {
			throw new CustomException("you are not admin");
		}
	}
	
	public List<UserDto> search(String query,String username){
		User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user not found "));
		if(user.getRole()==Role.ADMIN) {
			List<User> findByName= userRepository.searchUser(query);
			return findByName.stream().map(fa->modelMapper.map(fa, UserDto.class)).collect(Collectors.toList());
		}
		else {
			throw new CustomException("you are not admin");
		}
	}

}
