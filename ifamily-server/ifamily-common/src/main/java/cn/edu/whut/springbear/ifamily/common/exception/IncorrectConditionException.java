package cn.edu.whut.springbear.ifamily.common.exception;

/**
 * @author Spring-_-Bear
 * @since 23/03/22 09:06
 */
public class IncorrectConditionException extends RuntimeException {

    private static final long serialVersionUID = 6755251699807046797L;

    public IncorrectConditionException(String message) {
        super(message);
    }

}
