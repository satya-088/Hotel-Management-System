package com.hms.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.dto.FetchingDto;
import com.hms.entity.Payment;
import com.hms.entity.SetRates;
import com.hms.exception.UserException;
import com.hms.repository.FetchingRepository;
import com.hms.repository.SetRatesRepository;

@Service
public class SetRatesServiceImplementation implements SetRatesService{
		@Autowired
	    private SetRatesRepository setRatesRepository;
		
		@Autowired
		private FetchingRepository fetchingRepository;
		
		Logger log  = LoggerFactory.getLogger(SetRatesServiceImplementation.class);


		@Override
		public SetRates saveSetRates(SetRates setRates) throws UserException {
		    int memberCode = setRates.getFetchingDto().getMemberCode();
		    
		    Optional<FetchingDto> fetchingDtoOpt = fetchingRepository.findById(memberCode);
		    
		    if (fetchingDtoOpt.isPresent()) {
		        FetchingDto fetchingDto = fetchingDtoOpt.get();
		        setRates.setFetchingDto(fetchingDto);
		        setRates.setPricePerDay(fetchingDto.getType().getAmount());

		        long daysBetween = ChronoUnit.DAYS.between(fetchingDto.getCheckIn(), fetchingDto.getCheckOut());
		        if (daysBetween <= 0) {
		            throw new UserException("Check-out date must be after check-in date");
		        }

		       
		        double price = (daysBetween * setRates.getPricePerDay()) - fetchingDto.getAdvanceAmount();
		        setRates.setPrice(price);
		        
		        log.info("successfully saved the Set Rates");
		        
		        return setRatesRepository.save(setRates);
		    } else {
		        throw new UserException("this membercode not found :"+memberCode);
		    }
		}



	    
	    @Override
	    public SetRates getSetRatesById(Long id) throws UserException {
	        Optional<SetRates> setRates = setRatesRepository.findById(id);
	        if(setRates.isPresent()) {
	        	log.info("set rates are found by "+id);
	        return setRates.get();
	    }
	        else
	    	throw new UserException("id not found");
	    }

	    @Override
	    public List<SetRates> getAllSetRates() throws UserException {
	    	if(setRatesRepository.findAll().isEmpty()) {
	    		throw new UserException("it is empty");
	    	}
	    	else {
	    		log.info("list of set rates are founded");
	    		
	    		return setRatesRepository.findAll();
	    	}
	    	
	        
	    }

	}

