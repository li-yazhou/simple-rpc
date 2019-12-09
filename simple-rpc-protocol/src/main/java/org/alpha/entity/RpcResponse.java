package org.alpha.entity;

import lombok.Data;

/**
 * @author liyazhou1
 * @date 2019/12/6
 */
@Data
public class RpcResponse {

    private String requestId;

    private Throwable throwable;

    private Object result;

}
