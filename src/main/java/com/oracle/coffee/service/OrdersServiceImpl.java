package com.oracle.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.coffee.dao.OrdersDao;
import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.PageRespDto;
import com.oracle.coffee.dto.orders.OrdersDetailDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersListDto;
import com.oracle.coffee.dto.orders.OrdersPageDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;
import com.oracle.coffee.dto.orders.OrdersRefuseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrdersServiceImpl implements OrdersService {

	private final OrdersDao ordersDao;
	
	@Override
	public List<OrdersProductDto> getProducts() {
		// 판매 가능한 제품 목록
		// (isorder = true) && (isdel = false) && (price 존재)
		return ordersDao.getProducts();
	}
	
	@Override
	public int upsertInformation(OrdersDto order) {
		// 수주 정보 저장
		int orderCode = 0;
		List<OrdersDetailDto> details = order.getOrders_details();
		
		// create
		if (order.getOrder_code() == 0) {	
			// orders 생성
			ordersDao.createOrders(order);
			orderCode = order.getOrder_code();
			// ordersDetail
			for (OrdersDetailDto detail : details) {
				detail.setOrder_code(orderCode);
			}
		
		// update
		} else {
			// orders 비고 변경
			ordersDao.updateOrdersNote(order);
			orderCode = order.getOrder_code();
			// ordersDetail (전체 엎어치기)
			ordersDao.deleteOrdersDetails(orderCode);
		}
		
		// ordersDetail 추가
		ordersDao.createOrdersDetails(details);		
		
		return orderCode;
	}

	@Override
	public OrdersDto get(int orderCode) {
		OrdersDto order = ordersDao.findByCode(orderCode);
		return order;
	}
	
	@Override
	public void request(int orderCode) {
		// 수주 요청 (가맹점 -> 본사)
		// 요청상태(1)로 변경 (orders, orders_detail)
		OrdersDto order = ordersDao.findByCode(orderCode);
		
		int targetStatus = 1;
		order.setOrder_status(targetStatus);
		List<OrdersDetailDto> details = order.getOrders_details();
		for (OrdersDetailDto detail : details) {
			detail.setOrder_datail_status(targetStatus);
		}
		
		ordersDao.requestOrders(order);
	}
	
	private boolean autoApprove(int orderCode) {
		boolean result = false;
		
		return result;
	}

	@Override
	public void approve(int orderCode) {
		// 수주 승인 상태로 변경 (요청 상태에서 가능)
		// 수주 상세 출고예정 상태로 변경
	}

	@Override
	public void refuseOrCancel(OrdersRefuseDto refuse) {
		// 수주 취소(5)/반려(3) 상태로 변경 (요청 상태에서 가능)
		// 취소(가맹점): 취소상태(4) + 승인자 null + order_refuse null
		// 반려(본사): 반려상태(3) + 승인자 코드 존재 + 거부사유 존재
		
		OrdersDto order = ordersDao.findByCode(refuse.getOrder_code());
		
		int targetStatus = refuse.getReason() == null ? 5 : 3;
		
		order.setOrder_status(targetStatus);
		order.setOrder_refuse(refuse.getReason());
		order.setOrders_perm_code(refuse.getOrder_perm_code());

		// 취소 당시 총액
		order.setOrder_final_price(order.calculateTotalPrice());
		ordersDao.refuseOrders(order);
	}

	@Override
	public void delete(int orderCode) {
		ordersDao.deleteOrdersDetails(orderCode);
		ordersDao.deleteOrders(orderCode);
	}

	@Override
	public PageRespDto<OrdersListDto, Paging> list(PageRequestDto page) {
		int totalCount = ordersDao.totalCount();
		Paging paging = new Paging(totalCount, String.valueOf(page.getPage()));
		OrdersPageDto ordersPage = new OrdersPageDto(paging.getStart(), paging.getEnd());
		List<OrdersListDto> list = ordersDao.list(ordersPage);
		return new PageRespDto<OrdersListDto, Paging>(list, paging);
	}

	@Override
	public PageRespDto<OrdersListDto, Paging> list(PageRequestDto page, int clientCode) {
		int totalCount = ordersDao.totalCount(clientCode);
		Paging paging = new Paging(totalCount, String.valueOf(page.getPage()));
		OrdersPageDto ordersPage = new OrdersPageDto(paging.getStart(), paging.getEnd(), clientCode);
		List<OrdersListDto> list = ordersDao.list(ordersPage);
		return new PageRespDto<OrdersListDto, Paging>(list, paging);
	}

}
