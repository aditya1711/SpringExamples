package com.ggktech.poogleFormsSpring.service.springConfigs;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PoogleFormsApplicationListener implements ApplicationListener {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// TODO Auto-generated method stub
		System.out.println("EVENT:\n" + event.toString());
	}

}
