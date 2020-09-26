import React, {Component} from 'react';

export default class Shop extends Component{

  state = {
    products:[],
    isLoading: true
  }
  async componentDidMount() {
    const response = await fetch('http://localhost:8080/products');
    const products = await response.json();
    this.setState({ products, isLoading: false });
  }

  async handleClick(event){
    this.setState({
      isLoading: true
    })
    const index = event.target.value;
    const product = this.state.products[index];
    const order = {
      amount: 1,
      product: {...product},
      productName: product.name
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
        {products.map((product,index)=>{
          return (
            <div key={index} className='product'>
              <img src={product.imgUrl} alt={product.name}/>
              <p>{product.name}</p>
              <p>单价：{product.price}元/{product.unit}</p>
              <button onClick={(e)=>this.handleClick(e)} disabled={isLoading} value={index}>加入购物车</button>
            </div>
          )
        })}
      </div>
    )
  }
}