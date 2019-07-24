package cn.itcast.farming;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "cn.itcast.farming")
public class FarmingBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(FarmingBootstrap.class, args);
    }

}
