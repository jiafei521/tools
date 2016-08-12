package com.jiafei521.tools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 统计代码数量的工具类
 * @author jia
 *
 */
public class CodeCountUtils {
	
	private List<File> address;//存储地址的集合
	
	private double count = 0;//代码总数量
	
	private double jspcount;//jsp代码数量
	
	private double htmlcount;//html代码数量
	
	private double javacount;//java代码数量
	
	private double jscount;//js代码数量
	
	private double interfaceCount = 0;//传入的接口计算结果
	
	
	
	public double getInterfaceCount() {
		return interfaceCount;
	}
	public void setInterfaceCount(double interfaceCount) {
		this.interfaceCount = interfaceCount;
	}

	private CodeCount codeCount;//统计自定义类型
	
	
	private Map<CodeCount, Double> counts = new HashMap<>();
	
	
	public Map<CodeCount, Double> getCounts() {
		return counts;
	}
	public void setCounts(Map<CodeCount, Double> counts) {
		this.counts = counts;
	}
	public CodeCount getCodeCount() {
		return codeCount;
	}
	public void setCodeCount(CodeCount codeCount) {
		this.codeCount = codeCount;
	}

	private boolean isPrintFileName;//是否答应处理的文件名
	
	public double getJspcount() {
		return jspcount;
	}
	public double getHtmlcount() {
		return htmlcount;
	}
	public double getJavacount() {
		return javacount;
	}
	public double getJscount() {
		return jscount;
	}

	
	
	public List<File> getAddress() {
		return address;
	}
	
	public boolean isPrintFileName() {
		return isPrintFileName;
	}
	public void setPrintFileName(boolean isPrintFileName) {
		this.isPrintFileName = isPrintFileName;
	}
	public void setAddress(List<File> address) {
		this.address = address;
	}

	private boolean isCountJSP = true;//是否统计JSP文件
	
	private boolean isCountHTML = false;//是否统计HTML文件
	
	private boolean isCountJAVA = true;//是否统计JAVA文件
	
	private boolean isCountJS = false;//是否同价JS文件
	
	public CodeCountUtils(){		
	}
	/**
	 * 
	 * @param address 要统计的目录文件
	 */
	public CodeCountUtils(List<File> address){
		this.address = address;
	}
	/**
	 * 
	 * @param address 要统计的目录字符串
	 */
	public CodeCountUtils(String[] address){
		
			for (String string : address) {
				File f = new File(string);
				this.address.add(f);
			}
		
	}
	
	
	public boolean isCountJSP() {
		return isCountJSP;
	}

	public void setCountJSP(boolean isCountJSP) {
		this.isCountJSP = isCountJSP;
	}

	public boolean isCountHTML() {
		return isCountHTML;
	}

	public void setCountHTML(boolean isCountHTML) {
		this.isCountHTML = isCountHTML;
	}

	public boolean isCountJAVA() {
		return isCountJAVA;
	}

	public void setCountJAVA(boolean isCountJAVA) {
		this.isCountJAVA = isCountJAVA;
	}

	public boolean isCountJS() {
		return isCountJS;
	}

