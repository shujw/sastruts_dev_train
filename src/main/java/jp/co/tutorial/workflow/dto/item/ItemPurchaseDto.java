package jp.co.tutorial.workflow.dto.item;

import java.util.Date;

/**
 * 物品購入管理テーブルDTOクラス.
 *
 * @author intra-mart
 */
public class ItemPurchaseDto {

    /** ユーザデータID. */
    public String userDataId;

    /** 品名. */
    public String itemName;

    /** 金額. */
    public Long itemPrice;

    /** 納品日. */
    public Date itemDate;

    /** 購入理由. */
    public String itemReason;
}
