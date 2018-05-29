package Com.IFI.InternalTool;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//@SpringBootApplication
//@Configuration
//@EntityScan("Com.IFI.InternalTool.DS.Model")
//@ComponentScan({ "Com.IFI.InternalTool.Security","Com.IFI.InternalTool.BS.Service","Com.IFI.InternalTool.BS.Controller"})
//@EnableJpaRepositories("Com.IFI.InternalTool.DS.DAO")
//@EnableAutoConfiguration
@SpringBootApplication
@EntityScan(basePackageClasses = {
		InternalToolApplication.class,
		Jsr310JpaConverters.class
})
public class InternalToolApplication {
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	public static void main(String[] args) {
		SpringApplication.run(InternalToolApplication.class, args);
	}
}
