package io.github.ningyu.jmeter.plugin.common;

/**
 * @author 林杰炜 linjiewei
 * @Title TODO 类描述
 * @Description TODO 详细描述
 * @Copyright 2014-现在 厦门神州鹰掌通家园项目组
 * @date 2018/7/6 10:26
 */
public class Result {

    public int code;
    public Object body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
