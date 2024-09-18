package com.hms.service;

import java.util.List;
import java.util.Optional;

import com.hms.entity.SetRates;
import com.hms.exception.UserException;

public interface SetRatesService {

    SetRates saveSetRates(SetRates setRates)throws UserException;

    SetRates getSetRatesById(Long id) throws UserException;

    List<SetRates> getAllSetRates()throws UserException;


}

