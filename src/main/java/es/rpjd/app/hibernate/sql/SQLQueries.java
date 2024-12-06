package es.rpjd.app.hibernate.sql;

public class SQLQueries {

	public static final String SELECT_ORDERS_QUANTITY_TODAY = "SELECT COUNT(*) FROM CUSTOMER_ORDER WHERE CREATED_AT BETWEEN CURDATE() AND CURDATE() + INTERVAL 1 DAY - INTERVAL 1 SECOND";
}
