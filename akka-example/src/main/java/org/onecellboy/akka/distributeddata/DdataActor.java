package org.onecellboy.akka.distributeddata;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.cluster.Cluster;
import akka.cluster.ddata.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofSeconds;
import static scala.concurrent.duration.Duration.create;

public class DdataActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public static class ExampleState implements Serializable
    {
        private static final long serialVersionUID = 1L;
        private  Integer count = new Integer(0);
    }
    private ExampleState state = new ExampleState();
    Cancellable scheduleCancellable;


    private Cluster node = Cluster.get(getContext().getSystem());

    private final String key = "singleton-data";
    private final Key<LWWMap<String, Object>> dataKey = LWWMapKey.create(key);

    private final ActorRef replicator =
            DistributedData.get(getContext().getSystem()).replicator();



    @Override
    public void postStop() throws Exception {
        log.info("postStop()");
        super.postStop();

    }



    @Override
    public void preStart() throws Exception {
        log.info("preStart()");

        super.preStart();

        /**
         * 분산 데이터에 대한 구독 생성 , 해당 key 에 대한 작업에 대한 이벤트를 받을 수 있다.
         */
        Replicator.Subscribe<LWWMap<String, Object>> subscribe = new Replicator.Subscribe<>(dataKey, getSelf());
        replicator.tell(subscribe, ActorRef.noSender());
    }

    public void schedule()
    {
        scheduleCancellable = getContext().getSystem().scheduler().scheduleOnce(create(3, TimeUnit.SECONDS),
                new Runnable() {
                    @Override
                    public void run() {
                        getSelf().tell("timer", ActorRef.noSender());
                    }
                }, getContext().getSystem().dispatcher());
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
                    // log.info("====== 데이터(순번) 받음 : {}", state.count );

                    // saveSnapshot(state);
                    // state.count = state.count+1;
                    //  saveData( key,state.count );
                    // schedule();
                    get();
                }  )
                /* 분산 데이터 얻기 성공시*/
                .match(Replicator.GetSuccess.class, g -> receiveGetSuccess((Replicator.GetSuccess<LWWMap<String, Object>>) g))
                /* 분산 데이터의 해당 키가 존재하지 않을 때*/
                .match(Replicator.NotFound.class, a -> {
                    saveData(key,new Integer(0));
                    schedule();
                })
                .matchAny(o->{
                    log.info("Received unKnown message : {}",o);
                    //  schedule();
                })
                .build();

    }

    /**
     * 데이터 얻기 요청
     */
    private void get() {
        // final Replicator.ReadConsistency readAll = new Replicator.ReadAll(ofSeconds(5));
        //   final Replicator.ReadConsistency readFrom = new Replicator.ReadFrom(1, ofSeconds(3));
        Replicator.Get<LWWMap<String, Object>> get = new Replicator.Get<>(dataKey, Replicator.readLocal());
        replicator.tell(get, self());
    }

    /**
     * 데이터 얻기 요청에 대한 응답, 응답의 request() 는 초기에 데이터 얻기 요청에서 보낸 데이터이다.
     * request() 데이터는 응답을 받았을 때 어떤 요청에 대한 응답인지를 구분하기 위해 사용한다.
     * @param g
     */
    private void receiveGetSuccess(Replicator.GetSuccess<LWWMap<String, Object>> g) {
        Option<Object> valueOption = g.dataValue().get("data");
        Optional<Object> valueOptional = Optional.ofNullable(valueOption.isDefined() ? valueOption.get() : new Integer(0));
        state.count=(Integer)valueOption.get();
        log.info("====== 데이터(순번) 받음 : {}", state.count );
        saveData(key,state.count+1);
        schedule();
    }

    /**
     * 분산 데이터 저장소에 데이터 저장하기
     * @param key
     * @param count
     */
    public void saveData(String key,Integer count)
    {
        final Replicator.WriteConsistency writeAll = new Replicator.WriteAll(ofSeconds(5));
        Replicator.Update<LWWMap<String, Object>> update = new Replicator.Update<>( LWWMapKey.create(key), LWWMap.create(),writeAll,
                curr -> curr.put(node,"data",count));


        replicator.tell(update, self());
    }


}
