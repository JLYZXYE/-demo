package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.dto.SeckillResult;
import org.seckill.enity.Seckill;
import org.seckill.enums.SeckillEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SeckillController {
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private SeckillService seckillService;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> seckillList = seckillService.getSeckillList();
		model.addAttribute("list", seckillList);
		return "list";
		//WEB-INF/jsp/list.jsp
	}
	
	@RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
	public String detail(Model model,@PathVariable("seckillId") Long seckillId) {
		if (seckillId ==null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill ==null) {
			return "forword:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}
	
	//ajax json
	@RequestMapping(value="/{seckillId}/exposer"
			,method=RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public  SeckillResult<Exposer> exposer(@PathVariable("seckillId")Long seckillId) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result = new SeckillResult<>(false, e.getMessage());
		}
		return result;
	}
	
	
	@RequestMapping(value="/{seckillId}/{m5}/execution",method=RequestMethod.POST
			,produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExcution> execute(@PathVariable("seckillId")Long seckillId
			,@PathVariable("md5")String md5
			,@CookieValue(value="killPhone",required = false)Long phone){
		if (phone == null) {
			return new SeckillResult<SeckillExcution>(false, "未注册");
		}
		SeckillResult<SeckillExcution> result;
		try {
			SeckillExcution excution =  seckillService.executeSeckill(seckillId, phone, md5);
			return new SeckillResult<SeckillExcution>(true, excution);
		} catch (RepeatKillException e) {
			SeckillExcution excution = new SeckillExcution(seckillId, SeckillEnum.Repeat_KILL);
			return new SeckillResult<SeckillExcution>(false, excution);
		}catch (SeckillCloseException e) {
			SeckillExcution excution = new SeckillExcution(seckillId, SeckillEnum.END);
			return new SeckillResult<SeckillExcution>(false, excution);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			SeckillExcution excution = new SeckillExcution(seckillId, SeckillEnum.INNER_KILL);
			return new SeckillResult<SeckillExcution>(false, excution);
		}
	}

	@RequestMapping(value = "/time/now",method = RequestMethod.GET)
	    @ResponseBody
	    public SeckillResult<Long> time(){
	        Date now = new Date();
	        System.out.println(new SeckillResult(true,now.getTime()));
	        return new SeckillResult(true,now.getTime());
	    }
}
