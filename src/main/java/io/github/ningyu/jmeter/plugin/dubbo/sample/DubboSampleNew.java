package io.github.ningyu.jmeter.plugin.dubbo.sample;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import io.github.ningyu.jmeter.plugin.common.Result;
import io.github.ningyu.jmeter.plugin.util.ClassUtils;
import io.github.ningyu.jmeter.plugin.util.Constants;
import io.github.ningyu.jmeter.plugin.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 林杰炜 linjiewei
 * @Title TODO 类描述
 * @Description TODO 详细描述
 * @date 2018/7/24 0:39
 */
public class DubboSampleNew extends AbstractSampler {
    private static final Logger log = LoggingManager.getLoggerForClass();
    private static final long serialVersionUID = -1352781148654395348L;

    public static String FIELD_DUBBO_REGISTRY_PROTOCOL = "FIELD_DUBBO_REGISTRY_PROTOCOL";
    public static String FIELD_DUBBO_RPC_PROTOCOL = "FIELD_DUBBO_RPC_PROTOCOL";
    public static String FIELD_DUBBO_ADDRESS = "FIELD_DUBBO_ADDRESS";
    public static String FIELD_DUBBO_TIMEOUT = "FIELD_DUBBO_TIMEOUT";
    public static String FIELD_DUBBO_VERSION = "FIELD_DUBBO_VERSION";
    public static String FIELD_DUBBO_RETRIES = "FIELD_DUBBO_RETRIES";
    public static String FIELD_DUBBO_CLUSTER = "FIELD_DUBBO_CLUSTER";
    public static String FIELD_DUBBO_GROUP = "FIELD_DUBBO_GROUP";
    public static String FIELD_DUBBO_CONNECTIONS = "FIELD_DUBBO_CONNECTIONS";
    public static String FIELD_DUBBO_LOADBALANCE = "FIELD_DUBBO_LOADBALANCE";
    public static String FIELD_DUBBO_ASYNC = "FIELD_DUBBO_ASYNC";
    public static String FIELD_DUBBO_INTERFACE = "FIELD_DUBBO_INTERFACE";
    public static String FIELD_DUBBO_METHOD = "FIELD_DUBBO_METHOD";
    public static String FIELD_DUBBO_METHOD_ARGS = "FIELD_DUBBO_METHOD_ARGS";
    public static String FIELD_DUBBO_METHOD_ARGS_SIZE = "FIELD_DUBBO_METHOD_ARGS_SIZE";
    public static String DEFAULT_TIMEOUT = "3000";
    public static String DEFAULT_VERSION = "";
    public static String DEFAULT_RETRIES = "0";
    public static String DEFAULT_CLUSTER = "failfast";
    public static String DEFAULT_GROUP = "test";
    public static String DEFAULT_CONNECTIONS = "1";

    public String getRegistryProtocol() {
        return this.getPropertyAsString(FIELD_DUBBO_REGISTRY_PROTOCOL);
    }

    public void setRegistryProtocol(String registryProtocol) {
        this.setProperty(new StringProperty(FIELD_DUBBO_REGISTRY_PROTOCOL, registryProtocol));
    }

    public String getRpcProtocol() {
        return this.getPropertyAsString(FIELD_DUBBO_RPC_PROTOCOL);
    }

    public void setRpcProtocol(String rpcProtocol) {
        this.setProperty(new StringProperty(FIELD_DUBBO_RPC_PROTOCOL, rpcProtocol));
    }

    public String getAddress() {
        return this.getPropertyAsString(FIELD_DUBBO_ADDRESS);
    }

    public void setAddress(String address) {
        this.setProperty(new StringProperty(FIELD_DUBBO_ADDRESS, address));
    }

    public String getTimeout() {
        return this.getPropertyAsString(FIELD_DUBBO_TIMEOUT, DEFAULT_TIMEOUT);
    }

    public void setTimeout(String timeout) {
        this.setProperty(new StringProperty(FIELD_DUBBO_TIMEOUT, timeout));
    }

    public String getVersion() {
        return this.getPropertyAsString(FIELD_DUBBO_VERSION, DEFAULT_VERSION);
    }

    public void setVersion(String version) {
        this.setProperty(new StringProperty(FIELD_DUBBO_VERSION, version));
    }

    public String getRetries() {
        return this.getPropertyAsString(FIELD_DUBBO_RETRIES, DEFAULT_RETRIES);
    }

    public void setRetries(String retries) {
        this.setProperty(new StringProperty(FIELD_DUBBO_RETRIES, retries));
    }

