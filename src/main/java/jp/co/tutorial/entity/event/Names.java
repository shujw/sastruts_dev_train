package jp.co.tutorial.entity.event;

import jp.co.tutorial.entity.customer.MSampleCustomer;
import jp.co.tutorial.entity.event.MSampleEventNames._MSampleEventNames;


/**
 * 名前クラスの集約です.
 *
 * @author intra-mart
 */
public class Names {

    /**
     * {@link MSampleCustomer}の名前クラスを返します.
     *
     * @return MSampleCustomerの名前クラス
     */
    public static _MSampleEventNames MSampleEvent() {
        return new _MSampleEventNames();
    }
}
