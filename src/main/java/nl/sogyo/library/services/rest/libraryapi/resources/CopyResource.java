package nl.sogyo.library.services.rest.libraryapi.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;

import nl.sogyo.library.model.command.Library;
import nl.sogyo.library.services.rest.libraryapi.json.BookId;
import nl.sogyo.library.services.rest.libraryapi.json.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.AddCopyMessage;

@Path("copy")
public class CopyResource {

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public AddCopyMessage addCopy(BookId copyCommand) {
		System.out.println("addCopy " + copyCommand.getBookId());
		AddCopyMessage addCopyMessage = Library.addCopy(copyCommand);
		System.out.println(" " + addCopyMessage.getCommandSucceeded()
				+ addCopyMessage.getCopiesOfBook()
				+ addCopyMessage.getMessage());
		return addCopyMessage;
	}
	
	@DELETE
	@Consumes("application/json")
	@Produces("application/json")
	public DeleteCopyMessage deleteCopy(BookId copyCommand) {
		System.out.println("deleteCopy " + copyCommand.getBookId());
		DeleteCopyMessage deleteCopyMessage = Library.deleteCopy(copyCommand);
		System.out.println(" " + deleteCopyMessage.getCommandSucceeded()
				+ deleteCopyMessage.getCopiesOfBook()
				+ deleteCopyMessage.getMessage());
		return deleteCopyMessage;
	}
}
