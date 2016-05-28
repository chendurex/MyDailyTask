package com.tomcat.impl.chap2.application1;
 
import java.io.IOException; 
 
public class StaticResourceProcessor { 
 
   public void process(Request request, Response response) { 
     try { 
       response.sendStaticResource(); 
     } 
     catch (IOException e) { 
       e.printStackTrace(); 
     } 
   } 
} 

