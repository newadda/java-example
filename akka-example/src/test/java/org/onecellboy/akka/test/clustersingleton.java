package org.onecellboy.akka.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.onecellboy.akka.actor.MyActor;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import akka.testkit.javadsl.TestKit;

public class clustersingleton {

	static ActorSystem system1;
	static ActorSystem system2;
	static ActorSystem system3;
	
	@BeforeClass
	  public static void setup() {
		Config load = ConfigFactory.load("application1.conf");
		
		system1 = ActorSystem.create("ClusterSystem",load);
		
		
		
		load = ConfigFactory.load("application2.conf");
		system2 = ActorSystem.create("ClusterSystem",load);
		
		load = ConfigFactory.load("application3.conf");
		system3 = ActorSystem.create("ClusterSystem",load);
	  }
	  
	  @AfterClass
	  public static void teardown() {
	    TestKit.shutdownActorSystem(system1);
	    TestKit.shutdownActorSystem(system2);

	    system1 = null;
	    system2 = null;

	  }

	  
	@Test
	public void test() {
		ClusterSingletonManagerSettings settings =
				  ClusterSingletonManagerSettings.create(system1);
		
		ClusterSingletonManagerSettings settings2 =
				  ClusterSingletonManagerSettings.create(system2);
		
		ClusterSingletonManagerSettings settings3 =
				  ClusterSingletonManagerSettings.create(system3);
		
		system1.actorOf(
				  ClusterSingletonManager.props(
				    Props.create(MyActor.class),
				    "END",
				    settings),
				  "consumer");
		
		
		system2.actorOf(
				  ClusterSingletonManager.props(
				    Props.create(MyActor.class),
				    "END",
				    settings2),
				  "consumer");
		
		system3.actorOf(
				  ClusterSingletonManager.props(
				    Props.create(MyActor.class),
				    "END",
				    settings3),
				  "consumer");
		
		
		ClusterSingletonProxySettings proxySettings1 =
			    ClusterSingletonProxySettings.create(system1);
		
		ClusterSingletonProxySettings proxySettings2 =
			    ClusterSingletonProxySettings.create(system2);
		
		
		ClusterSingletonProxySettings proxySettings3 =
			    ClusterSingletonProxySettings.create(system3);
		
		
		ActorRef proxy1 =
				  system1.actorOf(ClusterSingletonProxy.props("/user/consumer", proxySettings1),
				    "consumerProxy");
		
		ActorRef proxy2 =
				  system2.actorOf(ClusterSingletonProxy.props("/user/consumer", proxySettings2),
				    "consumerProxy");
		

		ActorRef proxy3 =
				  system3.actorOf(ClusterSingletonProxy.props("/user/consumer", proxySettings3),
				    "consumerProxy");
		
		
		
		
		
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 Cluster cluster = Cluster.get(system1);
		 cluster.leave(cluster.selfAddress());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		pause();
		
		
		
	}
	
	
	private void pause()
	{
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
