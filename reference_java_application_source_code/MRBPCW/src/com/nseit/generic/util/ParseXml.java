package com.nseit.generic.util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXParseException;
import javax.xml.parsers.SAXParser;
import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;

public class ParseXml {
	
	boolean xmlParseError = false;
	
	
	public void processReturnResult(String xmlString, Object xmlObject){
		
		BufferedReader bufferedReader = null;
		SAXBuilder saxBuilder = null;
		Document document = null;
		Element element = null; 
		
		try{
			if(xmlString!=null
					&& !xmlString.equals("")
					&& !xmlString.equalsIgnoreCase("null")
					&& xmlObject!=null){
				
				if(isXmlWellFormed(xmlString, false)){
					bufferedReader = new BufferedReader(new StringReader(xmlString));
					saxBuilder = new SAXBuilder();
					document = saxBuilder.build(bufferedReader); 
					element = document.getRootElement();
					if(element!=null){
						processXml(xmlString, xmlObject);						
					}
				}
			}else{
				throw new Exception();				
			}
		}catch(Exception e){
			e.printStackTrace();
			if(xmlObject!=null){
				if(xmlObject instanceof SMSResponseBean){
					SMSResponseBean SMSResponseBean = (SMSResponseBean) xmlObject;
					SMSResponseBean.setCode("system");
					SMSResponseBean.setDesc("Error Processing SMS Response XML !");
				}
			}
		}
	}
	
	public void processXml(String xmlString, Object xmlObject){
		
		try{
			String xml = ""; 	
			if(xmlString!=null
					&& !xmlString.equals("")
					&& !xmlString.equalsIgnoreCase("null")){
				
				xml = xmlString;
				if(isXmlWellFormed(xml, true)){
					
					BufferedReader bufferedReader = null;
					SAXBuilder saxBuilder = null;
					Document document = null;
					Element element = null;
					
					bufferedReader = new BufferedReader(new StringReader(xml));
					saxBuilder = new SAXBuilder();
					document = saxBuilder.build(bufferedReader);
					
					element = document.getRootElement(); 
					retrieveXmlValues(element, xmlObject);
				}	
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	
	public void retrieveXmlValues(Element element, Object valueObject){
		
		try{
			if(element!=null){
				
				List elementLst = element.getChildren();
				if(elementLst!=null && elementLst.size()>0){
					Element tempElement = null;
					Iterator itrElement = elementLst.iterator();
					while(itrElement.hasNext()){
						tempElement = (Element)itrElement.next();
						retrieveXmlValues(tempElement, valueObject);
					}
				}
				
				List attrbLst = element.getAttributes(); 
				if(attrbLst!=null && attrbLst.size()>0){
					Attribute tempAttribute = null;
					Iterator itrAttribute = attrbLst.iterator();
					while(itrAttribute.hasNext()){
						tempAttribute = (Attribute)itrAttribute.next();
						setValueObjectAttributes(tempAttribute, valueObject);
					}
				}
				setValueObjectElement(element, valueObject);
			}
		}catch(Exception e){
			e.printStackTrace();			
		}
	}
	
	public void setValueObjectAttributes(Attribute attribute, Object valueObject){
		
		try{
			if(attribute!=null && valueObject!=null
					&& attribute.getName()!=null
					&& !attribute.getName().equals("")
					&& !attribute.getName().equalsIgnoreCase("null")
					&& valueObject!=null){
				
				if(valueObject instanceof SMSResponseBean){
					SMSResponseBean SMSResponseBean = (SMSResponseBean)valueObject;
					SMSResponseBean.setObjectProperties(attribute.getName()
							, attribute.getValue(), SMSResponseBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();			
		}
	}
	
	public void setValueObjectElement(Element element, Object valueObject){
		
		try{
			
			if(element!=null && valueObject!=null
					&& element.getName()!=null
					&& !element.getName().equals("")
					&& !element.getName().equalsIgnoreCase("null")
					&& valueObject!=null){
				
				if(valueObject instanceof SMSResponseBean){
					SMSResponseBean SMSResponseBean = (SMSResponseBean)valueObject;
					SMSResponseBean.setObjectProperties(element.getName()
							, element.getValue(), SMSResponseBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();			
		}
	}
	
	public boolean isXmlWellFormed(String xml, boolean validationFlag){
		
		InputSource source = new InputSource(new BufferedReader(new StringReader(xml)));
		boolean returnFlag = false;
		try{
			SAXParserFactoryImpl parserFactoryImpl = new SAXParserFactoryImpl();
			parserFactoryImpl.setValidating(validationFlag);
			parserFactoryImpl.setNamespaceAware(true);
			
			SAXParser parser = parserFactoryImpl.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			
			reader.setErrorHandler(new XMLErrorHandler());
			reader.parse(source);
			returnFlag = !xmlParseError;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnFlag;
	}
	
	public class XMLErrorHandler implements ErrorHandler {
	    public void warning(SAXParseException ex) throws SAXParseException{
	    	//logger.error("Warning inside isWellFormed() - " + ex.getMessage());	        
	    }
	    public void error(SAXParseException ex) throws SAXException{
	    	xmlParseError = true;
	    }
	    public void fatalError(SAXParseException ex) throws SAXException{
	    	xmlParseError = true;
	    }
	}
}

