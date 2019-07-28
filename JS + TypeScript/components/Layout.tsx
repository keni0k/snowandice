import * as React from 'react';
import NavMenuContainer from '../containers/NavMenuContainer';
import { Component } from "react";

export class Layout extends React.Component<{}, {}> {
    public render() {
      const firstChildProps = this.props.children && (this.props.children as any).props;
      return(
        <div className="app-container flex-container flex-container--vertical">
          <NavMenuContainer {...firstChildProps}/>
          <div className="flex-item--flexible flex-item--greedy flex-container fill-parent">
            { this.props.children }  
          </div>
        </div>
      );
    }
}

export function wrap(Component: React.ComponentType){
  return class extends React.Component {
    static displayName = `${Component.displayName || Component.name || ''} with Layout`;

    render() {
      return <Layout><Component {...this.props} /></Layout>;
    }
  };
}


