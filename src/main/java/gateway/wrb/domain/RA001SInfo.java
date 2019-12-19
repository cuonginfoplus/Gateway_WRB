package gateway.wrb.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ra001_s")
@Data
public class RA001SInfo implements Serializable {
    @Id
    @GeneratedValue
    private Long ra001id;

    private String fbkname;
    private String msgDscd;
    private String tmsDt;
    private String tmsTm;
    private String coNo;
    private String outActNo;
    private String dataCnt;
    private String etcAr;
}