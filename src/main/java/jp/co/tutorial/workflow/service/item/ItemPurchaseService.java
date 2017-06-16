package jp.co.tutorial.workflow.service.item;

import static jp.co.tutorial.workflow.entity.trip.BusinessTripNames.userDataId;
import static org.seasar.extension.jdbc.operation.Operations.eq;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;
import jp.co.tutorial.workflow.entity.item.SampleImwItem;

import org.seasar.extension.jdbc.exception.SNonUniqueResultException;

/**
 * {@link SampleImwItem}のサービスクラスです.
 *
 * @author intra-mart
 */
public class ItemPurchaseService extends AbstractService<SampleImwItem> {

    /**
     * ユーザデータIDを条件に物品購入情報を検索します.
     *
     * @param userDataId
     *            ユーザデータID
     * @return 物品購入情報エンティティ
     */
    public SampleImwItem findByUserDataId(final String userDataId) {

        SampleImwItem sampleImwItem;
        try {
            sampleImwItem = select().where(eq(userDataId(), userDataId))
                    .getSingleResult();
        } catch (final SNonUniqueResultException e) {
            throw new ApplicationRuntimeException(e);
        }

        return sampleImwItem;
    }
}
