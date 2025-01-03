package com.shiavnski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shiavnski.model.Employee;
import com.shiavnski.repository.EmployeeRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
    // Retrieve the Employee object wrapped in Optional
            Employee employee = employeeRepository.findByEmailId(emailId)
            .orElseThrow(() -> new UsernameNotFoundException("Employee not found with EmailID: " + emailId));
            System.out.println("THE EMPLOYEE DETAILS : "+employee);
    // Return the UserDetails object
        return org.springframework.security.core.userdetails.User.builder()
            .username(employee.getEmailId())
            .password(employee.getPassword())
            .roles("USER") // You can customize the roles as needed
            .build();
    }

    


}
