package com.tomcat.impl.chap2.application2;
public class RequestFacade implements ServletRequest { 
   private ServleLRequest request = null; 
 
   public RequestFacade(Request request) { 
     this.request = request; 
   } 
 
   /* implementation of the ServletRequest*/ 
   public Object getAttribute(String attribute) { 
     return request.getAttribute(attribute); 
   } 
 
   public Enumeration getAttributeNames() { 
     return request.getAttributeNames(); 
   } 
 
   ... 
} 

