package com.anil.swiftBus.dao;

import java.util.List;
import java.util.Optional;

import com.anil.swiftBus.entity.AgentCommissionRule;
import com.anil.swiftBus.entity.User;

public interface UserDAO {
	Optional<User> findByUsername(String username);
    
    Optional<User> findById(Long id);
    
    void save(User user);
    void update(User user);
    void delete(Long id);
    List<User> findAll();
    List<User> findAgents();
    List<User> findPassangers();
    
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);

	void activate(Long id);
	
	AgentCommissionRule findCommissionRuleByAgentId(Long agentId);
    void saveCommissionRule(AgentCommissionRule rule);
}