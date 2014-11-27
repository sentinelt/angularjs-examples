import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.atteo.moonshine.jta.Transactional;

@Path("/note")
@Consumes("application/json")
@Produces("application/json")
public class NoteResource {
	@Inject
	private EntityManager manager;

	@GET
	@Transactional
	public Note get() {
		try {
			return manager.createQuery("select n from Note n", Note.class).getSingleResult();
		} catch (NoResultException e) {
			Note note = new Note();
			note.setContent("Default message");
			manager.persist(note);
			return note;
		}
	}

	@POST
	@Transactional
	public void put(Note newNote) {
		manager.merge(newNote);
	}
}
