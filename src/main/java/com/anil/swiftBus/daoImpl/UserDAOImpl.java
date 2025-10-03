package com.anil.swiftBus.daoImpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.anil.swiftBus.dao.UserDAO;
import com.anil.swiftBus.entity.AgentCommissionRule;
import com.anil.swiftBus.entity.User;
import com.anil.swiftBus.enums.UserType;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        try {
        	User user = entityManager.createQuery(
                    "SELECT u FROM User u " +
                            "LEFT JOIN FETCH u.role r " +       //  User has a single Role (ManyToOne)
                            "LEFT JOIN FETCH r.rolePermissions rp " +
                            "LEFT JOIN FETCH rp.permission p " +
                            "WHERE u.username = :username OR u.phoneNumber = :username", User.class)
                        .setParameter("username", username)
                        .getSingleResult();

                    return Optional.of(user);

                } catch (NoResultException e) {
                    return Optional.empty();  // correct way for Optional
                } catch (Exception e) {
                    throw new RuntimeException("Error accessing data", e); // wrap in runtime exception
                }
    }

	@Override
	public Optional<User> findById(Long id) {
//		User user = entityManager.find(User.class, id);
//        return Optional.ofNullable(user);
		try {
	        User user = entityManager.createQuery(
	            "SELECT u FROM User u LEFT JOIN FETCH u.role r LEFT JOIN FETCH u.city WHERE u.id = :id", 
	            User.class
	        )
	        .setParameter("id", id)
	        .getSingleResult();

	        return Optional.ofNullable(user);
	    } catch (NoResultException e) {
	        return Optional.empty();
	    }
	}

	@Override
	public void save(User user) {
		entityManager.persist(user);
		
	}

	@Override
	public void update(User user) {
		entityManager.merge(user);
		
	}

	@Override
	public void delete(Long id) {
		User user = entityManager.find(User.class, id);
        if (user == null) {
        	throw new IllegalArgumentException("User not found");
        }
        user.setEnabled(false);
        entityManager.merge(user);
        		
	}

	@Override
	public List<User> findAll() {
		String query = "SELECT u FROM User u";
        return entityManager.createQuery(query, User.class).getResultList();
	}

	@Override
	public boolean existsByUsername(String username) {
		Long count = entityManager.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
	}

	@Override
	public boolean existsByPhoneNumber(String phoneNumber) {
		Long count = entityManager.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.phoneNumber = :phoneNumber", Long.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
        return count > 0;
	}

	@Override
	public List<User> findAgents() {
		try {
			List<User> user = entityManager.createQuery(
	            "SELECT u FROM User u LEFT JOIN FETCH u.role r LEFT JOIN FETCH u.city WHERE r.userType = :AGENT", 
	            User.class
	        )
			.setParameter("AGENT", UserType.AGENT)
	        .getResultList();

	        return user;
	    } catch (NoResultException e) {
	        return null;
	    }
	}

	@Override
	public List<User> findPassangers() {
		try {
			List<User> user = entityManager.createQuery(
	            "SELECT u FROM User u LEFT JOIN FETCH u.role r LEFT JOIN FETCH u.city WHERE r.userType = :PASSENGER", 
	            User.class
	        )
			.setParameter("PASSENGER", UserType.PASSENGER)
	        .getResultList();

	        return user;
	    } catch (NoResultException e) {
	        return null;
	    }
	}

	@Override
	public void activate(Long id) {
		User user = entityManager.find(User.class, id);
        if (user == null) {
        	throw new IllegalArgumentException("User not found");
        }
        user.setEnabled(true);
        entityManager.merge(user);
	}

	@Override
	public AgentCommissionRule findCommissionRuleByAgentId(Long agentId) {
		try {
            return entityManager.createQuery(
                    "SELECT r FROM AgentCommissionRule r WHERE r.agent.id = :agentId", AgentCommissionRule.class)
                    .setParameter("agentId", agentId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}

	@Override
	public void saveCommissionRule(AgentCommissionRule rule) {
		if (rule.getRuleId() == null) {
            entityManager.persist(rule);
        } else {
            entityManager.merge(rule);
        }
	}
}