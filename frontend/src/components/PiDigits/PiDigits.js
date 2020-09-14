import React, {Component} from "react";
import DigitsOutput from "../DigitsOutput/DigitsOutput";
import {submit} from "../../api";

/**
 * The complete UI component for the Pi digit service
 */
class PiDigits extends Component {

	constructor(props) {
		super(props);

		this.state = {
			response: null,
		}
	}

	submit = () => {
		submit(document.getElementById("input").value).then((response => this.setState({response})));
	}

	render() {
		return (
			<>
				<input id="input" type="text"/>
				<button onClick={this.submit}>Submit</button>

				{this.state.response && <DigitsOutput digits={this.state.response}/>}
			</>
		);
	}
}

export default PiDigits;