package me.eastack.parrot.article;

import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.io.InputStreamReader;
import java.util.Optional;

@Data
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class Config {
    private final Gson gson;
    private Server server;
    private R2dbc r2dbc;
    private Kafka kafka;

    public Config load(String environment) {
        return Optional
            .ofNullable(environment)
            .map(env -> String.format("%s.json", env))
            .map(Config.class.getClassLoader()::getResourceAsStream)
            .map(InputStreamReader::new)
            .map(reader -> gson.fromJson(reader, Config.class))
            .orElseThrow();
    }

    @Data
    static class Server {
        private Integer port;
        private String host;
    }

    @Data
    static class R2dbc {
        private String username;
        private String password;
        private String host;
        private String port;
    }

    @Data
    static class Kafka {
        private String host;
    }
}
