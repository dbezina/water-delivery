package com.bezina.water_delivery.notification_service.rest;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/notifications")

public class NotificationController {
    private String user;
    private String role;

    public NotificationController() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // userId → emitter
    private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();
    // role → список emitters
    private final Map<String, List<SseEmitter>> roleEmitters = new ConcurrentHashMap<>();


    // список активных подключений
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping
    public SseEmitter connect(@RequestHeader("X-User-Id") String userId,
                              @RequestHeader("X-User-Role") String role) {
        setUser(userId);
        setRole(role);

        // создаём emitter с таймаутом (например, 30 мин)
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);
        emitters.add(emitter);

        SseEmitter emitterUser = new SseEmitter(30 * 60 * 1000L);
        userEmitters.put(userId, emitterUser);


        roleEmitters.computeIfAbsent(role, r -> new CopyOnWriteArrayList<>()).add(emitterUser);


        // удаляем emitter при закрытии/ошибке
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        emitterUser.onCompletion(() -> emitters.remove(emitterUser));
        emitterUser.onTimeout(() -> emitters.remove(emitterUser));
        emitterUser.onError(e -> emitters.remove(emitterUser));


        try {
            // сразу отправляем "connected"
            emitter.send(SseEmitter.event().name("init").data("{\"msg\":\"connected\"}"));
        } catch (IOException e) {
            emitters.remove(emitter);
        }
        try {
            // сразу отправляем "connected"
            emitterUser.send(SseEmitter.event().name("first").data("{\"user\":\"user_role\"}"));
        } catch (IOException e) {
            userEmitters.remove(emitterUser);
        }

        return emitter;
    }

    // пример: ручка для отправки уведомлений
    @PostMapping("/send")
    public void sendMessage(@RequestBody String message,
                            @RequestHeader("X-User-Id") String userId,
                            @RequestHeader("X-User-Role") String role) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("message").data(message));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
        sendToUser(userId, Map.of(
                "type", "NEW_ORDER",
                "orderNo", "some order",
                "address", "Some Address"
        ));
        System.out.println("📦 Sent new order notification to user " + userId);
    }
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

    public void sendMessageFromKafka( Object payload) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                      //  .name("message")
                        .data(payload));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
        System.out.println("📦 Sent new notification to user " + user);
    }

}