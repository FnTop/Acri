package cn.fntop.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author fn
 * @description Acri容器，用于包含切面方法参数、方法返回值、方法异常
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
}
