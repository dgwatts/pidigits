import React, {Component} from "react";
import DigitsOutput from "../DigitsOutput/DigitsOutput";
import {submit} from "../../api";
import GoogleLogin from "react-google-login";

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

	responseGoogle = (googleUser) => {
		console.log(googleUser);

		const newState = {
			name: googleUser.profileObj.name,
			email: googleUser.profileObj.email,
			tokenId: googleUser.tokenId,
			accessToken: googleUser.accessToken,
			tokenObj: googleUser.tokenObject
		};

		this.setState(newState);
	}

	render() {
		return (
			<>
				<GoogleLogin
					clientId="335352850054-8q3pd4moshd6353588o1gbectgop6rt2.apps.googleusercontent.com"
					render={renderProps => (
						<button onClick={renderProps.onClick} disabled={renderProps.disabled}>This is my custom Google button</button>
					)}
					buttonText="Login"
					onSuccess={this.responseGoogle}
					onFailure={this.responseGoogle}
					cookiePolicy={'single_host_origin'}/>
				<input id="input" type="text"/>
				<button onClick={this.submit}>Submit</button>

				{this.state.response && <DigitsOutput digits={this.state.response}/>}
			</>
		);
	}
}

export default PiDigits;