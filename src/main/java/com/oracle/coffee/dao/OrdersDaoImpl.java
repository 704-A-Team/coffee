package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.orders.OrdersDetailDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersListDto;
import com.oracle.coffee.dto.orders.OrdersPageDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrdersDaoImpl implements OrdersDao {
	
	private final SqlSession session;
	
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
	public void updateOrdersNote(OrdersDto order) {
		try {
			session.update("updateOrdersNote", order);
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
	public void updateOrdersStatus(OrdersDto order) {
		try {
			session.update("updateOrdersStatus", order);
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
	public int totalCount() {
		int count = 0;

		try {
			count = session.update("totalCountOrders");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int totalCount(int clientCode) {
		int count = 0;

		try {
			count = session.update("totalCountOrdersByClient", clientCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<OrdersListDto> list(OrdersPageDto page) {
		List<OrdersListDto> list = null;
		
		try {
			list = session.selectList("listOrders", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
