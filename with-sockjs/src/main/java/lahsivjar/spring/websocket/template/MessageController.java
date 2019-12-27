package lahsivjar.spring.websocket.template;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private ChatHistoryDao chatHistoryDao;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    /*
     * This MessageMapping annotated method will be handled by
     * SimpAnnotationMethodMessageHandler and after that the Message will be
     * forwarded to Broker channel to be forwarded to the client via WebSocket
     */
    @MessageMapping("/all")
    @SendTo("/topic/all")
    public Map<String, String> post(@Payload Map<String, String> message) {
        message.put("timestamp", Long.toString(System.currentTimeMillis()));
        // log.info("message: " + message.toString());
        
        chatHistoryDao.save(message);
        return message;
    }

    @RequestMapping("/history")
    public List<Map<String, String>> getChatHistory() {
        return chatHistoryDao.get();
    }
}
