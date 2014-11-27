
import javax.xml.bind.annotation.XmlRootElement;

import org.atteo.moonshine.TopLevelService;
import org.atteo.moonshine.services.ImportService;
import org.atteo.moonshine.websocket.WebSocketContainerService;

import com.google.inject.Module;
import com.google.inject.PrivateModule;

@XmlRootElement(name = "note-service")
public class NoteService extends TopLevelService {
	@ImportService
	private WebSocketContainerService websockets;

	@Override
	public Module configure() {
		return new PrivateModule() {
			@Override
			protected void configure() {
				bind(NoteResource.class);
				bind(MessageHandler.class);
				websockets.addAnnotatedEndpoint(MessageHandler.class, getProvider(MessageHandler.class));
			}
		};
	}
}
