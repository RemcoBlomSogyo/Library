package com.rest;

import org.junit.Assert;
import org.junit.Test;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import com.rest.BooksResource;

public class RestTest extends JerseyTest {
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BooksResource.class);
	}
	
	@Test
	public void bla() {
		Response output = target("/books").request().get();
		System.out.println(output.getStatus());
		Assert.assertEquals(200, output.getStatus());
	}
}
