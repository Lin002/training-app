package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ����SSM��ܵĵ�һ��������
 * 1.���@Controller
 * 2.�������ӳ��·��
 * @author 46365
 *@since jdk8
 */
@Controller
public class HelloController {
	@RequestMapping("/hi")
	@ResponseBody
    public String hello() {
	return "hello nuc-a";
}
	public void hi() {
		System.out.println("hi,haha");
	}
}
