package gateway.wrb.model;

import lombok.Data;

import java.util.List;

@Data
public class RA001Model {
    private String orgCd;
    private String bankCd;
    private String bankCoNo;
    private String outActNo;
    private List<RA001AccModel> actList;
}
