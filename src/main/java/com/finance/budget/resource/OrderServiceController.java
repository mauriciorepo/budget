package com.finance.budget.resource;

import javax.validation.Valid;



import com.finance.budget.model.Company;
import com.finance.budget.model.OrderService;
import com.finance.budget.model.OrderServiceItems;
import com.finance.budget.resource.dto.CompanyDTO;
import com.finance.budget.resource.dto.OrderServiceDTO;
import com.finance.budget.resource.dto.OrderServiceItemsDTO;
import com.finance.budget.service.CompanyService;
import com.finance.budget.service.OrderServiceService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;

import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders-service")
@RequiredArgsConstructor

public class OrderServiceController {

    private final CompanyService companyService;
    private final OrderServiceService orderServiceService;
    private final ModelMapper modelMapper;




    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderService create(@RequestBody @Valid OrderServiceDTO dto){



        return companyService.getById(dto.getId_company()).map(company -> {

             OrderService order=modelMapper.map(dto,OrderService.class);

             order.setCompany(company);
             //OrderService orderService=new
             order.getList().forEach(entity-> entity.setOrderService(order));


             return orderServiceService.create(order);

        }).orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not exist"));

    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderServiceDTO> findOrderService(CompanyDTO companyDTO, Pageable pageRequest){
        Company company= modelMapper.map(companyDTO, Company.class);

        Page<OrderService> resultList=orderServiceService.findOrderServiceByIdCompany(company, pageRequest);
        modelMapper.typeMap(OrderServiceItems.class, OrderServiceItemsDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getOrderService().getId(),
                    OrderServiceItemsDTO::setORDERSERVICE_ID);

        });
        List list=resultList.getContent()
                .stream()
                .map(entity->modelMapper.map(entity, OrderServiceDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<OrderServiceDTO>(list,pageRequest,resultList.getTotalElements());

    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderServiceDTO> findOrderServiceByIdCompany(@PathVariable Long id){

       List<OrderService> listResult= orderServiceService.findByIdCompany(id);
       //listResult.stream().map(OrderService::getList()).;
        /*List<OrderServiceDTO> orderServiceDTOList= listResult.stream().map(entity-> {modelMapper.map
            return null;
        });*/
        modelMapper.typeMap(OrderServiceItems.class, OrderServiceItemsDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getOrderService().getId(),
                    OrderServiceItemsDTO::setORDERSERVICE_ID);

        });
        List<OrderServiceDTO> list=listResult
                .stream()
                .map(entity->modelMapper.map(entity, OrderServiceDTO.class))
                .collect(Collectors.toList());

       return list;
    }

   /* @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderServiceDTO updateOrderService(@PathVariable  Long id, @RequestBody OrderServiceDTO orderServiceDTO){


        if((id!=orderServiceDTO.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "different id value");
        }
        return  orderServiceService.getById(id).map(
                orderService-> {
                    Company company =companyService.getById(orderServiceDTO.getId_company()).get();
                    //BeanUtils.copyProperties(orderService,orderServiceDTO);
                    orderService.setAnnotation(orderServiceDTO.getAnnotation());
                    orderService.setStatus(orderServiceDTO.getStatus());
                    orderService.setTitle(orderServiceDTO.getTitle());

                    List<OrderServiceItems> items=    orderServiceDTO.getList()
                            .stream()
                            .map((entity)->
                            //entity.setORDERSERVICE_ID(orderServiceDTO.getId())
                                    modelMapper.map(entity,OrderServiceItems.class)

                            ).collect(Collectors.toList());
                    //orderService.getList().forEach(entity->entity.setOrderService(orderService));
                   // orderService.setList(null);
                    orderService.setList(items);
                    // orderService.getList().retainAll(items);
                    System.out.println(items);

                    //orderService.getList().removeIf((entity)-> !entity.contains(orderServiceDTO.getList()));
                    //orderService.getList().removeIf((entity)-> !orderService.getList().contains(entity));


                    //orderService
                    orderService.setCompany(company);
                    OrderService updatableOrderService =orderServiceService.update(orderService);
                    return modelMapper.map(updatableOrderService,OrderServiceDTO.class);

                }).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not exist"));
    } */

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderServiceDTO update(@PathVariable Long id, @RequestBody OrderServiceDTO orderServiceDTO){

        if((id!=orderServiceDTO.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "different id value");
        }

        OrderService order=orderServiceService.getById(id).map(orderService-> {
            //Company company = companyService.getById(orderServiceDTO.getId_company()).get();
            //BeanUtils.copyProperties(orderService,orderServiceDTO);
            orderService.setAnnotation(orderServiceDTO.getAnnotation());
            orderService.setStatus(orderServiceDTO.getStatus());
            orderService.setTitle(orderServiceDTO.getTitle());
            orderService.setDescription(orderServiceDTO.getDescription());
            return orderService;
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not exist" ));

        List<OrderServiceItems> listItems=orderServiceDTO
                .getList()
                .stream()
                .map(orderServiceItemsDTO-> DTOToOrderserviceItems(orderServiceItemsDTO))
                .collect(Collectors.toList());
        order.setList(listItems);
        //order.getList().retainAll(listItems);

          OrderService updateOrderService= orderServiceService.update(order);

         updateOrderService.getList().stream().filter(entity-> !listItems.contains(entity)).forEach(entity-> OrderServiceService.);
         //System.out.println(order.getList());
         //order.getList().forEach((entity)-> updateOrderService.removeItem(entity));
          return OrderServiceToDTO(updateOrderService)/*modelMapper.map(updateOrderService,OrderServiceDTO.class)*/;


    }


    private OrderServiceDTO OrderServiceToDTO(OrderService entity){
        modelMapper.typeMap(OrderServiceItems.class,OrderServiceItemsDTO.class ).addMappings(mapper ->{
            mapper.map(src -> src.getId(),(dest, v) -> dest.setORDERSERVICE_ID((Long) v) );
            //mapper.map(src->  );
        });

        modelMapper.typeMap(OrderService.class,OrderServiceDTO.class ).addMappings(mapper ->{
            mapper.map(src -> src.getCompany().getId(),(dest, v) -> dest.setId_company((Long) v) );
            //mapper.map(src->  );
        });

        return modelMapper.map(entity, OrderServiceDTO.class );
    }

    private OrderServiceItems DTOToOrderserviceItems(OrderServiceItemsDTO dto){
        modelMapper.typeMap(OrderServiceItemsDTO.class,OrderServiceItems.class ).addMappings(mapper ->{
            mapper.map(src -> src.getORDERSERVICE_ID(),(dest, v) -> dest.getOrderService().setId((Long) v) );
            //mapper.map(src->  );
        });
        return modelMapper.map(dto,OrderServiceItems.class);
    }

}
