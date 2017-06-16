package jp.co.tutorial.workflow.entity.trip;

import jp.co.tutorial.workflow.entity.trip.BusinessTripNames._BusinessTripNames;

/**
 * 名前クラスの集約です.
 * @author intra-mart
 */
public class Names {

    /**
     * {@link BusinessTrip}の名前クラスを返します.
     *
     * @return BusinessTripの名前クラス
     */
    public static _BusinessTripNames businessTrip() {
        return new _BusinessTripNames();
    }
}
