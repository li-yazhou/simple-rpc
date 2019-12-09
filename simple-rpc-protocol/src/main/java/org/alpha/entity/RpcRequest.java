package org.alpha.entity;

import lombok.Data;

/**
 * @author liyazhou1
 * @date 2019/12/6
 */
@Data
public class RpcRequest {


    private String requestId;

    private String className;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;


}
