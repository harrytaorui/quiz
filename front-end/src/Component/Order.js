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
    const index = event.target.value;
    const response = await fetch(`http://localhost:8080/orders/${index + 1}`, {
      method: 'DELETE',
    });
    if (response.status !== 204) {
      alert("订单删除失败，请稍后再试");
    }
  }

  render() {
    const {orders, isLoading} = this.state;
    if (isLoading) {
      return <p>Loading</p>
    }
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
          <tr>
            <td>名字</td>
            <td>单价</td>
            <td>数量</td>
            <td>单位</td>
            <td>操作</td>
          </tr>
          {orders.map((order, index) => {
            return (
              <tr key={index} className='order'>
                <td>{order.product.name}</td>
                <td>{order.product.price}</td>
                <td>{order.amount}</td>
                <td>{order.product.unit}</td>
                <td>
                  <button onClick={(e) => this.handleClick} value={index}>删除</button>
                </td>
              </tr>
            )
          })}
        </table>
      </div>
    )
  }
}