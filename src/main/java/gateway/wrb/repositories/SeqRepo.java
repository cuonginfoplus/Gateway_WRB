package gateway.wrb.repositories;

import gateway.wrb.model.SeqModel;

public interface SeqRepo {
    public SeqModel getItem(String key);

    public void addItem(SeqModel item);

    public void deleteItem(String key);

    public void updateItem(SeqModel item);
}
