package com.shiavnski.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiavnski.ResponseDto.LoginResponse;
import com.shiavnski.exception.ResourceNotFoundException;
import com.shiavnski.model.Employee;
import com.shiavnski.repository.EmployeeRepository;
import com.shiavnski.service.UserDetailsServiceImpl;
import com.shiavnski.utill.JwtUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl; 

	@Autowired
	private JwtUtil jwtUtil;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// get all employees
	@GetMapping("/allemployees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	// signup
	@PostMapping("/empsignup")
	public Employee createEmployee(@RequestBody Employee employee) {

		String encodedPassword = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(encodedPassword);

		return employeeRepository.save(employee);
	}

	// login
	@PostMapping("/emplogin")
	public ResponseEntity<LoginResponse>login(@RequestBody Employee employee) {
		try{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employee.getEmailId(),employee.getPassword()));
		UserDetails userDetails=userDetailsServiceImpl.loadUserByUsername(employee.getEmailId());	
		System.out.println("THE USERDETAILS :"+userDetails);
			String jwt=jwtUtil.generateToken(userDetails.getUsername());


			Optional<Employee> loggedInEmployee = employeeRepository.findByEmailId(employee.getEmailId());
			System.out.println("ALL DATA   ___________________"+loggedInEmployee);
         if (loggedInEmployee == null) {
             throw new UsernameNotFoundException("User not found");
         }
		 Employee employeeEntity = loggedInEmployee.get();
        //  Create response object
        LoginResponse loginResponse = new LoginResponse(jwt, employeeEntity.getFirstName(), employeeEntity.getEmailId());


			//return new ResponseEntity<>(jwt,HttpStatus.OK);
			return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}
	catch (Exception e) {  
		
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	
	}
	}

	// get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails) {
		
			Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
			
				if (!employee.getEmailId().equals(userDetails.getUsername())) {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // Forbidden if trying to delete another user's details
				}
		
		Employee employeedata = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		return ResponseEntity.ok(employeedata);
	}

	// update employee rest api

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails,@AuthenticationPrincipal UserDetails userDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		        if (!employee.getEmailId().equals(userDetails.getUsername())) {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // Forbidden if trying to update another user's details
				}
		

		employee.setFirstName(employeeDetails.getFirstName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);

		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	// delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

				if (!employee.getEmailId().equals(userDetails.getUsername())) {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // Forbidden if trying to delete another user's details
				}
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
