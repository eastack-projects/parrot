package me.eastack.parrot.article;

import com.google.gson.Gson;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import lombok.Data;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class ArticleApplicationTests {

    @Test
    void contextLoads() {
        Flux.just("Ben", "Michael", "Mark")
            .doOnNext(s -> System.out.println("Hello " + s + "!"))
            .doOnComplete(() -> System.out.println("Completed"))
            .take(2)
            .subscribe();
    }

    @Test
    void testConnection() {
        RedisClient client = RedisClient.create("redis://localhost");
        RedisReactiveCommands<String, String> commands = client.connect().reactive();
//        commands.set("foo3", "39").subscribe(System.out::println);
//
//        Flux.just("foo", "foo1", "foo2", "foo3")
//            .flatMap(commands::get)
//            .flatMap(value -> commands.rpush("result2", value))
//            .subscribe(System.out::println);
        commands.setAutoFlushCommands(false);

        Flux.just("set_result", "set_result")
            .flatMap(commands::scard)
            .reduce(Long::sum)
            .doOnError(error -> System.out.println(error))
            .subscribe(result -> System.out.println("Number of elements in sets: " + result));
    }

    @Test
    public void testKafka() {
        A a = new A();
        a.name = "A";
        a.b = new B();
        a.b.a = a;
        a.b.name = "B";
        System.out.println(new Gson().toJson(new A()));
    }

    @Data
    class A {
        public String name;
        public B b;
    }

    @Data
    class B {
        public String name;
        public A a;
    }
}
