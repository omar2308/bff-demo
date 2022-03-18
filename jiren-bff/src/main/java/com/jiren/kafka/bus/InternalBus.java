package com.jiren.kafka.bus;

import com.jiren.kafka.exception.KafkaException;
import com.jiren.kafka.exception.KafkaTimeoutException;
import com.jiren.kafka.support.JsonParser;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author Omar
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class InternalBus {
    private static final long WAIT_FOR_CYCLE = 100;
    private static final long TIMEOUT_WAIT = 10000;
    private final Map<String, String> bus = new HashMap<>();
    private final Lock lockPut = new ReentrantLock();
    private final Lock lockRemoveOrGet = new ReentrantLock();
    private final JsonParser jsonParse;
    /**
     * 
     * @param content
     * @return The key of the element
     */
    public String add(String content){
        String key = generateId();
        lockPut.lock();
        try {
            bus.put(key, content);
            
        } catch (Exception e) {
            throw new KafkaException("Error al colocar mensaje en el Bus", e);
        } finally {
            lockPut.unlock();
        }
        
        return key;
    }
    
    public void put(String key, Object obj){
        String content = jsonParse.toJson(obj);
        bus.put(key, content);
    }
    
    public void put(String key, String content){
        lockPut.lock();
        try {
            bus.put(key, content);
            
        } catch (Exception e) {
            throw new KafkaException("Error al colocar mensaje en el Bus", e);
        } finally {
            lockPut.unlock();
        }
    }
    
    private String generateId(){
        return Thread.currentThread().getId() + "";
    }
    
    public <C> C waitAndRemove(String key, Class<C> claz){
        String content = waitAndRemove(key);
        C c = jsonParse.toObj(content, claz);
        return c;
    }
    
    public String waitAndRemove(String key){
        boolean continueWating = true;
        String result = null;
        long init = System.currentTimeMillis();
        while(continueWating){
            if(bus.containsKey(key)){
               result = remove(key);
               continueWating = false;
            }else{
                waitOrThrow(init);
            }
            
        }
        return result;
    }

    private void waitOrThrow(long init) throws KafkaTimeoutException {
        waitFor(WAIT_FOR_CYCLE);
        long current = System.currentTimeMillis();
        if(current - init > TIMEOUT_WAIT){
            throw new KafkaTimeoutException("Timeout waiting for response message");
        }
    }
    
    private void waitFor(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.error("Error en espera de hilo", e);
        }
    }
    
    public String get(String key){
        lockRemoveOrGet.lock();
        try {
            return bus.get(key);
        } catch (Exception e) {
            throw new KafkaException("Error al remover mensaje en el Bus", e);
        } finally {
            lockRemoveOrGet.unlock();
        }
    }
    
    public String remove(String key){
        lockRemoveOrGet.lock();
        try {
            return bus.remove(key);
        } catch (Exception e) {
            throw new KafkaException("Error al remover mensaje en el Bus", e);
        } finally {
            lockRemoveOrGet.unlock();
        }
    }
    
    
}
