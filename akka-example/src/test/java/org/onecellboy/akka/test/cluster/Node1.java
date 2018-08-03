package org.onecellboy.akka.test.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import akka.routing.FromConfig;
import akka.testkit.javadsl.TestKit;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.onecellboy.akka.actor.MyActor;
import org.onecellboy.akka.cluster.SimpleClusterListener;
import scala.Option;
import scala.concurrent.duration.FiniteDuration;

import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Node1 {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        Config load = ConfigFactory.load("application1.conf");

        system = ActorSystem.create("ClusterSystem",load);


    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);


    }


    @Test
    public void _01test() {
        final TestKit probe = new TestKit(system);

        final Props props = Props.create(SimpleClusterListener.class,"111");
        ActorRef actorOf = system.actorOf(props, "zzz");



        ClusterSingletonManagerSettings settings =
                ClusterSingletonManagerSettings.create(system);

        ActorRef actorRef = system.actorOf(
                ClusterSingletonManager.props(
                        Props.create(MyActor.class),
                        "END",
                        settings),
                "consumer");


        ClusterSingletonProxySettings proxySettings1 =
                ClusterSingletonProxySettings.create(system);




        ActorRef proxy1 =
                system.actorOf(ClusterSingletonProxy.props("/user/consumer", proxySettings1),
                        "consumerProxy");



        pause();

    }






    private void pause()
    {
        try {
            System.in.read();
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
