package com.ivman.apicontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivman.apivalidator.IvManException;
import com.ivman.apivalidator.ValidateUser;
import com.ivman.dao.CompanyDao;
import com.ivman.dao.UserRoleDao;
import com.ivman.daointerface.UserInterfaceDao;
import com.ivman.model.CompanyModel;
import com.ivman.model.UserModel;
import com.ivman.model.UserRoleModel;
import com.ivman.securityconfig.JwtTokenUtil;
import com.ivman.to.CompanyTO;
import com.ivman.to.MainModel;
import com.ivman.to.Message;
import com.ivman.to.UserRoleTO;
import com.ivman.to.UserTO;
import com.ivman.utils.AppConstants;
import com.ivman.utils.StringUtils;

@RestController
@RequestMapping(value="Api")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired 
	private UserInterfaceDao userDao;

	@Autowired 
	private UserRoleDao userRoleDao;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CompanyDao companyDao;
	
	@GetMapping("/start")
	public String start() {
		return "Started";
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid UserTO request) {
		request.setPassword(request.getFirebaseId());
		Optional<UserModel> optionalUserModel = userDao.findbyPhoneNumber(request.getPhoneNumber());
		MainModel mainModel = new MainModel();
		if(!optionalUserModel.isPresent()) {
			mainModel.setMessage("Given Mobile Number Is Not Registered");
			mainModel.setResponse(false);
		    new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		UserTO userTO =  new UserTO();
		userTO.convertModelToTO(optionalUserModel.get());
		if(StringUtils.isNotEmpty(request.getFirebaseId())) {
			userTO.setFirebaseId(passwordEncoder.encode(request.getFirebaseId()));
		}
		if(StringUtils.isNotEmpty(request.getFirebaseToken())) {
			userTO.setFirebaseToken(request.getFirebaseToken());
		}
		userDao.save(userTO);
		UserModel user = null;
		try {
			Authentication authenticate = authenticationManager
					.authenticate(
							new UsernamePasswordAuthenticationToken(
									request.getPhoneNumber(), request.getPassword()
									)
							);

			user = (UserModel) authenticate.getPrincipal();
			userTO.convertModelToTO(user);
		} catch (BadCredentialsException ex) {
			mainModel.setMessage("Invalid Credentials");
			mainModel.setResponse(false);
		    new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		mainModel.setUserTOList(Arrays.asList(userTO));
		getDashBoardData(mainModel);
		return ResponseEntity.ok()
				.header(
						HttpHeaders.AUTHORIZATION,
						jwtTokenUtil.generateAccessToken(user)
						)
				.body(mainModel);
	}

	private void getDashBoardData(MainModel mainModel) {
		
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid UserTO request) {
		System.out.println(request);
		Optional<UserModel> optionalUserModel = userDao.findbyPhoneNumber(request.getPhoneNumber());
		MainModel mainModel = new MainModel();
		if(optionalUserModel.isPresent()) {
			UserTO userTO =  new UserTO();
			userTO.convertModelToTO(optionalUserModel.get());
			if(Objects.nonNull(request.getFirebaseId())) {
				userTO.setFirebaseId(passwordEncoder.encode(request.getFirebaseId()));
			}
			if(StringUtils.isNotEmpty(request.getFirebaseToken())) {
				userTO.setFirebaseToken(request.getFirebaseToken());
			}
			userDao.save(userTO);
			mainModel.setUserTOList(Arrays.asList(userTO));
		}else {
			if(Objects.nonNull(request.getFirebaseId())) {
				request.setFirebaseId(passwordEncoder.encode(request.getFirebaseId()));
			}
			userDao.save(request);
			mainModel.setUserTOList(Arrays.asList(request));
		}
		mainModel.setResponse(true);
		mainModel.setMessage("Registered Successfully");
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}

	@PostMapping("/checkPhoneNumber")
	public ResponseEntity<?> checkPhoneNumber(@RequestBody @Valid UserTO userTO) {
		ValidateUser validateUser = new ValidateUser(userDao);
		Message message = new Message();
		try {
			validateUser.checkPhoneNumber(userTO.getPhoneNumber());
			message.setMessage("Validated successfully");
			message.setResponse(true);
		}catch(IvManException e) {
			message.setMessage(e.getMessage());
			message.setResponse(false);
		}
		return new ResponseEntity<Message>(message,HttpStatus.OK);
	}

	@PostMapping("/createBaseData")
	public ResponseEntity<?> createBaseData() {
		
		ArrayList<String> roles = new ArrayList<String>(Arrays.asList(AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.EDITOR));
		List<UserRoleTO> userRoleTOList = new ArrayList<UserRoleTO>();
		roles.forEach(role -> {
			UserRoleTO userRoleTO = new UserRoleTO();
			userRoleTO.setUserRoleDesc(role);
			userRoleTOList.add(userRoleTO);
		});
		userRoleDao.saveAll(userRoleTOList);
		
		CompanyTO companyTO = new CompanyTO();
		companyTO.setCompanyName("Fidbee");
		companyDao.save(companyTO);
		
		Message message = new Message();
		message.setMessage("User Roles and Users Created successfully");
		message.setResponse(true);
		return new ResponseEntity<List<Message>>(Arrays.asList(message),HttpStatus.OK);
	}

}
