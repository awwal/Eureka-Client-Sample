package com.lawal.springframework.eurekaclient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.List;


@SpringBootApplication
@EnableEurekaClient
public class DisApplication {

	public static void main(String[] args) {

		System.setProperty("spring.application.name", "HelloClient");

		new SpringApplicationBuilder(DisApplication.class).web(false).run(args);
	}
}

//		server.port=8008
//		eureka.client.serviceUrl.defaultZone=http://localhost/eureka/
//		eureka.instance.metadataMap.instanceId = ${spring.application.name}-${server.port}
//		eureka.instance.metadataMap.version=8
//		spring.application.name=HelloService

@Component
class Service1Client implements CommandLineRunner {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Override
	public void run(String... strings) throws Exception {

		com.netflix.discovery.shared.Application application = discoveryClient.getApplication("HELLOSERVICE");
		List<InstanceInfo> instanceInfoList = application.getInstances();

		// sort descending by version metadata.
		InstanceInfo latest = instanceInfoList.stream().sorted(new Comparator<InstanceInfo>() {
			@Override
			public int compare(InstanceInfo o1, InstanceInfo o2) {
				int v1 = getVersion(o1);
				int v2 = getVersion(o2);

				return Integer.compare(v2, v1);
			}

			private int getVersion(InstanceInfo o1) {
				String metaVer = o1.getMetadata().get("version");
				if (metaVer == null)
					return -1;
				return Integer.parseInt(metaVer);
			}

		}).findFirst().get();

		System.out.println(new Date(latest.getLastDirtyTimestamp()) + " " + latest.getMetadata().get("instanceId"));

	}

}
