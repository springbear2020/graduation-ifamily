/**
 * 成功
 */
const SUCCESS = 200
/// ====================================================================================================================
/**
 * 内部异常
 */
const SERVER_INTERNAL_ERROR = 500
/**
 * 服务不可用
 */
const SERVICE_UNAVAILABLE = 503
/// ====================================================================================================================
/**
 * 身份认证
 */
const UNAUTHORIZED = 401
/**
 * 拒绝访问
 */
const FORBIDDEN = 403
/// ====================================================================================================================
/**
 * 失败
 */
const NOT_ACCEPTABLE = 406
/**
 * 错误条件
 */
const PRECONDITION_FAILED = 412
/**
 * 缺少参数
 */
const BAD_REQUEST = 400
/**
 * 非法方法
 */
const METHOD_NOT_ALLOWED = 405

export default {
    SUCCESS, NOT_ACCEPTABLE,
    UNAUTHORIZED, FORBIDDEN, PRECONDITION_FAILED, SERVICE_UNAVAILABLE,
    BAD_REQUEST, METHOD_NOT_ALLOWED, SERVER_INTERNAL_ERROR
}
