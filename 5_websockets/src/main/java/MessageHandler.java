
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ServerEndpoint("/note-websocket")
public class MessageHandler {
	private Logger logger = LoggerFactory.getLogger(MessageHandler.class);

	@Inject
	private NoteResource noteResource;

	private static final List<Session> openedSessions = new ArrayList<>();

	@OnOpen
	public void open(Session session) {
		logger.info("Opened session: " + session);
		openedSessions.add(session);
		session.getAsyncRemote().sendText(noteResource.get().getContent());
	}

	@OnClose
	public void close(Session session) {
		logger.info("Closed session: " + session);
		openedSessions.remove(session);
	}

	@OnMessage
	public void onMessage(String note, Session session) {
		logger.info("onMessage: " + note + ", session: " + session);
		Note n = new Note();
		n.setContent(note);
		noteResource.put(n);

		for (Session openedSession : openedSessions) {
			if (!openedSession.equals(session)) {
				logger.info("Sending update to session: " + openedSession);
				openedSession.getAsyncRemote().sendText(note);
			}
		}
	}

}
