package com.cg.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.cg.dto.HotelDto;
import com.cg.dto.LoginDto;
import com.cg.exception.HotelException;

@Repository("hbmsDao")
public class HBMSDaoImpl implements IHBMSDao  {
	HotelDto hotelDto;
	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public boolean validateLogin(String userid, String password) {
		LoginDto loginDto;
		loginDto=entityManager.find(LoginDto.class,userid);
		if(loginDto.getPassword().equals(password))
		{
			return true;
		}
		else
		{
		return false;
	}

}
	@Override
	public int addNewHotel(HotelDto hotelDto) {
		
		entityManager.persist(hotelDto);
		entityManager.flush();
		return hotelDto.getHotelId();
	}
	@Override
	public List<HotelDto> getAllHotels() {
		List<HotelDto> hotelList;
		String getHotelQuery="from HotelDto";
		TypedQuery<HotelDto> query=entityManager.createQuery(getHotelQuery,HotelDto.class);
		hotelList=query.getResultList();
		entityManager.flush();
		
		return hotelList;
	}
	@Override
	public void deleteHotel(int hotelId) throws HotelException {
		
		try{
		hotelDto=entityManager.find(HotelDto.class, hotelId);
		entityManager.remove(hotelDto);
		entityManager.flush();}
		catch(Exception e)
		{	System.out.println("In dao ");
			throw new HotelException("Hotel cannot be deleted because child records exist.");
		}
		
	}
	@Override
	public HotelDto retrieveHotelData(int hotelId) {
		hotelDto=entityManager.find(HotelDto.class, hotelId);
		entityManager.flush();
		return hotelDto;
	}
	@Override
	public void updateHotel(HotelDto hotel) {
		HotelDto hotelDto = entityManager.find(HotelDto.class,hotel.getHotelId()); //Consider em as JPA EntityManager
		System.out.println(hotelDto);
		entityManager.merge(hotel);

		entityManager.flush();
		
		/*hotelDto=entityManager.find(HotelDto.class, hotel.getHotelId());
		hotelDto.merge(hotel);
		entityManager.flush();*/
		
	}
	@Override
	public void deleteRoom(int hotelId) {
		try{
			hotelDto=entityManager.find(HotelDto.class, hotelId);
			entityManager.remove(roomId);
			entityManager.flush();}
			catch(Exception e)
			{	System.out.println("In dao ");
				throw new HotelException("Hotel cannot be deleted because child records exist.");
			}
		
	}
	
}