package org.jsrml.sv.organization.controller;

import com.alibaba.fastjson.serializer.SerializerFeature;
import org.jsrml.common.exception.RequestException;
import org.jsrml.common.util.JSONResultDTO;
import org.jsrml.common.util.JSONUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {


	@ModelAttribute
	public void initData(HttpServletRequest request, Model model) {

	}

	/**
	 * 自动将yyyy-MM-dd格式的参数转换成Date型参数
	 * 
	 * @param binder
	 * @throws RequestException
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) throws RequestException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat1.setLenient(true);

		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat2.setLenient(true);

		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat1, true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat2, true));

	}

	public static JSONResultDTO createSuccessResult(Object resultData) {
		return createSuccessResult(resultData, true);
	}

	public static JSONResultDTO createSuccessResult(Object resultData, boolean hibernateProxyRemove) {
		JSONResultDTO result = new JSONResultDTO();
		result.setCode("0");
		result.setMessage("请求成功");
		result.setData(resultData);

		if (hibernateProxyRemove) {
			result = JSONUtils.p(JSONUtils.c(result), JSONResultDTO.class);
		}

		return result;
	}

	public static String createSuccessResultJSON(Object resultData, boolean allowEmply) {
		if (!allowEmply && resultData == null) {
			return JSONUtils.c(createFailResult("data_is_empty", "数据不存在", resultData));
		}
		return JSONUtils.c(createSuccessResult(resultData, true), SerializerFeature.DisableCircularReferenceDetect);
	}

	public static String createSuccessResultJSON(Object resultData) {
		return JSONUtils.c(createSuccessResult(resultData, true), SerializerFeature.DisableCircularReferenceDetect);
	}

	public static String createSuccessResultJSON(Object resultData, boolean hibernateProxyRemove,
			SerializerFeature... features) {
		return JSONUtils.c(createSuccessResult(resultData, hibernateProxyRemove), features);
	}

	public static JSONResultDTO createFailResult(String code, String message, Object resultData) {
		JSONResultDTO result = new JSONResultDTO();
		result.setCode(code);
		result.setMessage(message);
		result.setData(resultData);
		return result;
	}

	public static String createFailResultJSON(String code, String message, Object resultData) {
		return JSONUtils.c(createFailResult(code, message, resultData),
				SerializerFeature.DisableCircularReferenceDetect);
	}


}
