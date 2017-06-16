package jp.co.tutorial.workflow.entity.item;

import jp.co.tutorial.workflow.entity.item.SampleImwItemNames._SampleImwItemNames;

/**
 * 名前クラスの集約です.
 * @author intra-mart
 */
public class Names {

    /**
     * {@link SampleImwItem}の名前クラスを返します.
     *
     * @return SampleImwItemの名前クラス
     */
    public static _SampleImwItemNames sampleImwItem() {
        return new _SampleImwItemNames();
    }
}
