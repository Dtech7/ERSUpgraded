package com.example.service;

import com.example.repository.EmployeeRepository;

@Service
@Transactional
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class EmployeeService {
	private EmployeeRepository eRepo;
	//private EmployeeRoleRepository erRepo;
	
	public Employee registerEmployee(String firstName, String lastName) {
		//EmployeeRole role = erRepo.findById(1).get()
		//List<EmployeeRole> roles = new ArrayList<>();
		//roles.add(role);
		Employee e = new Employee(0, roles, firstName, lastName)//add others
		
	}
	
	public Employee loginEmployee(String email, String password) {}
	Employee e = eRepo.getByEmail(email).orElseThrow(Exception);
	
	if(!e.getPassword.equals(password)) {}else {}
}
