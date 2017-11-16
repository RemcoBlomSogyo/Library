package nl.sogyo.library.services.logic;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;

public abstract class RestResource {

	@HeaderParam(HttpHeaders.AUTHORIZATION)
	protected String idToken;
}
