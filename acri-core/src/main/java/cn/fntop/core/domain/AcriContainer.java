package cn.fntop.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author fn
 * @description
 * @date 2023/11/29 19:36
 */
@Setter
@Getter
public class AcriContainer extends Exception{
    /**
     * 方法参数
     */
    private List<Object> params;
    /**
     * 方法异常
     */
    private Exception exception;

    /**
     * 方法返回值
     */
    private Object result;

    public AcriContainer() {
        super();
    }

    public AcriContainer setParams(List<Object> params) {
        this.params = params;
        return this;
    }

    public AcriContainer setException(Exception exception) {
        this.exception = exception;
        return this;
    }

    public AcriContainer setResult(Object result) {
        this.result = result;
        return this;
    }
}
