package org.onecellboy.akka.actor;

import java.util.concurrent.TimeUnit;

import akka.actor.*;
import akka.cluster.pubsub.DistributedPubSub;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.FI.UnitApply;
import akka.japi.pf.Match;
import scala.concurrent.duration.Duration;




public class MyActor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);



	@Override
	public void postStop() throws Exception {
		 log.info("postStop()");
		super.postStop();

	}



	@Override
	public void preStart() throws Exception {
		 log.info("preStart()");
		super.preStart();
	}



	/**
	 *
	 * 이 부분이 메세지를 받는 부분이다.
	 * */
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub

		return receiveBuilder()
				.match(String.class,  s->{
					 log.info("Received String message: {}", s);
				}  )
				.matchAny(o->{
					log.info("Received unKnown message");
				})

				.build();


	}

}
