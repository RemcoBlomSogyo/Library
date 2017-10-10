//package nl.sogyo.library.services;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.core.Request;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.ResponseBuilder;
//
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.test.JerseyTest;
//import org.glassfish.jersey.test.TestProperties;
//import org.junit.Assert;
//import org.junit.Test;
//
//import nl.sogyo.library.services.rest.libraryapi.helper.CORSResponseFilter;
//import nl.sogyo.library.services.rest.libraryapi.resources.BookResource;
//
//public class HelperTest extends JerseyTest {
//	
//	@Override
//	public Application configure() {
//		enable(TestProperties.LOG_TRAFFIC);
//		enable(TestProperties.DUMP_ENTITY);
//		return new ResourceConfig(BookResource.class);
//	}
//	
//	@Test
//	public void requestForBookInfoOfProRestfulApisHasRequestHeadersForCORS() {
//		Response response = target("/book").path("46").request().get(Response.class);
//		
//		MultivaluedMap<String, Object> map = response.getHeaders();
//
//		List<Boolean> containCheckList = new ArrayList<Boolean>();
//		containCheckList.add(map.containsKey("Access-Control-Allow-Origin"));
//		containCheckList.add(map.containsKey("Access-Control-Allow-Methods"));
//		containCheckList.add(map.containsKey("Access-Control-Allow-Headers"));
//		containCheckList.add(map.containsKey("Server"));
//		containCheckList.add(map.containsValue("*"));
//		containCheckList.add(map.containsValue("GET, POST, DELETE, PUT, OPTIONS, HEAD"));
//		containCheckList.add(map.containsValue("X-Requested-With, Content-Type, X-Codingpedia"));
//		for (Boolean bool : containCheckList) {
//			System.out.println(bool);
//		}
//		Assert.assertFalse(containCheckList.contains(Boolean.FALSE));
//	}
//}
