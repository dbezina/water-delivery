package com.bezina.water_delivery.notification_service.rest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


//@RestController
//@RequestMapping("/notifications")
public class NotificationController {

    // userId → emitter
    private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();
    // role → список emitters
    private final Map<String, List<SseEmitter>> roleEmitters = new ConcurrentHashMap<>();


 /*   @GetMapping(path = "/notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public void stream(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write("data: {\"msg\":\"connected\"}\n\n");
        writer.flush();
    }

   /* @GetMapping(path = "/notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestHeader("X-User-Id") String userId,
                             @RequestHeader("X-User-Role") String role) {

        SseEmitter emitter = new SseEmitter(0L); // 0 = без таймаута
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data("{\"msg\":\"ping\"}"));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }, 0, 10, TimeUnit.SECONDS);
        // Лог для проверки
        System.out.println("🔔 SSE подключился: user=" + userId + ", role=" + role);


        return emitter;
    }*/

  /*  @GetMapping
    public SseEmitter connect(@RequestHeader("X-User-Id") String userId,
                              @RequestHeader("X-User-Role") String role) {

        SseEmitter emitter = new SseEmitter(0L); // без таймаута
        userEmitters.put(userId, emitter);

        roleEmitters.computeIfAbsent(role, r -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> {
            userEmitters.remove(userId);
            roleEmitters.getOrDefault(role, List.of()).remove(emitter);
        });
        emitter.onTimeout(() -> {
            userEmitters.remove(userId);
            roleEmitters.getOrDefault(role, List.of()).remove(emitter);
        });
        emitter.onError(e -> {
            userEmitters.remove(userId);
            roleEmitters.getOrDefault(role, List.of()).remove(emitter);
        });

        // Лог для проверки
        System.out.println("🔔 SSE подключился: user=" + userId + ", role=" + role);

        return emitter;
    }*/

    public void sendToUser(String userId, Object payload) {
        SseEmitter emitter = userEmitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data(payload));
            } catch (IOException e) {
                userEmitters.remove(userId);
            }
        }
    }

    public void sendToRole(String role, Object payload) {
        List<SseEmitter> emitters = roleEmitters.getOrDefault(role, List.of());
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data(payload));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}

