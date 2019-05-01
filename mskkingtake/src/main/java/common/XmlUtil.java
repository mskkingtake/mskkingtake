package common;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * XML工具类
 * @author mskkingtake
 *
 */
public class XmlUtil {
	
	/**
	 * <p> 创建根节点元素  <p> 
	 * @param rootName 根节点名称
	 * @return 节点对象
	 */
	public static Element createRootElement(String rootName) {
		Element rootElement = null; // 返回的跟几点对象
		Document document = DocumentHelper.createDocument(); // 使用 DocumentHelper 类创建一个文档实例
		rootElement = document.addElement(rootName); //创建根元素
		return rootElement;
	}
	
	/**
	 * <p> 在节点下添加新节点并赋值  <p>
	 * @param elt 父节点
	 * @param eltName 节点名称
	 * @param eltValue 节点值
	 * @return 变化后的节点
	 */
	public static Element createElement(Element elt, String eltName, String eltValue) {
		Element resultElt = null;
		if(null != elt) {
			resultElt = elt.addElement(eltName).addText(eltValue); // 添加新节点并赋值
		}
		return resultElt;
	}
	
	/**
	 * <p> 在节点下添加一个空的新节点 <p>
	 * @param elt 父节点
	 * @param eltName 节点名称
	 * @return 变化后的节点
	 */
	public static Element createEmptyElement(Element elt, String eltName) {
		Element resultElt = null;
		if(null != elt) {
			resultElt = elt.addElement(eltName); // 添加新节点
		}
		return resultElt;
	}
	
	/**
	 * 通过xml字符串得到xml的根节点对象
	 * @param xml字符串根节点对象
	 * @return 字符串根节点对象
	 */
	public static Element getRootElement(String xml) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
		Element rootElt = doc.getRootElement(); // 获取根节点 
		return rootElt;
	}
	
	/**
	 * 通过父级标签和要得到的标签名字得到element对象
	 * @param parent
	 * @param tagName
	 * @return
	 */
	public static Element getElementByTagName(Element parent, String tagName) {
		Element ele = null;
		try {
			ele = parent.element(tagName);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return ele;
	}
	
	/**
	 * <p> 通过节点对象和名字获取相应节点的值---会去除换行  <p>
	 * @param element 节点对象
	 * @param name 要获取的节点的名称
	 * @return 节点中的值
	 */
	public static String getTextByElementName(Element element, String name) {
		String result = null;
		if (null != element && null != name && !name.trim().equals("")) {
			try {
				result = element.elementTextTrim(name);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 通过节点对象和名字获取相应节点中的属性值
	 * @param element 要获取的属性的节点对象
	 * @param name 要获取的属性名称
	 * @return相应属性的值
	 */
	public static String getattributeValueByElementName(Element element, String name) {
		String result=null;
		if(null != element && null != name && !name.trim().equals("")) {
			try {
				result = element.attributeValue(name);	
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 给已知的节点添加属性和属性值
	 * @param elt 需要添加属性的节点
	 * @param attrName 属性名
	 * @param attrValue 属性值
	 * @return 添加属性后的节点
	 */
	public static Element addAttributeForElement(Element elt, String attrName, String attrValue) {
		if(null != elt) {
			elt.addAttribute(attrName, attrValue);
		}
		return elt;
	}
}
