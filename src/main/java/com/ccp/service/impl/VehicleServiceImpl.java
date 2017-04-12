package com.ccp.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.ccp.dao.VehicleDAO;
import com.ccp.model.Vehicle;
import com.ccp.service.VehicleService;

public class VehicleServiceImpl implements VehicleService {
	
	private VehicleDAO vehicleDAO;
	
	public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }
	
	@Override
	@Transactional
	public Vehicle save(Vehicle vehicle) {
		return this.vehicleDAO.save(vehicle);
	}
}
