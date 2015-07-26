package net.sf.otrcutmp4.controller.web.mbean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class TestBean implements Serializable
{
	private static final long serialVersionUID = 1;
	final static Logger logger = LoggerFactory.getLogger(TestBean.class);
	
	private String value;
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}

	@PostConstruct
	public void init()
	{
		logger.info("Init!");
		Date now = new Date();
		value = now.toString();
	}
}
