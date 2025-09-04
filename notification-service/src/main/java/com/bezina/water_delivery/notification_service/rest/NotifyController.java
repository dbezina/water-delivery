package com.bezina.water_delivery.notification_service.rest;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/notifications")
public class NotifyController {

    // список активных подключений
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@RequestHeader("X-User-Id") String userId,
                              @RequestHeader("X-User-Role") String role) {

        // создаём emitter с таймаутом (например, 30 мин)
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);
        emitters.add(emitter);

        // удаляем emitter при закрытии/ошибке
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        try {
            // сразу отправляем "connected"
            emitter.send(SseEmitter.event().name("init").data("{\"msg\":\"connected\"}"));
        } catch (IOException e) {
            emitters.remove(emitter);
        }

        return emitter;
    }

    // пример: ручка для отправки уведомлений
    @PostMapping("/send")
    public void sendMessage(@RequestBody String message) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("message").data(message));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }

}