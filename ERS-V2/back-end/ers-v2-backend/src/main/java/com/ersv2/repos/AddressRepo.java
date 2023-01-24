package com.ersv2.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ersv2.models.Address;

@Repository
public interface AddressRepo extends JpaRepository <Address, Long>{

}
