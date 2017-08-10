package com.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//@Path("/")
//@Path("{filename}")
//public class IndexResource {
//	@GET
//	@Produces(MediaType.TEXT_HTML)
//	public String sayHi() {
//		return "<form action=\"books\"><input type=\"submit\" value=\"Get all books\"/></form>";
//	}
//	
//	
//	@GET
//	public InputStream getIndex(@PathParam("filename") String fileName){
//		File index = new File("C:\\Users\\rblom\\Documents\\apache-tomcat\\webapps\\SogyoLibrary\\src\\main\\webapp\\public\\" + fileName);
//		System.out.println("check");
//		try {
//		    return new FileInputStream(index);
//		} catch (FileNotFoundException e) {
//		    String s = "ERROR";
//		    return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
//		}  
//	}
//}

