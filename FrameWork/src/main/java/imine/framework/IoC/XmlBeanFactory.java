package imine.framework.IoC;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wym
 */
public class XmlBeanFactory {

    private Map<String, Object> beanMap = new HashMap<String, Object>();

    private String xmlPath;

    public XmlBeanFactory(String xmlPath) throws Exception {
        this.xmlPath = xmlPath;
        loadBeans();
    }

    public Object getBean(String name) {
        Object bean = beanMap.get(name);
        if (null == bean) {
            throw new IllegalArgumentException("There is no bean with name:" + name);
        }
        return bean;
    }

    public void registerBean(String name, Object bean){
        beanMap.put(name,bean);
    }

    public void loadBeans() throws Exception {
        if (xmlPath == null){

        }

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(this.getClass().getClassLoader().getResource(xmlPath));
        Element rootElement = document.getRootElement();
        List elements = rootElement.elements();

        for (Object obj:elements) {
            if (!(obj instanceof Element)){
                continue;
            }
            Element ele = (Element)obj;
            String id= ele.attributeValue("id").trim();

            if (StringUtils.isEmpty(id)) {
                continue;
            }

            String className = ele.attributeValue("class");
            Class<?> beanClass = null;
            try {
                beanClass = Class.forName(className);
            } catch (Exception e) {
                System.out.println("Nonexistent class : " + className);
                continue;
            }
            Object instance = beanClass.newInstance();

            List elementList = ele.elements();
            for (Object obj1 : elementList) {
                if (!(obj1 instanceof Element)){
                    continue;
                }
                Element element = (Element)obj1;
                String name = element.attributeValue("name");
                String value = element.attributeValue("value");
                Field declaredField = beanClass.getDeclaredField(name);
                declaredField.setAccessible(true);

                if (value != null && value.length() > 0){
                    declaredField.set(instance,value );
                }else{
                    String ref = element.attributeValue("ref");
                    if (null == ref || 0 == ref.length()) {
                        throw new IllegalArgumentException("ref config error");
                    }
                    declaredField.set(instance, getBean(ref));
                }

            }
            registerBean(id, instance);

        }
    }


}
