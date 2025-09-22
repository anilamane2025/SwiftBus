package com.anil.swiftBus.serviceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.ModelMapper.AgentMapper;
import com.anil.swiftBus.ModelMapper.UserMapper;
import com.anil.swiftBus.dao.CityDAO;
import com.anil.swiftBus.dao.RoleDAO;
import com.anil.swiftBus.dao.UserDAO;
import com.anil.swiftBus.dto.AgentDTO;
import com.anil.swiftBus.dto.AgentRegisterDTO;
import com.anil.swiftBus.dto.RegistrationDTO;
import com.anil.swiftBus.dto.UserDTO;
import com.anil.swiftBus.entity.AgentCommissionRule;
import com.anil.swiftBus.entity.City;
import com.anil.swiftBus.entity.Role;
import com.anil.swiftBus.entity.User;
import com.anil.swiftBus.enums.CommissionType;
import com.anil.swiftBus.enums.UserType;
import com.anil.swiftBus.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private RoleDAO roleDAO;
    
    @Autowired
    private CityDAO cityDAO;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
//    public UserServiceImpl(UserDAO userDAO, ModelMapper modelMapper) {
//        this.userDAO = userDAO;
//        this.modelMapper = modelMapper;
//    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

	@Override
	public Optional<UserDTO> findById(Long id) {
		Optional<User> userOptional = userDAO.findById(id);
        return userOptional.map(UserMapper::toDTO);
	}

	@Override
	public Optional<UserDTO> findUserByUsername(String username) {
		Optional<User> userOptional = userDAO.findByUsername(username);
        return userOptional.map(UserMapper::toDTO);
	}

	@Override
	public void save(RegistrationDTO dto) {
		Role passengerRole = roleDAO.findByUserType(UserType.PASSENGER);

        City city = cityDAO.getCityByCityId(dto.getCityId());
		
		// map DTO -> Entity
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setGender(dto.getGender());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setCity(city);
		user.setRole(passengerRole);
		user.setEnabled(true);
		
		userDAO.save(user);
	}

	@Override
	@Transactional
	public void update(Long id, UserDTO userDTO) {
		Optional<User> userOptional = userDAO.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setUsername(userDTO.getUsername());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setGender(userDTO.getGender());
            user.setCity(cityDAO.getCityByCityId(userDTO.getCityId()));

            userDAO.update(user);
        }
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		userDAO.delete(id);
	}

	@Override
	public List<UserDTO> findAll() {
		List<User> users = userDAO.findAll();
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
	}

	@Override
	public boolean usernameExists(String username) {
		return userDAO.existsByUsername(username);
	}

	@Override
	public boolean phoneNumberExists(String phoneNumber) {
		return userDAO.existsByPhoneNumber(phoneNumber);
	}

	@Override
	public void updatePassword(Long id, String newPassword) {
		Optional<User> userOptional = userDAO.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // hash password before saving (recommended)
            user.setPassword(passwordEncoder.encode(newPassword));
            userDAO.update(user);
        }
	}

	@Override
	public List<UserDTO> findAgent() {
		List<User> users = userDAO.findAgents();
        return users.stream()
                .map(UserMapper::toDTO) 
                .collect(Collectors.toList());
	}

	@Override
	public List<UserDTO> findPassengers() {
		List<User> users = userDAO.findPassangers();
        return users.stream()
                .map(UserMapper::toDTO) 
                .collect(Collectors.toList());
	}

	@Override
	public void deletePassenger(Long id) {
		try {
			userDAO.delete(id);
        } catch (IllegalStateException e) {
            // rethrow for controller
            throw e;
        }
	}

	@Override
	public void activatePermission(Long id) {
		try {
            userDAO.activate(id);
        } catch (IllegalStateException e) {
            // rethrow for controller
            throw e;
        }
	}

	@Override
	public Optional<AgentDTO> getAgentById(Long id) {
		Optional<User> agent = userDAO.findById(id);
        AgentCommissionRule rule = userDAO.findCommissionRuleByAgentId(id);
        return Optional.ofNullable(AgentMapper.toDTO(agent.get(), rule));
	}

	@Override
	public List<AgentDTO> getAllAgents() {
		List<User> agents = userDAO.findAgents();
        List<AgentDTO> agentDTOs = new ArrayList<>();

        for (User agent : agents) {
            AgentCommissionRule rule = userDAO.findCommissionRuleByAgentId(agent.getId());
            AgentDTO dto = AgentMapper.toDTO(agent, rule);
            agentDTOs.add(dto);
        }

        return agentDTOs;
	}

	@Override
	public void updateAgent(Long id, AgentDTO agentDTO) {
		Optional<User> userOptional = userDAO.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(agentDTO.getFirstName());
            user.setLastName(agentDTO.getLastName());
            user.setUsername(agentDTO.getUsername());
            user.setPhoneNumber(agentDTO.getPhoneNumber());
            user.setGender(agentDTO.getGender());
            user.setCity(cityDAO.getCityByCityId(agentDTO.getCityId()));

            userDAO.update(user);
            AgentCommissionRule rule = userDAO.findCommissionRuleByAgentId(id);
    		if (rule == null) {
                rule = new AgentCommissionRule();
                rule.setAgent(user);
            }
            if (agentDTO.getCommissionType() != null) {
                try {
                    CommissionType type = CommissionType.valueOf(agentDTO.getCommissionType().toUpperCase());
                    rule.setCommissionType(type);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid commission type: " + agentDTO.getCommissionType());
                }
            }

            rule.setCommissionValue(agentDTO.getCommissionValue());

            userDAO.saveCommissionRule(rule);
        } else {
            throw new RuntimeException("Agent not found with id: " + id);
        }
	}

	@Override
	public void saveAgent(AgentRegisterDTO agentDTO) {
		Role passengerRole = roleDAO.findByUserType(UserType.AGENT);

        City city = cityDAO.getCityByCityId(agentDTO.getCityId());
		
		// map DTO -> Entity
		User user = new User();
		user.setUsername(agentDTO.getUsername());
		user.setFirstName(agentDTO.getFirstName());
		user.setLastName(agentDTO.getLastName());
		user.setPassword(passwordEncoder.encode(agentDTO.getPassword()));
		user.setGender(agentDTO.getGender());
		user.setPhoneNumber(agentDTO.getPhoneNumber());
		user.setCity(city);
		user.setRole(passengerRole);
		user.setEnabled(true);
		
		userDAO.save(user);
		
		
		AgentCommissionRule rule = new AgentCommissionRule();
        rule.setAgent(user);
        
        if (agentDTO.getCommissionType() != null) {
            try {
                CommissionType type = CommissionType.valueOf(agentDTO.getCommissionType().toUpperCase());
                rule.setCommissionType(type);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid commission type: " + agentDTO.getCommissionType());
            }
        }

        rule.setCommissionValue(agentDTO.getCommissionValue());

        userDAO.saveCommissionRule(rule);
	}

	

}