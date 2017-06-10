package org.jsrml.common.util;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtil {

	private static Map<String, Map<Object, Object>> propCacheMap = new HashMap<String, Map<Object,Object>>();
	
	public static String getProperiesValue(String key, String path) {
		try {
			return PropertiesLoaderUtils.loadProperties(
					new ClassPathResource(path)).getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Set<Entry<Object, Object>> getAllProperiesEntry(String path) {
		try {
			return PropertiesLoaderUtils.loadProperties(
					new ClassPathResource(path)).entrySet();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

    public static Map<Object, Object> getAllProperiesEntryMap(String path) {
        try {
            Map<Object, Object> map = new HashMap<Object, Object>();
            Set<Entry<Object, Object>> set = PropertiesLoaderUtils.loadProperties(
                    new ClassPathResource(path)).entrySet();
            for(Entry<Object, Object> entry : set){
                map.put(entry.getKey(), entry.getValue());
            }
            return map;
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }

    public static Set<Entry<Object, Object>> getAllProperiesEntryOrder(String path) {
        try {
            InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = input.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }
            BufferedReader bf=new BufferedReader(new StringReader(decodeUnicode(out.toString())));
            String str = "";
            String [] ss = null;
            Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
            while ((str = bf.readLine()) != null){
                str = str.trim();
                if(StringUtils.isNotEmpty(str) && str.indexOf("#") != 0 && str.indexOf("=") > 0){
                    ss=str.split("=");
                    if(ss.length == 2){
                        resultMap.put(ss[0], ss[1]);
                    }
                    else if(ss.length == 1){
                        resultMap.put(ss[0], "");
                    }
                }
            }
            return resultMap.entrySet();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }
    private static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
    
}
