package cn.edu.whut.springbear.ifamily.common.exception;

/**
 * @author Spring-_-Bear
 * @since 23/03/20 15:30
 */
public class SqlExecutionException extends RuntimeException {

    private static final long serialVersionUID = 1590196502905137315L;

    public SqlExecutionException(String message) {
        super(message);
    }

}
