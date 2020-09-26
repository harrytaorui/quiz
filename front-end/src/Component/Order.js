import React, {Component} from 'react';
import {NavLink} from "react-router-dom";

export default class Order extends Component {

  state = {
    orders: [],
    isLoading: true
  }

  async componentDidMount() {
    const response = await fetch('http://localhost:8080/orders');
    const orders = await response.json();
    this.setState({orders, isLoading: false});
  }

  async handleClick(event) {
    this.setState({
      isLoading: true
    })
    const id = event.target.value;
    const response = await fetch(`http://localhost:8080/orders/${id}`, {
      method: 'DELETE',
    });
    if (response.status === 204) {
      await this.componentDidMount()
    } else {
      alert("订单删除失败，请稍后再试");
    }
  }

  render() {
    const {orders} = this.state;
    if (orders.length === 0) {
      return (
        <div className='no-content'>
          <h1>暂无订单，返回商城页面继续购买</h1>
          <NavLink to='/'><h1>商城页面</h1></NavLink>
        </div>
      )
    }
    return (
      <div className='orders'>
        <table>
          <thead>
          <tr>
            <td>名字</td>
            <td>单价</td>
            <td>数量</td>
            <td>单位</td>
            <td>操作</td>
          </tr>
          </thead>
          <tbody>
          {orders.map((order, index) => {
            return (
              <tr key={index} className='order'>
                <td>{order.product.name}</td>
                <td>{order.product.price}</td>
                <td>{order.amount}</td>
                <td>{order.product.unit}</td>
                <td>
                  <button onClick={(e) => this.handleClick(e)} value={order.id}>删除</button>
                </td>
              </tr>
            )
          })}
          </tbody>
        </table>
      </div>
    )
  }
}