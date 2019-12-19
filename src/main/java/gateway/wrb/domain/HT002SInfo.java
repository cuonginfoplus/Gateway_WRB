package gateway.wrb.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ht002_s")
@Data
public class HT002SInfo implements Serializable {
    @Id
    @GeneratedValue
    private Long ht002id;

    private String fbkname;
    private String msgDscd;
    private String tmsDt;
    private String tmsTm;
    private String coNo;
    private String mgscd;
}