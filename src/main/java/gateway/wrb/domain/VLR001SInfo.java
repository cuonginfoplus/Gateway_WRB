package gateway.wrb.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "vlr001_s")
@Data
public class VLR001SInfo implements Serializable {
    @Id
    @GeneratedValue
    private Long rv001id;

    private String fbkname;
    private String msgDscd;
    private String tmsDt;
    private String tmsTm;
    private String coNo;
    private String outActNo;
    private String dataCnt;
    private String etcAr;
}