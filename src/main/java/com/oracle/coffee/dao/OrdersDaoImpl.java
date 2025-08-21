package com.oracle.coffee.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.orders.OrdersDetailDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersListDto;
import com.oracle.coffee.dto.orders.OrdersPageDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;
import com.oracle.coffee.dto.orders.OrdersRefuseDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrdersDaoImpl implements OrdersDao {
	
	private final SqlSession session;
	
	@Override
	public void updateOrdersDetailsDelivery() {
		try {
			session.update("deliveryOrdersDetail");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<OrdersProductDto> getProducts() {
		List<OrdersProductDto> products = null;
		
		try {
			products = session.selectList("selectOrdersProducts");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public void createOrders(OrdersDto order) {
		try {
			session.insert("insertOrders", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createOrdersDetails(List<OrdersDetailDto> ordersdetails) {
		try {
			session.insert("insertAllOrdersDetails", ordersdetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public OrdersDto findByCode(int orderCode) {
		OrdersDto order = null;
		
		try {
			order = session.selectOne("selectOrderByCode", orderCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public void updateOrders(OrdersDto order) {
		try {
			session.update("updateOrders", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrdersDetails(int orderCode) {
		try {
			session.delete("deleteAllOrdersDetails", orderCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void requestOrders(OrdersDto order) {
		try {
			session.update("requestOrders", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrders(int orderCode) {
		try {
			session.update("deleteOrders", orderCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int totalCount(OrdersPageDto ordersPage) {
		int count = 0;

		try {
			count = session.selectOne("totalCountOrders", ordersPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<OrdersListDto> list(OrdersPageDto ordersPage) {
		List<OrdersListDto> list = null;
		
		try {
			list = session.selectList("listOrders", ordersPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void refuseOrders(OrdersDto order) {
		try {
			session.update("refuseOrders", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void approveOrders(OrdersDto order) {
		try {
			session.update("approveOrders", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Integer> approveOrdersDetails(int orderCode) {
		// 한 수주의 모든 orders_detail 재고 확인하는 프로시저 동작
		Map<String, Object> map = new HashMap<>();
		map.put("order_code", orderCode);
		
		try {
			session.selectOne("approveOrdersDetail", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<OrdersDetailDto> details = (List<OrdersDetailDto>) map.get("enabled_products") ;
		List<Integer> enabledPrdCodes = details.stream().map(detail -> detail.getProduct_code()).toList();
		return enabledPrdCodes;
		
	}

	@Override
	public List<OrdersDetailDto> currentOrder() {
		
		return session.selectList("currentOrder");
	}

	@Override
	public List<OrdersDto> excellentClient() {

		return session.selectList("excellentClient");
	}

	@Override
	public List<OrdersDto> monthTotalPrice() {

		return session.selectList("monthTotalPrice");
	}

}