    public String getCluster() {
        return this.getPropertyAsString(FIELD_DUBBO_CLUSTER, DEFAULT_CLUSTER);
    }

    public void setCluster(String cluster) {
        this.setProperty(new StringProperty(FIELD_DUBBO_CLUSTER, cluster));
    }

    public String getGroup() {
        return this.getPropertyAsString(FIELD_DUBBO_GROUP, null);
    }

    public void setGroup(String group) {
        this.setProperty(new StringProperty(FIELD_DUBBO_GROUP, group));
    }

    public String getConnections() {
        return this.getPropertyAsString(FIELD_DUBBO_CONNECTIONS, DEFAULT_CONNECTIONS);
    }

    public void setConnections(String connections) {
        this.setProperty(new StringProperty(FIELD_DUBBO_CONNECTIONS, connections));
    }

    public String getLoadbalance() {
        return this.getPropertyAsString(FIELD_DUBBO_LOADBALANCE);
    }

    public void setLoadbalance(String loadbalance) {
        this.setProperty(new StringProperty(FIELD_DUBBO_LOADBALANCE, loadbalance));
    }

    public String getAsync() {
        return this.getPropertyAsString(FIELD_DUBBO_ASYNC);
    }

    public void setAsync(String async) {
        this.setProperty(new StringProperty(FIELD_DUBBO_ASYNC, async));
    }

    public String getInterface() {
        return this.getPropertyAsString(FIELD_DUBBO_INTERFACE);
    }

    public void setInterfaceName(String interfaceName) {
        this.setProperty(new StringProperty(FIELD_DUBBO_INTERFACE, interfaceName));
    }

    public String getMethod() {
        return this.getPropertyAsString(FIELD_DUBBO_METHOD);
    }

    public void setMethod(String method) {
        this.setProperty(new StringProperty(FIELD_DUBBO_METHOD, method));
    }

    public String getArgs() {
        return this.getPropertyAsString(FIELD_DUBBO_METHOD_ARGS);
    }

    public void setArgs(String args) {
        this.setProperty(new StringProperty(FIELD_DUBBO_METHOD_ARGS, args));
    }

