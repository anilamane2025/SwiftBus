package com.anil.swiftBus.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.anil.swiftBus.dto.AgentDTO;
import com.anil.swiftBus.dto.AgentRegisterDTO;
import com.anil.swiftBus.dto.RegistrationDTO;
import com.anil.swiftBus.dto.UserDTO;
import com.anil.swiftBus.entity.User;

public interface UserService {
	Optional<User> findByUsername(String username);
    //void saveUser(User user);
    
    Optional<UserDTO> findById(Long id);
    Optional<UserDTO> findUserByUsername(String username);
    void save(RegistrationDTO registrationDTO);
    void update(Long id, UserDTO userDTO);
    void delete(Long id);
    List<UserDTO> findAll();
    List<UserDTO> findAgent();
    List<UserDTO> findPassengers();
    
    boolean usernameExists(String username);
    boolean phoneNumberExists(String phoneNumber);

	void updatePassword(Long id, String newPassword);

	void deletePassenger(Long id);

	void activatePermission(Long id);
	
	Optional<AgentDTO> getAgentById(Long id);
    List<AgentDTO> getAllAgents();

	void updateAgent(Long id, AgentDTO agentDTO);

	void saveAgent(AgentRegisterDTO agentDTO);
}