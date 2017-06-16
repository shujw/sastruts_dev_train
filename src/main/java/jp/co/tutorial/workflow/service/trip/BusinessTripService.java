package jp.co.tutorial.workflow.service.trip;

import static jp.co.tutorial.workflow.entity.trip.BusinessTripNames.endFlag;
import static jp.co.tutorial.workflow.entity.trip.BusinessTripNames.userDataId;
import static org.seasar.extension.jdbc.operation.Operations.eq;

import java.util.Map;

import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;
import jp.co.tutorial.workflow.entity.trip.BusinessTrip;

import org.seasar.extension.jdbc.exception.SNonUniqueResultException;

/**
 * {@link BusinessTrip}のサービスクラスです.
 *
 * @author intra-mart
 */
public class BusinessTripService extends AbstractService<BusinessTrip> {

    /**
     * ユーザデータIDを条件に旅費情報を検索します.
     *
     * @param userDataId
     *            ユーザデータID
     * @return 旅費情報エンティティ
     */
    public BusinessTrip findByUserDataId(final String userDataId) {

        BusinessTrip businessTrip;
        try {
            businessTrip = select().where(eq(userDataId(), userDataId))
                    .getSingleResult();
        } catch (final SNonUniqueResultException e) {
            throw new ApplicationRuntimeException(e);
        }

        return businessTrip;
    }

    /**
     * 旅費情報更新.
     *
     * @param param
     */
    public int updateTripData(final Map<String, Object> param) {
        return jdbcManager.updateBySqlFile(SqlFiles.UPDATE_TRIP_DATA, param)
                .execute();
    }

    /**
     * 終了フラグを更新します.
     *
     * @param businessTrip
     *            旅費情報
     * @return 更新件数
     */
    public int updateFlg(final BusinessTrip businessTrip) {

        return jdbcManager.update(businessTrip).includes(endFlag()).execute();
    }

}
