package gateway.wrb.repositories.impl;

import gateway.wrb.model.SeqModel;
import gateway.wrb.repositories.SeqRepo;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SeqRepoImpl implements SeqRepo {

    public static final String KEY = "SEQ";
    private RedisTemplate<String, SeqModel> redisTemplate;
    private HashOperations hashOperations;

    public SeqRepoImpl(RedisTemplate<String, SeqModel> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    /*Getting a specific item by item id from table*/
    @Override
    public SeqModel getItem(String id) {
        return (SeqModel) hashOperations.get(KEY, id);
    }

    /*Adding an item into redis database*/
    @Override
    public void addItem(SeqModel seqModel) {
        hashOperations.put(KEY, seqModel.getId(), seqModel);
    }

    /*delete an item from database*/
    @Override
    public void deleteItem(String id) {
        hashOperations.delete(KEY, id);
    }

    /*update an item from database*/
    @Override
    public void updateItem(SeqModel seqModel) {
        addItem(seqModel);
    }
}
