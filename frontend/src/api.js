// Rest calls go in here
import axios from "axios";

export function submit(criteria) {
	return axios.get('/pidigits/' + criteria)
		.then(response => response.data);
}