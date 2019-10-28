package gateway.wrb.cache;

import gateway.wrb.model.SeqModel;
import gateway.wrb.repositories.SeqRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SeqCache {
    @Autowired
    SeqRepo seqRepo;

    @Cacheable(value = "seqCache", key = "#id")
    public SeqModel getItem(String id) {
        SeqModel seqModel = null;
        try {
            seqModel = seqRepo.getItem(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seqModel;
    }

    @CacheEvict(value = "seqCache", key = "#id")
    public void deleteItem(String key) {
        seqRepo.deleteItem(key);
    }

    @CachePut(value = "seqCache", key = "#id", condition = "#result != null")
    public void addItem(SeqModel seqModel) {
        seqRepo.addItem(seqModel);
    }

    @CachePut(value = "seqCache", key = "#id", condition = "#result != null")
    public void updateItem(SeqModel seqModel) {
        seqRepo.updateItem(seqModel);
    }
}
