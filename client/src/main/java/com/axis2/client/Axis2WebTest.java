package com.axis2.client;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class Axis2WebTest {

    
    
   public static String ADDRESS="http://localhost:8080/AxisWebDemo/services/myService?wsdl";    
        
    public static void main(String[] args) throws IOException{    
        axisDemo();    
    }    
        
    @SuppressWarnings("rawtypes")    
    public static Object[] invoke(String method,Object[] params,Class[] classes) throws AxisFault{    
        //使用RPC方式调用WebService    
        RPCServiceClient client=new RPCServiceClient();    
        Options option=client.getOptions();    
            
        //指定调用的URL    
        EndpointReference reference=new EndpointReference(ADDRESS);    
        option.setTo(reference);    
            
        /*  
         * 设置要调用的方法  
         * http://ws.apache.org/axis2 为默认的（无package的情况）命名空间，  
         * 如果有包名，则为 http://axis2.webservice.demo.com ,包名倒过来即可  
         * method为方法名称  
         *   
         */    
        QName  qname=new QName("http://webservice.demo.com", method);    
            
        //调用远程方法,并指定方法参数以及返回值类型    
        Object[] result=client.invokeBlocking(qname,params,classes);    
            
        return result;    
            
    }    
        
    public static void axisDemo() throws AxisFault{    
        Object[] result=invoke("sayHello", new Object[]{"demo"}, new Class[]{String.class});    
        System.out.println(result[0]);    
        result=invoke("getAge", new Object[]{}, new Class[]{int.class});    
        System.out.println(result[0]);    
    }    
        
}
