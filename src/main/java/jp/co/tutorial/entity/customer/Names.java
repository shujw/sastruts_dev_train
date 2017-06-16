package jp.co.tutorial.entity.customer;

import jp.co.tutorial.entity.customer.MSampleCustomerNames._MSampleCustomerNames;


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
    public static _MSampleCustomerNames MSampleCustomer() {
        return new _MSampleCustomerNames();
    }
}
