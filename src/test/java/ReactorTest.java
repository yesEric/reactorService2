import jdk.nashorn.internal.runtime.Debug;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;

public class ReactorTest {
    @Test
    public void testFibonacci() {
        Flux<Long> fibonacciGenerator = Flux.create(e -> {
                    long current = 1, prev = 0;
                    AtomicBoolean stop = new AtomicBoolean(false);
                    e.onDispose(()->{
                        stop.set(true);
                        System.out.println("*********Stop Received**********");
                    });
                    while (current>0){
                        e.next(current);
                        System.out.println("generated"+current);
                        long next=current+prev;
                        prev=current;
                        current=next;
                    }
                    e.complete();
                }
        );

        List<Long> fibonacciSeries = new LinkedList<>();
        int size = 50;
        fibonacciGenerator.take(size).subscribe(t -> {
            System.out.println("consuming" + t);
            fibonacciSeries.add(t);
        });
        System.out.println(fibonacciSeries);
      //  assertEquals(7778742049L, fibonacciSeries.get(size - 1).longValue());
    }

    @Test
    public void testFibonacci2(){
        Flux<Long> fibonacciGenerator=Flux.generate(() -> Tuples.<Long,Long>of(0L,1L),
                (state,sink) -> {
                   if(state.getT1()<0){
                       sink.complete();
                   }else {
                      sink.next(state.getT1());
                   }
                   return  Tuples.of(state.getT2(),state.getT1()+state.getT2());
                });

        fibonacciGenerator.skipUntil(t->t>100).subscribe(t -> {
            System.out.println(t);
        });
    }

    @Test
    public void testMapConvert(){
        Flux<Long> fibonacciGenerator=Flux.generate(() -> Tuples.<Long,Long>of(0L,1L),
                (state,sink) -> {
                    if(state.getT1()<0){
                        sink.complete();
                    }else {
                        sink.next(state.getT1());
                    }
                    return  Tuples.of(state.getT2(),state.getT1()+state.getT2());
                });

        RomanNumber numberConvertor=new RomanNumber();
        fibonacciGenerator.skip(1).take(10).map(t ->  numberConvertor.toRomanNumeral(t.intValue())).subscribe(t->{
            System.out.println(t);
        });
    }

    @Test
    public void testFlatMap(){
        Flux<Long> fibonacciGenerator=Flux.generate(() -> Tuples.<Long,Long>of(0L,1L),
                (state,sink) -> {
                    if(state.getT1()<0){
                        sink.complete();
                    }else {
                        sink.next(state.getT1());
                    }
                    return  Tuples.of(state.getT2(),state.getT1()+state.getT2());
                });

        Factorization numberConvertor=new Factorization();
        fibonacciGenerator.skip(1).take(10).map(t ->  numberConvertor.findFactor(t.intValue())).subscribe(t->{
            System.out.println(t);
        });
    }
}
