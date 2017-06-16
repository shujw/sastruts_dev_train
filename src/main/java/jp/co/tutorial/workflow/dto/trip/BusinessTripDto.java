package jp.co.tutorial.workflow.dto.trip;

import java.util.Date;

/**
 * 旅費情報管理テーブルDTOクラス.
 *
 * @author intra-mart
 */
public class BusinessTripDto {

    /** 旅費ID. */
    public String tripId;

    /** 出発日. */
    public Date startDate;

    /** 到着日. */
    public Date arrivalDate;

    /** 出張先. */
    public String destination;

    /** 出張理由. */
    public String reason;

    /** 交通費. */
    public Long tExpenses;

    /** 宿泊費. */
    public Long lExpenses;

    /** ユーザデータID. */
    public String userDataId;

    /** 終了フラグ. */
    public String endFlag;
}
