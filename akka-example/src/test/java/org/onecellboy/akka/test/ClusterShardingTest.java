package org.onecellboy.akka.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.onecellboy.akka.persistence.ExamplePersistentActor;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import akka.cluster.sharding.ShardRegion;
import akka.japi.Option;
import akka.testkit.javadsl.TestKit;

public class ClusterShardingTest {
	static ActorSystem system1;
	static ActorSystem system2;
	
	@BeforeClass
	  public static void setup() {
		Config load = ConfigFactory.load("application1.conf");
		
		system1 = ActorSystem.create("ClusterSystem",load);
		
		
		
		// load = ConfigFactory.load("application2.conf");
		//system2 = ActorSystem.create("ClusterSystem",load);
	  }
	  
	  @AfterClass
	  public static void teardown() {
	    TestKit.shutdownActorSystem(system1);
	 //   TestKit.shutdownActorSystem(system2);

	    system1 = null;
	  //  system2 = null;

	  }

	
	@Test
	public void test() {
		Option<String> roleOption = Option.none();
	    ClusterShardingSettings settings = ClusterShardingSettings.create(system1);
	    ActorRef startedCounterRegion = ClusterSharding.get(system1).start("Counter",
	      Props.create(ExamplePersistentActor.class), settings,  new ShardRegion.MessageExtractor() {

	        @Override
	        public String entityId(Object message) {
	          if (message instanceof Integer)
	            return String.valueOf(message);
	          else
	            return null;
	        }

	        @Override
	        public Object entityMessage(Object message) {
	          if (message instanceof Integer)
	            return new ExamplePersistentActor.Cmd("cmd"+String.valueOf(message));
	          else
	            return message;
	        }

	        @Override
	        public String shardId(Object message) {
	          int numberOfShards = 100;
	        
	            Integer id = (Integer)message;
	            return String.valueOf(id % numberOfShards);

	        }

	      });
	    
	    
	  
	    try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		final TestKit probe = new TestKit(system2);
	    startedCounterRegion.tell(new Integer(1), probe.getRef());
	    startedCounterRegion.tell(new Integer(2), probe.getRef());
	    startedCounterRegion.tell(new Integer(3), probe.getRef());
	    
	   
	    
	    
	    System.out.println("end");
	    
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    TestKit.shutdownActorSystem(system1);
	    
	    
	    
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
