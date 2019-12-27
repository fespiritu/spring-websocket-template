package lahsivjar.spring.websocket.template;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private MessageController messageController;
    
    @Autowired
    private SimpMessagingTemplate webSocket;
    
    @Scheduled(fixedRate = 5000)
    public Map<String, String> reportCurrentTime() {
    	String myTime = "The time is now " + dateFormat.format(new Date());
      log.info(myTime);
      
      // ChatMessage msg = new ChatMessage();

      Map<String,String> message = new HashMap<>();
      message.entrySet();
      message.put("author", "Freddie");
      message.put("authorId", "65NzLmZSey0zdmn4eB84p8f5Qvhuj6gB");
      message.put("message", myTime);
      Map<String, String> data = messageController.post(message);
      
      webSocket.convertAndSend("/topic/all", message);
      
      return data;
    }


}
