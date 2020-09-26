import React, {Component} from 'react';

export default class Product extends Component {

  state = {
    name: '',
    price: 0,
    unit: '',
    imgUrl: ''
  }

  async handleSubmit(event){
    event.preventDefault();
    const product = this.state;
    const response = await fetch(`http://localhost:8080/products`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(product),
    });
    if (response.status === 201) {
      this.setState({
        name: '',
        price: 0,
        unit: '',
        imgUrl: ''
      })
    }
    if (response.status === 400) {
      alert('商品名称已存在，请输入新的商品名称');
    }
  }

  handleFieldChange = (field, event) => {
    const value = event.target.value;
      this.setState({
        [field]: value
      })
  }

  render() {
    return (
      <div className='container'>
        <form className='my-form' onSubmit={(e)=>this.handleSubmit(e)}>
          <div className='form-group'>
            <label htmlFor='name'>名称</label>
            <input type='text' className='form-control' id='name' value={this.state.name} placeholder='名称'
                   onChange={(event) => this.handleFieldChange('name', event)}/>
          </div>
          <div className='form-group'>
            <label htmlFor='name'>价格</label>
            <input type='text' className='form-control' id='name' value={this.state.price} placeholder='价格'
                   onChange={(event) => this.handleFieldChange('price', event)}/>
          </div>
          <div className='form-group'>
            <label htmlFor='name'>单位</label>
            <input type='text' className='form-control' id='name' value={this.state.unit} placeholder='单位'
                   onChange={(event) => this.handleFieldChange('unit', event)}/>
          </div>
          <div className='form-group'>
            <label htmlFor='name'>图片</label>
            <input type='text' className='form-control' id='name' value={this.state.imgUrl} placeholder='图片'
                   onChange={(event) => this.handleFieldChange('imgUrl', event)}/>
          </div>
          <button
            type='submit'
            className='btn btn-primary'
            disabled={this.state.name === '' || !(/[0-9]/).test(this.state.price) || this.state.unit === '' || this.state.imgUrl === ''}>
            Submit
          </button>
        </form>
      </div>
    )
  }
}