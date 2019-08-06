package my.spring.springedu;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dao.OnePersonDAO;
import dao.SubwayDAO;
import vo.OnePersonVO;
import vo.SubwayVO;
@Controller
public class OnePersonController {
	@Autowired
	OnePersonDAO dao;
	
	@RequestMapping("/one")
	ModelAndView detail() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("OnePersonView");
		return mav;	
	}
	
	@RequestMapping("/detailone")
	ModelAndView selectData1(int fid) {
		System.out.println("detailOne의 i값:"+fid);
		ModelAndView mav = new ModelAndView();
		if(fid==1) {
			List<OnePersonVO> list = dao.select1(fid);
			mav.addObject("list1", list);
		}
		else if(fid==2) {
			List<OnePersonVO> list = dao.select2(fid);
			mav.addObject("list1", list);
		}
		else if(fid==3) {
			List<OnePersonVO> list = dao.select3(fid);
			mav.addObject("list1", list);
		}
		else if(fid==4) {
			List<OnePersonVO> list = dao.select4(fid);
			mav.addObject("list1", list);
		}
		mav.setViewName("OnePersonView");
		return mav;	
	}
	
	@RequestMapping("/detailkeyword")
	ModelAndView selectKey(String search) {
		System.out.println("search의 i값:"+search);
		ModelAndView mav = new ModelAndView();
			List<OnePersonVO> list = dao.search(search);
			mav.addObject("list1", list);
		mav.setViewName("OnePersonView");
		return mav;	
	}
}
