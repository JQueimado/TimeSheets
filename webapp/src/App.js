import React from 'react';
import { BrowserRouter as Router, Route } from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css"

//Components
import NavigationBar from "./Components/NavigationBar"
import Home from "./Components/Home";
import Add from "./Components/Add";

function App() {
  return (
    <div className="App">
      <Router>
        <NavigationBar/>
        <Route path="/" exact component={Home} />
        <Route path="/add" exact component={Add} />
      </Router>
    </div>
  );
}

export default App;
