import React from 'react';

import './App.css';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Header from "./Component/Header";
import Shop from "./Component/Shop";
import Order from "./Component/Order";

function App() {
  return (
    <div className="app">
      <BrowserRouter>
        <Header/>
        <Switch>
          <Route exact path='/' component={Shop}/>
          {<Route exact path='/order' component={Order}/>}
          {/*<Route exact path='/product' component={Product}/>*/}
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
