package com.fngry.monk.common.jmx;

import java.beans.PropertyDescriptor;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.management.Descriptor;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.RequiredModelMBean;

import com.fngry.monk.common.sdk.IPluginManager;
import com.fngry.monk.common.util.CollectionUtil;
import com.sun.jdmk.comm.HtmlAdaptorServer;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.jmx.export.assembler.AbstractReflectiveMBeanInfoAssembler;
import org.springframework.stereotype.Component;

/**
 * MBean信息组装器
 * Created by gaorongyu on 2017/11/6.
 */
@Component("monkMBeanInfoAssembler")
public class MonkMBeanInfoAssembler extends AbstractReflectiveMBeanInfoAssembler implements IPluginManager {

    private static final String ENHANCER_BY_CGLIB = "EnhancerByCGLIB";

    private static final String ENHANCER_BY_SPRING_CGLIB = "EnhancerBySpringCGLIB";

    private static final String JDK_DYNAMIC_AOP_PROXY = "org.springframework.aop.framework.JdkDynamicAopProxy";

    private static final String ADVISED = "advised";

    private MBeanServer server = null;

    @Override
    protected boolean includeReadAttribute(Method method, String s) {
        return true;
    }

    @Override
    protected boolean includeWriteAttribute(Method method, String s) {
        return true;
    }

    @Override
    protected boolean includeOperation(Method method, String s) {
        return true;
    }

    @Override
    public ModelMBeanOperationInfo[] getOperationInfo(Object managedBean, String beanKey) {
        Method[] methods = this.getClassToExpose(managedBean).getMethods();
        List<ModelMBeanOperationInfo> infos = new ArrayList<>();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(JmxOperation.class)) {
                continue;
            }
            // from super.getOperationInfo()
            if (!method.isSynthetic() && Object.class != method.getDeclaringClass()) {
                ModelMBeanOperationInfo info = null;
                PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method);
                Descriptor desc;
                if (pd != null && (method.equals(pd.getReadMethod()) && this.includeReadAttribute(method, beanKey)
                        || method.equals(pd.getWriteMethod()) && this.includeWriteAttribute(method, beanKey))) {
                    info = this.createModelMBeanOperationInfo(method, pd.getName(), beanKey);
                    desc = info.getDescriptor();
                    if (method.equals(pd.getReadMethod())) {
                        desc.setField("role", "getter");
                    } else {
                        desc.setField("role", "setter");
                    }

                    desc.setField("visibility", Integer.valueOf(4));
                    if (this.isExposeClassDescriptor()) {
                        desc.setField("class", this.getClassForDescriptor(managedBean).getName());
                    }

                    info.setDescriptor(desc);
                }

                if (info == null && this.includeOperation(method, beanKey)) {
                    info = this.createModelMBeanOperationInfo(method, method.getName(), beanKey);
                    desc = info.getDescriptor();
                    desc.setField("role", "operation");
                    if (this.isExposeClassDescriptor()) {
                        desc.setField("class", this.getClassForDescriptor(managedBean).getName());
                    }

                    this.populateOperationDescriptor(desc, method, beanKey);
                    info.setDescriptor(desc);
                }

                if (info != null) {
                    infos.add(info);
                }
            }
        }
        return infos.toArray(new ModelMBeanOperationInfo[infos.size()]);
    }

    @PostConstruct
    public void init() {
        server = getMBeanServer();
    }

    @Override
    public void initPlugin(String beanName, Object bean) throws Exception {
        Class clazz = bean.getClass();
        Class targetClazz = null;

        if (clazz.getName().contains(ENHANCER_BY_CGLIB)
                || clazz.getName().contains(ENHANCER_BY_SPRING_CGLIB)) {
            // cglib增强的类
            targetClazz = clazz.getSuperclass();
        } else if (Proxy.isProxyClass(clazz)) {
            // 被代理过的类
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(bean);
            Class proxyClass = invocationHandler.getClass();

            if (JDK_DYNAMIC_AOP_PROXY.equals(proxyClass.getName())) {
                Field field = proxyClass.getField(ADVISED);
                field.setAccessible(true);
                AdvisedSupport advisedSupport = (AdvisedSupport) field.get(invocationHandler);
                Object targetObject = advisedSupport.getTargetSource().getTarget();

                targetClazz = targetObject.getClass();
            }
        } else {
            targetClazz = bean.getClass();
        }

        if (targetClazz != null && targetClazz.isAnnotationPresent(JmxResource.class)) {
            injectMBean(bean, beanName);
        }
    }

    @Override
    public void postInitPlugin() throws Exception {
        ObjectName adapterName = new ObjectName("monkAgent:name=htmladapter,port=8082");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        server.registerMBean(adapter, adapterName);
        adapter.start();
    }

    /**
     * 注册MBean
     * @param bean
     * @param beanName
     * @throws JMException
     * @throws InvalidTargetObjectTypeException
     */
    private void injectMBean(Object bean, String beanName) throws JMException, InvalidTargetObjectTypeException {
        ModelMBean modelMBean = new RequiredModelMBean();
        modelMBean.setModelMBeanInfo(getMBeanInfo(bean, beanName));
        modelMBean.setManagedResource(bean, "ObjectReference");

        ObjectName objectName = new ObjectName("monkJmxBean:name=$" + beanName + "$");
        server.registerMBean(modelMBean, objectName);
    }

    /**
     * 获取MBeanServer
     * @return MBeanServer
     */
    private MBeanServer getMBeanServer() {
        ArrayList<MBeanServer> beanServerList = MBeanServerFactory.findMBeanServer(null);
        if (CollectionUtil.isNotEmpty(beanServerList)) {
            return beanServerList.get(0);
        }
        return ManagementFactory.getPlatformMBeanServer();
    }

}
