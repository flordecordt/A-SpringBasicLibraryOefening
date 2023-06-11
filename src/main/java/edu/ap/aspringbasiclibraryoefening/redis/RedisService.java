package edu.ap.aspringbasiclibraryoefening.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;


import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringTemplate;

    // Methode om de Redis-verbinding op te halen
    private RedisConnection getConnection() {
        return this.stringTemplate.getConnectionFactory().getConnection();
    }

    // Methode om een waarde aan een sleutel toe te wijzen
    public void setKey(String key, String value) {
        this.stringTemplate.opsForValue().set(key, value);
    }

    // Methode om de waarde van een sleutel op te halen
    public String getKey(String key) {
        return this.stringTemplate.opsForValue().get(key);
    }

    // Methode om te controleren of een sleutel aanwezig is
    public boolean hasKey(String key) {
        return this.stringTemplate.hasKey(key);
    }

    // Methode om sleutels op te halen op basis van een patroon
    public Set<String> keys(String pattern) {
        return this.stringTemplate.keys(pattern);
    }

    // Methode om een hash in te stellen met meerdere velden en waarden
    public void hset(String key, Map<String, String> values) {
        this.stringTemplate.opsForHash().putAll(key, values);
    }

    // Methode om een hash op te halen met alle velden en waarden
    public Map<Object, Object> hgetAll(String key) {
        return stringTemplate.opsForHash().entries(key);
    }

    // Methode om een waarde aan het einde van een lijst toe te voegen (rechts)
    public Long rpush(String key, String value) {
        return this.stringTemplate.opsForList().rightPush(key, value);
    }

    // Methode om een waarde aan het begin van een lijst toe te voegen (links)
    public Long lpush(String key, String value) {
        return this.stringTemplate.opsForList().leftPush(key, value);
    }

    // Methode om een lijst op te halen met alle waarden
    public List<String> getList(String key) {
        return this.stringTemplate.opsForList().range(key, 0, -1);
    }

    // Methode om een bericht te verzenden naar een Redis-kanaal
    public void sendMessage(String channel, String message) {
        this.stringTemplate.convertAndSend(channel, message);
    }

    // Methoden zonder template-interface (directe Redis-verbinding)

    // Methode om een sleutel te verhogen met 1 (atomische operatie)
    public Long incr(String key) {
        return this.getConnection().incr(key.getBytes());
    }
    // Methode om een sleutel te verlagen met 1 (atomische operatie)
    public Long decr(String key) {
        return this.getConnection().decr(key.getBytes());
    }


    // Methode om een bit in te stellen op een bepaalde offset
    public Boolean setBit(String key, int offset, boolean value) {
        return this.getConnection().setBit(key.getBytes(), offset, value);
    }

    // Methode om de waarde van een bit op een bepaalde offset op te halen
    public Boolean getBit(String key, int offset) {
        return this.getConnection().getBit(key.getBytes(), offset);
    }

    // Methode om het aantal ingestelde bits in een sleutel te tellen
    public Long bitCount(String key) {
        return this.getConnection().bitCount(key.getBytes());
    }

    // Methode om de Redis-database te wissen
    public void flushDb() {
        this.getConnection().flushDb();
    }

    // Methode om alle rijen voor een bepaalde sleutel te verwijderen
    public void deleteKey(String key) {
        this.stringTemplate.delete(key);
    }

}

/*
 // ValueOperations, BoundValueOperations
stringTemplate.opsForValue().set(key, value); 
stringTemplate.boundValueOps(key).set(value); 

// HashOperations, BoundHashOperations
stringTemplate.opsForHash().put(key, "hashKey", value); 
stringTemplate.boundHashOps(key).put("hashKey", value); 

// ListOperations, BoundListOperations
stringTemplate.opsForList().leftPush(key, value); 
stringTemplate.opsForList().rightPush(key, value); 
stringTemplate.opsForList().rightPop(key, 1, TimeUnit.SECONDS); 
stringTemplate.opsForList().leftPop(key, 1, TimeUnit.SECONDS); 
stringTemplate.boundListOps(key).leftPush(value); 
stringTemplate.boundListOps(key).rightPush(value); 
stringTemplate.boundListOps(key).rightPop(1, TimeUnit.SECONDS); 
stringTemplate.boundListOps(key).leftPop(1, TimeUnit.SECONDS); 

// ZSetOperations, BoundZSetOperations
stringTemplate.opsForZSet().add(key, "player1", 12.0d); 
stringTemplate.opsForZSet().add(key, "player2", 11.0d); 
stringTemplate.boundZSetOps(key).add("player1", 12.0d); 
stringTemplate.boundZSetOps(key).add("player2", 11.0d);

// Misc
stringTemplate.getConnectionFactory().getConnection().bitOp(BitOperation.AND, arg1, arg2);
stringTemplate.expire(key, 1, TimeUnit.SECONDS);
stringTemplate.opsForHyperLogLog().add(arg0, arg1);
*/
