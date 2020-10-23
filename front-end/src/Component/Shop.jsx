import React, {Component} from 'react';
import {DeleteOutlined, ShoppingCartOutlined} from '@ant-design/icons/lib/icons';

export default class Shop extends Component {

  state = {
    products: [],
    isLoading: true
  }

  async componentDidMount() {
    const response = await fetch('http://localhost:8080/products');
    const products = await response.json();
    this.setState({products, isLoading: false});
  }

  addToCart = (product) => {
    this.setState({
      isLoading: true
    })
    const products = localStorage.getItem("products") ? JSON.parse(localStorage.getItem("products")) : []
    let item = products.find(current => current.name === product.name)
    if (item === undefined) {
      item = {
        name: product.name,
        price: product.price,
        unit: product.unit,
        id: product.id,
        imgUrl: product.imgUrl,
        num: 1
      }
    } else {
      item.num += 1
    }
    products.push(item)
    localStorage.setItem("products", JSON.stringify(products))
    this.setState({
      isLoading: false
    })
  }

  checkCart = () => {
    const products = localStorage.getItem("products") ? JSON.parse(localStorage.getItem("products")) : []
    if (products.length === 0) {
      return <p>暂无商品，请添加商品</p>
    }
    return (
      <div className='shopping-cart'>
        <table>
          <thead>
          <tr>
            <td>商品</td>
            <td>数量</td>
            <td/>
          </tr>
          </thead>
          <tbody>
          {products.forEach((product, index) => {
            return (
              <tr key={index} className='product'>
                <td>{product.name}</td>
                <td>{product.num}</td>
                <td><DeleteOutlined/></td>
              </tr>
            )
          })}
          </tbody>
        </table>
      </div>
    )
  }

  deleteAllInCart = () => {
    localStorage.clear()
  }

  deleteFromCart = (product) => {
    const products = localStorage.getItem("products") ? JSON.parse(localStorage.getItem("products")) : []
    const index = products.findIndex(current => current.name === product.name)
    if (index >= 0) {
      products.splice(0, index)
    }
    localStorage.setItem("products", JSON.stringify(products))
  }

  async handleSubmit(event) {
    this.setState({
      isLoading: true
    })
    const products = localStorage.getItem("products") ? JSON.parse(localStorage.getItem("products")) : []
    const order = {
      products: {...products},
    }
    const response = await fetch('http://localhost:8080/orders', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(order),
    });
    if (response.status === 201) {
      this.setState({
        isLoading: false
      })
    }
    console.log(order);
  }

  render() {
    const {products, isLoading} = this.state;
    return (
      <div className='products'>
        {products.map((product, index) => {
          return (
            <div key={index} className='product'>
              <img src={product.imgUrl} alt={product.name}/>
              <p>{product.name}</p>
              <p>单价：{product.price}元/{product.unit}</p>
              <button onClick={(e) => this.addToCart(product)} disabled={isLoading} value={index}>加入购物车</button>
            </div>
          )
        })}
        <ShoppingCartOutlined/>
      </div>
    )
  }
}