    public List<MethodArgument> getMethodArgs() {
        String methodArgs = getArgs();
        int size = 0;
        JSONArray jsonArray = null;
        List<MethodArgument> result = Lists.newArrayList();
        if (StringUtils.isNotBlank(methodArgs)) {
            try {
                jsonArray = JSON.parseArray(methodArgs);
            } catch (Exception e) {
                jsonArray = JSON.parseArray("[]");
            }
            if (jsonArray.size() > 0) {
                size = jsonArray.size();
            }
        }
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.keySet().size() == 1) {
                for (String key : jsonObject.keySet()) {
                    String paramType = key;
                    String paramValue = (String) jsonObject.get(key);
                    MethodArgument argument = new MethodArgument(paramType, paramValue);
                    result.add(argument);
                }
            }
        }
        return result;
    }

    @Override
    public SampleResult sample(Entry entry) {
        SampleResult result = new SampleResult();
        result.setSampleLabel(getName());
        result.sampleStart();
        result.setSamplerData(getSampleData());
        result.setResponseData(JsonUtils.getJson(callDubbo(result)).getBytes(Charset.forName("UTF-8")));
        result.setDataType(SampleResult.TEXT);
        result.setResponseCodeOK();
        result.setResponseMessageOK();
        result.sampleEnd();
        return result;
    }

    private String getSampleData() {
        StringBuilder sb = new StringBuilder();
        sb.append("Registry Protocol: ").append(getRegistryProtocol()).append("\n");
        sb.append("Address: ").append(getAddress()).append("\n");
        sb.append("RPC Protocol: ").append(getRpcProtocol()).append("\n");
        sb.append("Timeout: ").append(getTimeout()).append("\n");
        sb.append("Version: ").append(getVersion()).append("\n");
        sb.append("Retries: ").append(getRetries()).append("\n");
        sb.append("Cluster: ").append(getCluster()).append("\n");
        sb.append("Group: ").append(getGroup()).append("\n");
        sb.append("Connections: ").append(getConnections()).append("\n");
        sb.append("Loadbalance: ").append(getLoadbalance()).append("\n");
        sb.append("Async: ").append(getAsync()).append("\n");
        sb.append("Interface: ").append(getInterface()).append("\n");
        sb.append("Method: ").append(getMethod()).append("\n");
        sb.append("Method Args: ").append(getMethodArgs().toString());
        return sb.toString();
    }

    private Object callDubbo(SampleResult res) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("DubboSample");

        // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        ReferenceConfig reference = new ReferenceConfig();
        // 引用远程服务
        reference.setApplication(application);
        RegistryConfig registry = null;

        String protocol = getRegistryProtocol();
        switch (protocol) {
            case Constants.REGISTRY_ZOOKEEPER:
                registry = new RegistryConfig();
                registry.setProtocol(Constants.REGISTRY_ZOOKEEPER);
                registry.setAddress(getAddress());
                reference.setRegistry(registry);
                reference.setProtocol(getRpcProtocol().replaceAll("://", ""));
                break;
            case Constants.REGISTRY_MULTICAST:
                registry = new RegistryConfig();
                registry.setProtocol(Constants.REGISTRY_MULTICAST);
                registry.setAddress(getAddress());
                reference.setRegistry(registry);
                reference.setProtocol(getRpcProtocol().replaceAll("://", ""));
                break;
            case Constants.REGISTRY_REDIS:
                registry = new RegistryConfig();
                registry.setProtocol(Constants.REGISTRY_REDIS);
                registry.setAddress(getAddress());
                reference.setRegistry(registry);
                reference.setProtocol(getRpcProtocol().replaceAll("://", ""));
                break;
            case Constants.REGISTRY_SIMPLE:
                registry = new RegistryConfig();
                registry.setAddress(getAddress());
                reference.setRegistry(registry);
                reference.setProtocol(getRpcProtocol().replaceAll("://", ""));
                break;
            default:
                // 直连方式
                StringBuffer sb = new StringBuffer();
                sb.append(getRpcProtocol()).append(getAddress()).append("/").append(getInterface());
                log.debug("rpc invoker url : " + sb.toString());
                reference.setUrl(sb.toString());
        }
        try {
            reference.setInterface(getInterface());
            reference.setRetries(Integer.valueOf(getRetries()));
            reference.setCluster(getCluster());
            reference.setVersion(getVersion());
            reference.setTimeout(Integer.valueOf(getTimeout()));
            String group = getGroup();
            reference.setGroup(com.alibaba.dubbo.common.utils.StringUtils.isBlank(group) ? null : group);
            reference.setConnections(Integer.valueOf(getConnections()));
            reference.setLoadbalance(getLoadbalance());
            reference.setAsync(Constants.ASYNC.equals(getAsync()) ? true : false);
            reference.setGeneric(true);
            //TODO 不同的注册中心地址使用不同的cache对象
            ReferenceConfigCache cache = ReferenceConfigCache.getCache(getAddress(), new ReferenceConfigCache.KeyGenerator() {
                @Override
                public String generateKey(ReferenceConfig<?> referenceConfig) {
                    return referenceConfig.toString();
                }
            });
            GenericService genericService = (GenericService) cache.get(reference);
            String[] parameterTypes = null;
            Object[] parameterValues = null;
            List<MethodArgument> args = getMethodArgs();
            List<String> paramterTypeList =  new ArrayList<String>();
            List<Object> parameterValuesList = new ArrayList<Object>();
            for(MethodArgument arg : args) {
                ClassUtils.parseParameter(paramterTypeList, parameterValuesList, arg);
            }
            //发起调用
            parameterTypes = paramterTypeList.toArray(new String[paramterTypeList.size()]);
            parameterValues = parameterValuesList.toArray(new Object[parameterValuesList.size()]);
            Object result = null;
            Result result1 = new Result();
            try {
                result = genericService.$invoke(getMethod(), parameterTypes, parameterValues);
                log.info("result:" + result);
                res.setSuccessful(true);
                result1.setCode(10000);
                result1.setBody(result);
            } catch (Exception e) {
                log.error("接口返回异常：", e);
                //TODO
                //当接口返回异常时，sample标识为successful，通过响应内容做断言来判断是否标识sample错误，因为sample的错误会统计到用例的error百分比内。
                //比如接口有一些校验性质的异常，不代表这个操作是错误的，这样就可以灵活的判断，不至于正常的校验返回导致测试用例error百分比的不真实
                res.setSuccessful(true);
                result = e;
                result1.setCode(-1);
                result1.setBody(result);
            }
            return result1;
        } catch (Exception e) {
            log.error("未知异常：", e);
            res.setSuccessful(false);
            return e;
        } finally {
            //TODO 不能在sample结束时destroy
//            if (registry != null) {
//                registry.destroyAll();
//            }
//            reference.destroy();
        }
    }
}
