package com.demo.spring.webservice;
import java.util.Random;    
    
import org.springframework.stereotype.Component;    
    
@Component("springWebService")    
public class SpringWebServiceDemo {    
        
    public String springHello(){    
        return "hello spring-axis2";     
    }    
        
    public int getAge(){    
        return new Random().nextInt(80);    
    }    
        
    public void update(){    
        System.out.println("update something..");    
    }    
}   