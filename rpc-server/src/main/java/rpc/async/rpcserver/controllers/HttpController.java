package rpc.async.rpcserver.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HttpController {
    @GetMapping("/httpResponse")
    public String httpResponse(String msg) {
        log.info("client "+msg);
        return "I am the http server, I received the message from the client： "+msg+" server response";
    }
}
