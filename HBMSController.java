package com.cg.controller;



import java.util.ArrayList;




import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cg.dto.HotelDto;
import com.cg.dto.LoginDto;
import com.cg.exception.HotelException;
import com.cg.service.IHBMSService;
@Controller
public class HBMSController {
	ModelAndView mv;
	@Autowired
	IHBMSService hbmsService;
	@RequestMapping("/")
	public ModelAndView first()
	{

		mv =new ModelAndView("login");

		mv.addObject("login", new LoginDto());

		return mv;

	}
	@RequestMapping("/validateLogin")
	public ModelAndView validateLogin(@ModelAttribute("login") @Valid LoginDto dto,BindingResult result){
		

		if(result.hasErrors())
		{
			 mv = new ModelAndView("login");
			return mv;
		}
		boolean validateLogin;
		try {
			validateLogin = hbmsService.validateLogin(dto.getUserid(),dto.getPassword());
			if(validateLogin){
				 mv = new ModelAndView("success");
						return mv;
					}
					else
					{
						mv = new ModelAndView("error");
						return mv;

					}
		} catch (HotelException e) {
			mv=new ModelAndView("error");
			mv.addObject("exception",e.getMessage());
			return mv;
		}
	

	}
	@RequestMapping("/hotelManagement")
	public ModelAndView hotelManagementOperations()
	{
		List<HotelDto> hotelList;
		try {
			hotelList = hbmsService.getAllHotels();
			 mv = new ModelAndView("hotelManagement");
				mv.addObject("hotelList",hotelList);
				return mv;

		} catch (HotelException e) {
			mv=new ModelAndView("error");
			mv.addObject("exception",e.getMessage());
			return mv;
		}
		
	}
	@RequestMapping("/home")
	public ModelAndView home()
	{
		
		 mv = new ModelAndView("success");
		return mv;

	}

	@RequestMapping(value="/addHotel",method=RequestMethod.POST)
	public ModelAndView addHotel()
	{
		mv = new ModelAndView("addHotel");
		ArrayList<String>	cityList=new ArrayList<String>();
		cityList.add("Anantapur");
		cityList.add("Hyderabad");
		cityList.add("Bangalore");
		cityList.add("Chennai");
		cityList.add("Kolkata");
		cityList.add("Noida");
		cityList.add("Pune");
		cityList.add("Goa");
		cityList.add("Vizag");
		cityList.add("New Delhi");
		cityList.add("Maisore");
		cityList.add("Nellore");
		cityList.add("Medchal");
		mv.addObject("cityList",cityList);
		
		ArrayList<String>	rating=new ArrayList<String>();
		rating.add("Good");
		rating.add("Average");
		rating.add("Poor");
		mv.addObject("rating", rating);
		mv.addObject("add", new HotelDto());
		return mv;

	}
	@RequestMapping("/addHotelData")
	public ModelAndView addHotelData(@ModelAttribute("add") @Valid HotelDto hotelDto,BindingResult result)
	{	
		
		if(result.hasErrors()){
			 mv=new ModelAndView("addHotel");
			ArrayList<String>	cityList=new ArrayList<String>();
			cityList.add("Anantapur");
			cityList.add("Hyderabad");
			cityList.add("Bangalore");
			cityList.add("Chennai");
			cityList.add("Kolkata");
			cityList.add("Noida");
			cityList.add("Pune");
			cityList.add("Goa");
			cityList.add("Vizag");
			cityList.add("New Delhi");
			cityList.add("Maisore");
			cityList.add("Nellore");
			cityList.add("Medchal");
			mv.addObject("cityList",cityList);
			
			ArrayList<String>	rating=new ArrayList<String>();
			rating.add("Good");
			rating.add("Average");
			rating.add("Poor");
			mv.addObject("rating", rating);
			
			return mv;
		}
		else{
			
			try {
				hbmsService.addNewHotel(hotelDto);
				List<HotelDto> hotelList=hbmsService.getAllHotels();
				mv = new ModelAndView("hotelManagement");
				mv.addObject("hotelList",hotelList);
				return mv;
				
			} catch (HotelException e) {
				
				mv=new ModelAndView("error");
				mv.addObject("exception",e.getMessage());
				return mv;
			}
		
			
		}
		
		
	}
	@RequestMapping("/deleteHotel")
	public ModelAndView deleteHotel(@RequestParam("hId")int hotelId){
		
		System.out.println("in controll");
	try{
		System.out.println("in tru11");
		hbmsService.deleteHotel(hotelId);
		System.out.println("In controler try");
		List<HotelDto> hotelList=hbmsService.getAllHotels();
		mv = new ModelAndView("hotelManagement");
		mv.addObject("hotelList",hotelList);
		return mv;
	}catch(HotelException e){

		mv=new ModelAndView("error");
		mv.addObject("exception",e.getMessage());
		return mv;
	}

		
	}
	@RequestMapping("/updateHotel")
	public ModelAndView updateHotel(@RequestParam("hId")int hotelId){
		HotelDto hotelData=hbmsService.retrieveHotelData(hotelId);
		
		mv = new ModelAndView("update");
		mv.addObject("hotelData",hotelData);
		mv.addObject("update", new HotelDto());
		return mv;
		
	}
	@RequestMapping("/update")
	public ModelAndView updateHotel(@ModelAttribute("update") HotelDto hotel){
		try {
			System.out.println(hotel.toString());
		hbmsService.updateHotel(hotel);
		
		List<HotelDto> hotelList;
		
			hotelList = hbmsService.getAllHotels();
			mv = new ModelAndView("hotelManagement");
			mv.addObject("hotelList",hotelList);
			return mv;
		} catch (HotelException e) {
			mv=new ModelAndView("error");
			mv.addObject("exception",e.getMessage());
			return mv;
		}
		
		}
	
	
	
	//	Room management
	@RequestMapping("/roomManagement")
	public ModelAndView roomManagement()
	{
		List<HotelDto> hotelList;
		try {
			hotelList = hbmsService.getAllHotels();
			 mv = new ModelAndView("hotelNames");
				mv.addObject("hotelList",hotelList);
				return mv;

		} catch (HotelException e) {
			mv=new ModelAndView("error");
			mv.addObject("exception",e.getMessage());
			return mv;
		}
		
	}
	@RequestMapping("/roomManagementOperations")
	public ModelAndView roomManagementOperations(){

		List<HotelDto> hotelList;
		try {
			hotelList = hbmsService.getAllHotels();
			 mv = new ModelAndView("roomManagement");
				mv.addObject("hotelList",hotelList);
				return mv;

		} catch (HotelException e) {
			mv=new ModelAndView("error");
			mv.addObject("exception",e.getMessage());
			return mv;
		}
		
		@RequestMapping("/deleteHotel")
		public ModelAndView deleteRoom(@RequestParam("hId")int hotelId){
			
			System.out.println("in controll");
		try{
			System.out.println("in tru11");
			hbmsService.deleteRoom(hotelId);
			System.out.println("In controler try");
			List<HotelDto> hotelList=hbmsService.getAllHotels();
			mv = new ModelAndView("roomManagement");
			mv.addObject("hotelList",hotelList);
			return mv;
		}catch(HotelException e){

			mv=new ModelAndView("error");
			mv.addObject("exception",e.getMessage());
			return mv;
		}
		
	}
}
