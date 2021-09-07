const React = require('react');
const ReactDOM = require('react-dom');
const axios = require('axios');
import  { useState, useEffect } from 'react';

const App = () => {

    const [ hello, setHello ] = useState("test");

	useEffect(()=>{
	    axios.get('/apl-api/experiment')
                  .then(function (response) {
                    setHello(response.data.x);
                  })
                  .catch(function (error) {
                    console.log(error);
                  });
	},[]);
	return (<>{hello}</>)

}

ReactDOM.render(
    <App/>,
  document.getElementById('react')
);