	public void setCountJS(boolean isCountJS) {
		this.isCountJS = isCountJS;
	}


	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}
	/**
	 * 递归迭代目录
	 * @param file
	 * @throws Exception
	 */
	private  void dealFile(File file)throws Exception {
		//判断是否为目录
		if(file.isDirectory())
		{
			File[] files = file.listFiles();
			for (File file2 : files) {
				dealFile(file2);
			}
		}
		else{
			
			//获取文件名
			String name = file.getName();
			//统计以.java为后缀的文件
			if(isCountJSP){
				if(name.endsWith(".jsp")){
					if(isPrintFileName){
						System.out.println(name);
					}
					count(file,"jsp");
				}
			}
			if(isCountHTML){
				if(name.endsWith(".htm")||name.endsWith(".html")){
					if(isPrintFileName){
						System.out.println(name);
					}
					count(file,"html");
				}
			}
			if(isCountJS){
				if(name.endsWith(".js")){
					if(isPrintFileName){
						System.out.println(name);
					}
					count(file,"js");
				}
			}
			if(isCountJAVA){
				
				if(name.endsWith(".java")){
					if(isPrintFileName){
						System.out.println(name);
					}
					count(file,"java");
				}
			}
			if(codeCount!=null){
				//接口内传递了参数
				String suffix = codeCount.dealSuffix();
				if(name.endsWith(suffix)){
					if(isPrintFileName){
						System.out.println(name);
					}
					count(file,suffix);
				}
			}
			if(counts!=null){
				if(counts.size()>0){
					for (CodeCount c : counts.keySet()) {
						if(c!=null){
							//接口内传递了参数
							String suffix = c.dealSuffix();
							if(name.endsWith(suffix)){
								if(isPrintFileName){
									System.out.println(name);
								}
								count(file,suffix);
							}
						}
					}
				}
			}
	
			
		}
		
		
	}
	/**
	 * 统计代码
	 * @param file 要统计的文件
	 * @param type 类型
	 */
	private void count(File file,String type)  {
		try{
		//读取文件到流中，按行读取
		FileInputStream inputStream = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		//判读行中是否存在；如果存在则count+1
		while(reader.read()!=-1){
			String str  = reader.readLine();
			if(str!=null){
				//类型为java文件
				if(type.equals("java")){
					//统计有；的行
					if(str.contains(";")){
						javacount++;
					}
					
				}
				if(type.equals("jsp")){
					if(str.trim()!=""){
						jspcount++;
					}
				}
				if(type.equals("html")){
					if(str.trim()!=""){
						htmlcount++;
					}
				}
				if(type.equals("js")){
					if(str.trim()!=""){
						jscount++;
					}
				}
				
				
			
			}
			
		}
		//处理单个自定义类型
		if(codeCount!=null){
			if(type.equals(codeCount.dealSuffix())){
				interfaceCount += codeCount.dealType(file);
			}
		}
		//处理多个自定义类型
		if(counts!=null){
			if(counts.size()>0){
				for (CodeCount c : counts.keySet()) {
					if(type.equals(c.dealSuffix())){
						Double cc = c.dealType(file);
						Double currentVal = counts.get(c);
						counts.put(c, cc+currentVal);
					}
				}
				
			}
		}
		}catch (Exception e) {
			throw new RuntimeException("count "+e);
		}
	}
	public void getResult(){
		try {
			for (File f : address) {
				dealFile(f);
			}
			if (isCountJSP) {
				count += jspcount;
				System.out.println("JSP代码总数为：" + jspcount);
			}
			if (isCountHTML) {
				count += htmlcount;
				System.out.println("html代码总数为：" + htmlcount);
			}
			if (isCountJS) {
				count += jscount;
				System.out.println("js代码总数为：" + jscount);
			}
			if (isCountJAVA) {
				count += javacount;
				System.out.println("java代码总数为：" + javacount);
			}
			if(interfaceCount!=0){
				count += interfaceCount;
				String suffix = codeCount.dealSuffix();
				suffix = suffix.substring(1,suffix.length());
				System.out.println(suffix+"代码总数为：" + interfaceCount);
			}
			if(counts.size()>0){
				for (CodeCount c :counts.keySet()) {
					//得到每一个
					count += counts.get(c);
					String suffix = c.dealSuffix();
					suffix = suffix.substring(1,suffix.length());
					System.out.println(suffix+"代码总数为：" + counts.get(c));
					
				}
			}
			System.out.println("代码总数为：" + count);
		} catch (Exception e) {
			throw new RuntimeException(e+"地址加载出错了");
		}
	}
	public static void main(String[] args){
		List<File> address = new ArrayList<File>();
		File file = new File("D:\\data\\workspace\\j2ee");
		//File file = new File("D:\\data\\workspace\\j2ee\\mimile_ssh");
		address.add(file);
		CodeCountUtils countUtils = new CodeCountUtils();
		countUtils.setAddress(address);//设置加载的地址
		countUtils.setCountHTML(false);//设置统计html文件
		//countUtils.setPrintFileName(true);//设置打印文件名
		countUtils.setCountJSP(true);//设置不统计jsp文件
		CodeCount cd = new CodeCount() {
			
			@Override
			public Double dealType(File file) {
				//System.out.println(file.getName());
				return 1.0;
			}
			
			@Override
			public String dealSuffix() {
				return ".xml";
			}
		};
		CodeCount cd1 = new CodeCount() {
			
			@Override
			public Double dealType(File file) {
				//System.out.println(file.getName());
				return 1.0;
			}
			
			@Override
			public String dealSuffix() {
				return ".css";
			}
		};
		//countUtils.setCodeCount(cd);
		Map<CodeCount,Double> counts = new HashMap<CodeCount,Double>();
		counts.put(cd1, 0.0);
		counts.put(cd, 0.0);
		countUtils.setCounts(counts);
		countUtils.getResult();
		
		

	}
	
	

}
