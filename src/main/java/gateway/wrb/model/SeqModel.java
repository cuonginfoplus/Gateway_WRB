package gateway.wrb.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeqModel implements Serializable {
    private String id;
    private int seqValue;

    public SeqModel() {
    }

    public SeqModel(String id, int seqValue) {
        this.id = id;
        this.seqValue = seqValue;
    }
}
