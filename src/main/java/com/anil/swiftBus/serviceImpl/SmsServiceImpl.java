package com.anil.swiftBus.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anil.swiftBus.service.SmsService;

@Service
public class SmsServiceImpl implements SmsService{
	
	private final RestTemplate rest = new RestTemplate();

    //@Value("${msg91.authKey}") private String authKey;
    //@Value("${msg91.senderId}") private String senderId;
	
	private String authKey;
    private String senderId;

    // setters for Spring XML injection
    public void setAuthKey(String authKey) { 
    	this.authKey = authKey; 
    }
    public void setSenderId(String senderId) { 
    	this.senderId = senderId; 
    }
	
	
	@Override
	public void send(String phone, String text) {
		// TODO Auto-generated method stub
		System.out.println("[SMS] to " + phone + ": " + text);
		
		/*String url = "https://api.msg91.com/api/sendhttp.php?authkey=" + authKey
                + "&mobiles=" + phone
                + "&message=" + UriUtils.encode(text, StandardCharsets.UTF_8)
                + "&sender=" + senderId
                + "&route=4&country=91";
				rest.getForObject(url, String.class);*/
		
	}
	
}
