const axios = require('axios');
import React, {useState, useEffect} from 'react'
import { SortingForm } from '../Sorting/SortingForm';
import { SortingResultView } from '../Sorting/SortingResultView';

const App = () => {

	const [id, setId] = useState("")

	return (
	<div>
		{
			id === "" ? <SortingForm setExperimentId={setId}/> :<SortingResultView experimentId={id}/>
		}
	    
	</div>
	)

}

export default App