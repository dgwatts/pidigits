import React, {Component} from "react";

/**
 * The component to display results
 */
class DigitsOutput extends Component {

	render() {
		return (
			<>
				<div>Returned {this.props.digits.digits.length} digits</div>
				{this.props.digits.truncated && <div>Some requested indices were truncated</div>}
				{this.props.digits.outOfBounds && <div>Some requested indices were out of bounds</div>}
				{this.props.digits.digits.map(digit => <div>{digit.index} : {digit.value}</div>)}
			</>
		);
	}

}

export default DigitsOutput;