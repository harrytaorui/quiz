import React, {Component} from 'react';

export default class Product extends Component {

  state = {
    name: '',
    price: 0,
    unit: '',
    imgUrl: ''
  }
  handleFieldChange = (field, event) => {
    const value = event.target.value;
    const numberReg =
    this.setState({
      [field]: value
    })
  }

  render() {
    return (
      <div className='container'>
        <form className='my-form' onSubmit={this.handleSubmit}>
          <div className='form-group'>
            <label htmlFor='name'>名称</label>
            <input type='text' className='form-control' id='name' value={this.state.name} placeholder='名称'
                   onChange={(event) => this.handleFieldChange('name', event)}/>
          </div>
        </form>
      </div>
    )
  }
}