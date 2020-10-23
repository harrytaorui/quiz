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

  handleClick = async (id) => {
    this.setState({
      isLoading: true
    })
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

        {orders.map((order, index) => {
          return (
            <table>
              <p>订单号：{order.id}</p>
              <button onClick={this.handleClick(order.id)}>删除</button>
              <thead>
              <tr>
                <td>#</td>
                <td>名字</td>
                <td>单价</td>
                <td>数量</td>
              </tr>
              </thead>
              <tbody>
              {order.products.map(product => {
                return (
                  <tr key={index} className='order'>
                    <td>{index}</td>
                    <td>{product.name}</td>
                    <td>{product.price}</td>
                    <td>`{product.amount}{product.unit}`</td>
                  </tr>
                )
              })}
              <tr>
                <td>总价</td>
                <td/>
                <td>{order.products.reduce(product=>product.price*product.amount)}</td>
              </tr>
              </tbody>
            </table>
          )
        })}

      </div>
    )
  }
}