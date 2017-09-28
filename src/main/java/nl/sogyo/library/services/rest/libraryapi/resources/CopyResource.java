package nl.sogyo.library.services.rest.libraryapi.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;

import nl.sogyo.library.model.command.Library;
import nl.sogyo.library.services.rest.libraryapi.json.BookId;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;

@Path("copy")
public class CopyResource {

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public AddCopyMessage addCopy(BookId copyCommand) {
		AddCopyMessage addCopyMessage = Library.addCopy(copyCommand);
		return addCopyMessage;
	}
	
	@DELETE
	@Consumes("application/json")
	@Produces("application/json")
	public DeleteCopyMessage deleteCopy(BookId copyCommand) {
		DeleteCopyMessage deleteCopyMessage = Library.deleteCopy(copyCommand);
		System.out.println(" " + deleteCopyMessage.getCommandSucceeded()
				+ deleteCopyMessage.getCopiesOfBook()
				+ deleteCopyMessage.getMessage());
		return deleteCopyMessage;
	}
}
