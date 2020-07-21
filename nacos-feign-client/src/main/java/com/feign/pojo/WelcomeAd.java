package com.feign.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WelcomeAd implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String type;

    private String title;

    private String img;

    private String showUrl;

    private String content;

    private long duration;

}
