package src.com.jiafei521.tools.utils;

import java.io.File;

public interface CodeCount {
	/**
	 * 需要处理的文件后缀
	 * @param suffix
	 * @return
	 */
	public String dealSuffix();
	
	/**
	 * 定义如何处理
	 * @param file 该后缀的文件
	 * @return 处理后的代码数
	 */
	public Double dealType(File file);

}
