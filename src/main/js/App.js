const axios = require('axios');
import React, {useState, useEffect} from 'react'

const App = () => {
	useEffect(()=>{
	    axios.get('/apl-api/experiment')
                  .then(function (response) {
                    setHello(response.data.x);
                  })
                  .catch(function (error) {
                    console.log(error);
                  });
	},[]);
	return (
	<div>
	    <h1>TEST</h1>
	</div>
	)

}

export default App