package gateway.wrb.repositories;


import gateway.wrb.domain.HT002Info;
import gateway.wrb.model.HT002DTO;

import java.util.List;

public interface HT002Repo {
    List<HT002Info> getAllHT002();

    void addHT002(HT002Info ht002Info);

    void updateHT002(HT002Info ht002Info);

    void deleteHT002(long id);

    boolean isHT002Exist(String msgDscd, String actNo, String trnDt, String trnTm, String drCr, String trnAmt, String trnAfBl,
                         String brCd, String chkAmt, String trnType, String particular, String depSeq, String status, String channelType,
                         String trnSrno, String destAccount, String recieveName, String refTxt, String depRmk, String trmPrcSrno);

    List<HT002DTO> filterHT002(String orgCd, String bankCd, String bankCoNo, String outActNo, String InqSdt, String InqEdt);
}
