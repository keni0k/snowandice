import * as React from "react";
import moment = require("moment");
import { IconButton } from "@material-ui/core";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import ChevronRightIcon from "@material-ui/icons/ChevronRight";

interface MonthPickerProps {
  month: number
  year: number
  onChange: (month: {month: number, year: number}) => void
}

export class MonthPicker extends React.PureComponent<MonthPickerProps>{
  onNextMonth = () => {
    const newMoment = moment();
    newMoment.month(this.props.month);
    newMoment.year(this.props.year);
    newMoment.add(1, 'month');
    this.props.onChange({month: newMoment.month(), year: newMoment.year()})
  };

  onPrevMonth = () => {
    const newMoment = moment();
    newMoment.month(this.props.month);
    newMoment.year(this.props.year);
    newMoment.subtract(1, 'month');
    this.props.onChange({month: newMoment.month(), year: newMoment.year()})
  };

  render(){
    const m = moment();
    m.month(this.props.month - 1);
    m.year(this.props.year);

    return(
      <div className="flex-container flex-container--centered flex-container--space-between text-align--center">
        <IconButton onClick={this.onPrevMonth}><ChevronLeftIcon/></IconButton>
        <div style={{width: 100}}>{m.format('MMMM')}</div>
        <IconButton onClick={this.onNextMonth}><ChevronRightIcon/></IconButton>
      </div>
    )
  }
}
