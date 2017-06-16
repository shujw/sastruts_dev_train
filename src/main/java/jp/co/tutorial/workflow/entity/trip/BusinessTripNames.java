package jp.co.tutorial.workflow.entity.trip;

import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link BusinessTrip}のプロパティ名の集合です.
 *
 * @author intra-mart
 */
public class BusinessTripNames {

    /**
     * tripIdのプロパティ名を返します.
     *
     * @return tripIdのプロパティ名
     */
    public static PropertyName<String> tripId() {
        return new PropertyName<String>("tripId");
    }

    /**
     * startDateのプロパティ名を返します.
     *
     * @return startDateのプロパティ名
     */
    public static PropertyName<String> startDate() {
        return new PropertyName<String>("startDate");
    }

    /**
     * arrivalDateのプロパティ名を返します.
     *
     * @return arrivalDateのプロパティ名
     */
    public static PropertyName<String> arrivalDate() {
        return new PropertyName<String>("arrivalDate");
    }

    /**
     * destinationのプロパティ名を返します.
     *
     * @return destinationのプロパティ名
     */
    public static PropertyName<String> destination() {
        return new PropertyName<String>("destination");
    }

    /**
     * reasonのプロパティ名を返します.
     *
     * @return reasonのプロパティ名
     */
    public static PropertyName<String> reason() {
        return new PropertyName<String>("reason");
    }

    /**
     * tExpensesのプロパティ名を返します.
     *
     * @return tExpensesのプロパティ名
     */
    public static PropertyName<String> tExpenses() {
        return new PropertyName<String>("tExpenses");
    }

    /**
     * lExpensesのプロパティ名を返します.
     *
     * @return lExpensesのプロパティ名
     */
    public static PropertyName<String> lExpenses() {
        return new PropertyName<String>("lExpenses");
    }

    /**
     * userDataIdのプロパティ名を返します.
     *
     * @return userDataIdのプロパティ名
     */
    public static PropertyName<String> userDataId() {
        return new PropertyName<String>("userDataId");
    }

    /**
     * endFlagのプロパティ名を返します.
     *
     * @return endFlagのプロパティ名
     */
    public static PropertyName<String> endFlag() {
        return new PropertyName<String>("endFlag");
    }

    /**
     * @author intra-mart
     */
    public static class _BusinessTripNames extends PropertyName<BusinessTrip> {

        /**
         * インスタンスを構築します.
         */
        public _BusinessTripNames() {
        }

        /**
         * インスタンスを構築します.
         *
         * @param name
         *            名前
         */
        public _BusinessTripNames(final String name) {
            super(name);
        }

        /**
         * インスタンスを構築します.
         *
         * @param parent
         *            親
         * @param name
         *            名前
         */
        public _BusinessTripNames(final PropertyName<?> parent,
                final String name) {
            super(parent, name);
        }

        /**
         * tripIdのプロパティ名を返します.
         *
         * @return tripIdのプロパティ名
         */
        public PropertyName<String> tripId() {
            return new PropertyName<String>(this, "tripId");
        }

        /**
         * startDateのプロパティ名を返します.
         *
         * @return startDateのプロパティ名
         */
        public PropertyName<String> startDate() {
            return new PropertyName<String>(this, "startDate");
        }

        /**
         * arrivalDateのプロパティ名を返します.
         *
         * @return arrivalDateのプロパティ名
         */
        public PropertyName<String> arrivalDate() {
            return new PropertyName<String>(this, "arrivalDate");
        }

        /**
         * destinationのプロパティ名を返します.
         *
         * @return destinationのプロパティ名
         */
        public PropertyName<String> destination() {
            return new PropertyName<String>(this, "destination");
        }

        /**
         * reasonのプロパティ名を返します.
         *
         * @return reasonのプロパティ名
         */
        public PropertyName<String> reason() {
            return new PropertyName<String>(this, "reason");
        }

        /**
         * tExpensesのプロパティ名を返します.
         *
         * @return tExpensesのプロパティ名
         */
        public PropertyName<String> tExpenses() {
            return new PropertyName<String>(this, "tExpenses");
        }

        /**
         * lExpensesのプロパティ名を返します.
         *
         * @return lExpensesのプロパティ名
         */
        public PropertyName<String> lExpenses() {
            return new PropertyName<String>(this, "lExpenses");
        }

        /**
         * userDataIdのプロパティ名を返します.
         *
         * @return userDataIdのプロパティ名
         */
        public PropertyName<String> userDataId() {
            return new PropertyName<String>(this, "userDataId");
        }

        /**
         * endFlagのプロパティ名を返します.
         *
         * @return endFlagのプロパティ名
         */
        public PropertyName<String> endFlag() {
            return new PropertyName<String>(this, "endFlag");
        }
    }
}
