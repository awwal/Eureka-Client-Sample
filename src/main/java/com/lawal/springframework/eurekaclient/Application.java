package com.lawal.springframework.eurekaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lawal Olufowobi on 17/07/2015.
 */

@SpringBootApplication
@EnableEurekaClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

@RestController
@RequestMapping("/hello")
class Controller {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String sayHello(@RequestParam(defaultValue = "world") String where) {
		return "Hello " + where;
	}

}
