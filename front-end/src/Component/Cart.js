import React from 'react';
import {DeleteOutlined} from '@ant-design/icons/lib/icons';

const Cart = (props) => {
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
      <button onClick={props.handleSubmit}>立即下单</button>
      <button onClick={props.deleteAllInCart}>清空</button>
    </div>
  )
}

export default Cart;