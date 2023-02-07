package AlsongDalsong_backend.AlsongDalsong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AlsongDalsongApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlsongDalsongApplication.class, args);
	}

}

 