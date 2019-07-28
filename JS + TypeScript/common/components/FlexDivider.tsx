import * as React from "react";

export default class FlexDivider extends React.PureComponent<{
  orientation?: 'vertical' | 'horizontal',
  color?: string
}> {
  render(){
    const orientation = this.props.orientation;

    return(
      <div style={{
        margin: 0,
        width: orientation === "horizontal" ? undefined : 1,
        height: orientation === "horizontal" ? 1 : undefined,
        border: 'none',
        flexShrink: 0,
        flexGrow: 0,
        backgroundColor: this.props.color || 'rgba(0, 0, 0, 0.12)'
      }}/>
    )
  }
}
