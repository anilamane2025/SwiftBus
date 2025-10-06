package com.anil.swiftBus.dto;

import java.math.BigDecimal;

public class AgentSummaryDTO {
    private Long agentId;
    private String agentName;
    private Long totalBookings;
    private BigDecimal totalCommission;
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public Long getTotalBookings() {
		return totalBookings;
	}
	public void setTotalBookings(Long totalBookings) {
		this.totalBookings = totalBookings;
	}
	public BigDecimal getTotalCommission() {
		return totalCommission;
	}
	public void setTotalCommission(BigDecimal totalCommission) {
		this.totalCommission = totalCommission;
	}

}
