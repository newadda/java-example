package org.onecellboy.akka.test;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class ConfigurationOverride {


    public void configurationOverride()
    {
        // 디폴트 설정파일(리소스 내의 파일)
        Config baseConfig = ConfigFactory.load("application.conf");

        // 덮어쓸 설정파일, 디폴트 설정파일을 덮어쓴다.
        // 외부 파일의 경로이다.
        Config load =ConfigFactory.parseFile(new File("application.conf")).withFallback(baseConfig).resolve();

        ActorSystem system = ActorSystem.create("ClusterSystem",load);
    }


}
