import React from 'react';
import {NavLink} from "react-router-dom";

const Header = () => {
  return (
    <nav>
      <div className='nav-bar'>
        <NavLink exact to='/' className='nav-link' activeClassName='active'>
          商城
        </NavLink>
        <NavLink exact to='/order' className='nav-link' activeClassName='active'>
          订单
        </NavLink>
        <NavLink exact to='/product' className='nav-link' activeClassName='active'>
          添加商品
        </NavLink>
      </div>
    </nav>
  )
}

export default Header;