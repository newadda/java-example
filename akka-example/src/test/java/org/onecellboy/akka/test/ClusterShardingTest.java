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
		  TestKit.shutdownActorSystem(system3);

	    system1 = null;
	  //  system2 = null;

	  }

	
	@Test
	public void test() {
		Option<String> roleOption = Option.none();
	    ClusterShardingSettings settings1 = ClusterShardingSettings.create(system1);
		ClusterShardingSettings settings2 = ClusterShardingSettings.create(system2);
		ClusterShardingSettings settings3 = ClusterShardingSettings.create(system3);

		ShardRegion.MessageExtractor messageExtractor = new ShardRegion.MessageExtractor() {

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
					return new ExamplePersistentActor.Cmd("cmd" + String.valueOf(message));
				else
					return message;
			}

			@Override
			public String shardId(Object message) {
				int numberOfShards = 100;

				if(message instanceof  Integer)
				{
					Integer id = (Integer) message;
					return String.valueOf(id % numberOfShards);
				}
				else if (message instanceof ShardRegion.StartEntity) {
					long id = Long.valueOf(((ShardRegion.StartEntity) message).entityId());
					return String.valueOf(id % numberOfShards);
				}
				return null;
			}

		};
		ActorRef startedCounterRegion1 = ClusterSharding.get(system1).start("Counter",
	      Props.create(ExamplePersistentActor.class), settings1, messageExtractor );
		ActorRef startedCounterRegion2 = ClusterSharding.get(system2).start("Counter",
				Props.create(ExamplePersistentActor.class), settings2, messageExtractor );
		ActorRef startedCounterRegion3 = ClusterSharding.get(system3).start("Counter",
				Props.create(ExamplePersistentActor.class), settings3, messageExtractor );

		ActorRef counterRegion = ClusterSharding.get(system3).shardRegion("Counter");
	  
	    try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("========================================================");
		//final TestKit probe = new TestKit(system2);
		counterRegion.tell(new Integer(1), null);
		counterRegion.tell(new Integer(2),null);
		counterRegion.tell(new Integer(3), null);
	    
	   
	    
	    
	    System.out.println("end");
	    
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("=====================================================================");
	    TestKit.shutdownActorSystem(system1);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		counterRegion.tell(new Integer(1), null);
		counterRegion.tell(new Integer(2),null);
		counterRegion.tell(new Integer(3), null);
	    
	    
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
