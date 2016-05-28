package com.tomcat.impl.chap2.application2;
public class ResponseFacade implements ServletResponse { 
   private ServleLResponse response = null; 
 
   public ResponseFacade(Response response) { 
     this.response = response; 
   } 
 
   /* implementation of the ServletResponse*/ 
   ... 
} 

