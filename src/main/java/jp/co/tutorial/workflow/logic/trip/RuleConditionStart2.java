package jp.co.tutorial.workflow.logic.trip;

import jp.co.intra_mart.common.platform.log.Logger;
import jp.co.intra_mart.foundation.workflow.plugin.rule.condition.RuleConditionEventListener;
import jp.co.intra_mart.foundation.workflow.plugin.rule.condition.RuleConditionParameter;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * 分岐条件／分岐結合処理プログラム テンプレート.
 *
 * @author intra-mart
 *
 *         このプログラム中ではDBトランザクションを張らないで下さい。<br>
 */
public class RuleConditionStart2 extends RuleConditionEventListener {

    /** ロジックコンポーネントの定義. */
    private final BusinessTripLogic businessTripLogic;

    /** コンストラクタ. */
    public RuleConditionStart2() {
        // 自動バインディングは適用されないので、明示的に
        // ロジックコンポーネントを取得する
        this.businessTripLogic = (BusinessTripLogic) SingletonS2ContainerFactory
                .getContainer().getComponent(BusinessTripLogic.class);
    }

    /** LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RuleConditionStart2.class);

    /**
     * 分岐開始処理クラスの実行メソッド.
     * <p>
     *
     * 分岐開始処理において、処理結果に成功（true）を返却することにより、その分岐先ノードに進みます.<br />
     * 全ての分岐開始処理の結果が失敗（false）の場合は、分岐開始ノードで案件は停止します.<br />
     * このような場合は、案件操作処理で案件を進める必要があります.<br />
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @return 処理結果(true: 成功/false: 失敗)
     * @see RuleConditionParameter
     */
    public boolean execute(final RuleConditionParameter parameter) {
        LOGGER.info("----- RuleConditionStart2 - execute -----");

        final int total = 50000; // 合計金額
        boolean result = false;

        if (businessTripLogic.getMatterProperty(parameter.getUserDataId()) < total) {
            result = true;
        }
        return result;
    }
}
