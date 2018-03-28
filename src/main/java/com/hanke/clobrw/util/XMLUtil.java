package com.hanke.clobrw.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hanke.clobrw.generator.ISqlGenerator;
import com.hanke.clobrw.holder.Column;
import com.hanke.clobrw.holder.ColumnHolder;
import com.hanke.clobrw.holder.MapHolder;
import com.hanke.clobrw.type.IColumnType;

public class XMLUtil {
	
	private static final Logger log = LoggerFactory.getLogger(XMLUtil.class);
	
	/**
	 * 获取文档
	 * @param name 文档名
	 * @return
	 */
	public static Document getDoc(String name) {
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(name));
			return doc;
		} catch(Exception e) {
			e.printStackTrace();
		}
		 return doc;
	}
	
	/**
	 * 获取文档的根节点
	 * @param doc
	 * @return
	 */
	public static Element getRoot(Document doc) {
		return doc.getDocumentElement();
	}
	
	public static MapHolder getMapHolder(String name) {
		Document doc = getDoc(name);
		Element root = getRoot(doc);
		String neneratorClass = root.getAttribute("generator");
		ISqlGenerator gen = null;
		try {
			gen = (ISqlGenerator)Class.forName(neneratorClass).newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		Node source = root.getElementsByTagName("sourceTableName").item(0);
		String sourceId = ((Element)source).getAttribute("id");
		String sourceIdTypeName = ((Element)source).getAttribute("type");
		Node target = root.getElementsByTagName("targetTableName").item(0);
		String targetId = ((Element)target).getAttribute("id");
		String targetIdTypeName = ((Element)target).getAttribute("type");
		IColumnType sourceIdType = null;
		IColumnType targetIdType = null;
		if(!StringUtil.isBlank(targetIdTypeName) && !StringUtil.isBlank(sourceIdTypeName))
			try {
				sourceIdType = (IColumnType)Class.forName(sourceIdTypeName).newInstance();
				targetIdType = (IColumnType)Class.forName(targetIdTypeName).newInstance();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		log.info("source table name is :{}\n", source.getTextContent());
		log.info("target table name is :{}\n", target.getTextContent());
		
		NodeList list = root.getElementsByTagName("columns");
		List<ColumnHolder> columns = new ArrayList<ColumnHolder>();
		for (int i = 0; i < list.getLength(); i++) {
			Node item = list.item(i);
			NodeList columnNodes = item.getChildNodes();
			
			for(int j = 0;j < columnNodes.getLength();j++) {
				if("column".equals(columnNodes.item(j).getNodeName())) {
					ColumnHolder columnHolder = new ColumnHolder();
					columnHolder.setIndex(columns.size() + 1);
					NodeList nlist = columnNodes.item(j).getChildNodes();
					Element sourceColumn = (Element)nlist.item(1);
					String className = sourceColumn.getAttribute("type");
					IColumnType type = null;
					try {
						type = (IColumnType)Class.forName(className).newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
					columnHolder.setSource(new Column(sourceColumn.getTextContent(), type));
					Element targetColumn = (Element)nlist.item(3);
					className = targetColumn.getAttribute("type");
					try {
						type = (IColumnType)Class.forName(className).newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
					columnHolder.setTarget(new Column(targetColumn.getTextContent(), type));
					columns.add(columnHolder);
				}
			}
		}
		MapHolder holder = new MapHolder(gen, source.getTextContent(), target.getTextContent() 
				, new Column(sourceId, sourceIdType), new Column(targetId, targetIdType), columns);
		return holder;
	}
	
	public static List<MapHolder> getListMapHolder(String dir) {
		List<MapHolder> list = new ArrayList<MapHolder>();
		String fileName = Thread.currentThread().getContextClassLoader()
		.getResource(dir).getFile();
		File file = new File(fileName);
		if(file.isDirectory()) {
			String[] files = file.list();
			for (String item : files) {
				if (item.endsWith(".xml")) {
					list.add(getMapHolder(file.getAbsolutePath() + File.separator + item));
				}
			}
		}else if(fileName.endsWith(".xml"))
			list.add(getMapHolder(file.getAbsolutePath()));
		return list;
	}
}
