package cn.edu.whut.springbear.ifamily.common.exception;

/**
 * @author Spring-_-Bear
 * @since 23/03/22 09:07
 */
public class IllegalStatusException extends RuntimeException {

    private static final long serialVersionUID = 6384430874863298895L;

    public IllegalStatusException(String message) {
        super(message);
    }

}
