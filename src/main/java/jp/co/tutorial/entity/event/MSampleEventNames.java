package jp.co.tutorial.entity.event;

import jp.co.tutorial.entity.customer.MSampleCustomer;

import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link MSampleCustomer}のプロパティ名の集合です.
 *
 * @author intra-mart
 */
public class MSampleEventNames {

    /**
     * eventIdのプロパティ名を返します.
     *
     * @return eventIdのプロパティ名
     */
    public static PropertyName<String> eventId() {
        return new PropertyName<String>("eventId");
    }

    /**
     * eventNameのプロパティ名を返します.
     *
     * @return eventNameのプロパティ名
     */
    public static PropertyName<String> eventName() {
        return new PropertyName<String>("eventName");
    }

    /**
     * eventDetailのプロパティ名を返します.
     *
     * @return eventDetailのプロパティ名
     */
    public static PropertyName<String> eventDetail() {
        return new PropertyName<String>("eventDetail");
    }

    /**
     * eventDateのプロパティ名を返します.
     *
     * @return eventDateのプロパティ名
     */
    public static PropertyName<String> eventDate() {
        return new PropertyName<String>("eventDate");
    }

    /**
     * entryUserのプロパティ名を返します.
     *
     * @return entryUserのプロパティ名
     */
    public static PropertyName<String> entryUser() {
        return new PropertyName<String>("entryUser");
    }

   /**
     * @author intra-mart
     */
    public static class _MSampleEventNames extends PropertyName<MSampleEvent> {

        /**
         * インスタンスを構築します.
         */
        public _MSampleEventNames() {
        }

        /**
         * インスタンスを構築します.
         *
         * @param name
         *            名前
         */
        public _MSampleEventNames(final String name) {
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
        public _MSampleEventNames(final PropertyName<?> parent, final String name) {
            super(parent, name);
        }

        /**
         * eventIdのプロパティ名を返します.
         *
         * @return eventIdのプロパティ名
         */
        public PropertyName<String> eventId() {
            return new PropertyName<String>(this, "eventId");
        }

        /**
         * eventNameのプロパティ名を返します.
         *
         * @return eventNameのプロパティ名
         */
        public PropertyName<String> eventName() {
            return new PropertyName<String>(this, "eventName");
        }

        /**
         * eventDetailのプロパティ名を返します.
         *
         * @return eventDetailのプロパティ名
         */
        public PropertyName<String> eventDetail() {
            return new PropertyName<String>(this, "eventDetail");
        }

        /**
         * eventDateのプロパティ名を返します.
         *
         * @return eventDateのプロパティ名
         */
        public PropertyName<String> eventDate() {
            return new PropertyName<String>(this, "eventDate");
        }

        /**
         * entryUserのプロパティ名を返します.
         *
         * @return entryUserのプロパティ名
         */
        public PropertyName<String> entryUser() {
            return new PropertyName<String>(this, "entryUser");
        }
    }
}
