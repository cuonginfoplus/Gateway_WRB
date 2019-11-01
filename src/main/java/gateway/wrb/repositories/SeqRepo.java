package gateway.wrb.repositories;

import gateway.wrb.model.SeqModel;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SeqRepo {

    public static final String KEY = "SEQ";
    private RedisTemplate<String, SeqModel> redisTemplate;
    private HashOperations hashOperations;

    public SeqRepo(RedisTemplate<String, SeqModel> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    /*Getting a specific item by item id from table*/
    public SeqModel getItem(String id) {
        return (SeqModel) hashOperations.get(KEY, id);
    }

    /*Adding an item into redis database*/
    public void addItem(SeqModel seqModel) {
        hashOperations.put(KEY, seqModel.getId(), seqModel);
    }

    /*delete an item from database*/
    public void deleteItem(String id) {
        hashOperations.delete(KEY, id);
    }

    /*update an item from database*/
    public void updateItem(SeqModel seqModel) {
        addItem(seqModel);
    }
}
