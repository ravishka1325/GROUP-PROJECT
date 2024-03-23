package com.Goappoint.GoAppoint.Controller;

import com.Goappoint.GoAppoint.DTO.ResponseDTO;
import com.Goappoint.GoAppoint.DTO.ServiceDTO;
import com.Goappoint.GoAppoint.Service.ServiceService;
import com.Goappoint.GoAppoint.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/service")
@CrossOrigin
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping(value = "/saveService")
    public ResponseEntity saveService(@RequestBody ServiceDTO serviceDTO){
        try{
            String res = serviceService.saveService(serviceDTO);
            if(res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMesseage("Success");
                responseDTO.setContent(serviceDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            }else if(res.equals("06")){
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMesseage("Service Added");
                responseDTO.setContent(serviceDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }else{
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMesseage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }
        }catch (Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMesseage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getService/{businessId}")
    public ResponseEntity<ResponseDTO> getServicesByBusinessId(@PathVariable int businessId) {
        try {
            List<ServiceDTO> serviceDTOs = serviceService.getServicesByBusinessId(businessId);
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMesseage("Success");
            responseDTO.setContent(serviceDTOs);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException ex) {
            responseDTO.setCode(VarList.RSP_FAIL);
            responseDTO.setMesseage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
    }
}
